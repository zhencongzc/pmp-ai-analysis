package com.pmp.application.service.dicom.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pixelmed.dicom.DicomException;
import com.pmp.common.util.HttpUtil;
import com.pmp.domain.model.dicom.DicomDO;
import com.pmp.domain.model.dicom.DicomGroupDTO;
import com.pmp.domain.model.dicom.enums.DicomStatus;
import com.pmp.domain.model.patient.PatientDO;
import com.pmp.domain.model.report.ReportDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.common.util.DicomUtil;
import com.pmp.common.util.FileUtil;
import com.pmp.common.util.StreamUtil;
import com.pmp.domain.model.report.enums.DiseaseLevel;
import com.pmp.infrastructure.mapper.DicomMapper;
import com.pmp.infrastructure.mapper.LabelDataMapper;
import com.pmp.infrastructure.mapper.PatientMapper;
import com.pmp.application.service.dicom.DicomService;
import com.pmp.interfaces.web.vo.DicomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class DicomServiceImpl implements DicomService {

    @Value("${document.upload.path}")
    private String uploadPath;
    @Value("${document.png.path}")
    private String pngPath;
    @Value("${ai.model.analysis.path}")
    private String analysisPath;
    @Value("${ai.model.pci.path}")
    private String pciPath;

    private final LabelDataMapper labelDataMapper;
    private final PatientMapper patientMapper;
    private final DicomMapper dicomMapper;

    /**
     * 保存患者的dicom文件
     *
     * @param file
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String saveDicom(MultipartFile file) throws Exception {
        //校验文件格式
        String fileName = FileUtil.getFileName(file, true);
        if (!fileName.toLowerCase().endsWith(".dcm")) {
            throw new RuntimeException("文件格式错误，请上传.dcm文件格式");
        }

        InputStream is = file.getInputStream();
        byte[] data = StreamUtil.getCopyInputStream(is);
        //读取DICOM元数据
        DicomInputStream dis = new DicomInputStream(new ByteArrayInputStream(data));
        Attributes attributes = dis.readDataset();

        //封装患者信息
        PatientDO patientDO = new PatientDO();
        patientDO.setPatientId(attributes.getString(Tag.PatientID));
        patientDO.setPatientName(attributes.getString(Tag.PatientName));
//            patientDO.setSex(attributes.getString(Tag.PatientSex));//性别有乱码，后续优化
        patientDO.setBirthDay(attributes.getString(Tag.PatientBirthDate));
        //查询是否录入过当前患者信息，如果没有就新增数据
        PatientDO existPatient = patientMapper.selectPatientByPatientId(patientDO);
        if (existPatient == null) {
            patientMapper.insertPatient(patientDO);
        }

        //将dicom数据转为对象，如果之前存储过就不再处理
        DicomDO dicomDO = DicomUtil.changeAttributesToDicom(attributes);
        DicomDO existDicom = dicomMapper.selectDicomBySopInstanceUid(dicomDO);
        String accessionNumber = dicomDO.getAccessionNumber();
        if (!Objects.isNull(existDicom)) {
            return accessionNumber;
        }

        //将dicom文件存储到服务器
        String dicomPath = uploadPath + accessionNumber + "/" + fileName;
        StreamUtil.saveInputStreamToFile(new ByteArrayInputStream(data), dicomPath);

        // 处理后的dicom文件地址，目前采用吴霞的模型，不新生成dicom
        String dicomPathNew = uploadPath + accessionNumber + "/result/sc_dicom/" + accessionNumber + "_" + FileUtil.getFileName(file, false) + "_sc.dcm";
        // 吴霞模型输出png的地址
        String pngPathNew = pngPath + accessionNumber + "/result/Result_" + FileUtil.getFileName(file, false) + ".png";
        dicomDO.setDicomPath(dicomPathNew);
        dicomDO.setPngPath(pngPathNew);
        dicomDO.setStatus(DicomStatus.PENDING.getCode());

        //新增dicom数据
        dicomMapper.insertDicom(dicomDO);
        return accessionNumber;
    }

    /**
     * 查询dicom列表
     *
     * @param dicomVO
     * @return
     */
    @Override
    public ResponseResult<List<DicomDO>> findDicom(DicomVO dicomVO) {
        Page<Object> page = PageHelper.startPage(dicomVO.getPage(), dicomVO.getPageRow(), true);
        List<DicomDO> list = dicomMapper.findDicomByCondition(dicomVO);
        return ResponseResult.success(list, page.getTotal());
    }

    /**
     * 查询dicom组列表
     * 以accessionNumber为纬度做分组展示
     *
     * @param dicomVO
     * @return
     */
    @Override
    public ResponseResult<List<DicomGroupDTO>> findDicomGroup(DicomVO dicomVO) {
        Page<Object> page = PageHelper.startPage(dicomVO.getPage(), dicomVO.getPageRow(), true);
        List<DicomGroupDTO> list = dicomMapper.findDicomGroupByCondition(dicomVO);
        return ResponseResult.success(list, page.getTotal());
    }

    /**
     * 查看dicom详情
     *
     * @param id
     * @return
     */
    @Override
    public DicomDO findDicomDetail(Integer id) {
        DicomVO dicomVO = new DicomVO();
        dicomVO.setId(id);
        return dicomMapper.findDicomById(dicomVO);
    }

    /**
     * 根据医院唯一标识号查询dicom组详情
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    @Override
    public DicomGroupDTO findDicomDetailByAccessionNumber(String accessionNumber) {
        return dicomMapper.findDicomGroupByAccessionNumber(accessionNumber);
    }

    /**
     * 根据医院唯一标识号查询整组图片
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    @Override
    public List<String> findGroupPicture(String accessionNumber) {
        return dicomMapper.findGroupPictureByAccessionNumber(accessionNumber);
    }

    /**
     * 根据医院唯一标识号查询CT分析报告
     *
     * @param accessionNumber
     * @return
     */
    @Override
    public ReportDO findReport(String accessionNumber) {
        return dicomMapper.findReport(accessionNumber);
    }

    /**
     * 病灶识别模型分析后回调接口
     * 回传是否成功标记和对应的唯一标识号
     */
    @Override
    public ResponseResult<String> dicomAnalysisCallback(Integer isSuccess, String accessionNumber) {
        log.info("收到病灶识别模型回调请求，isSuccess：{}，accessionNumber：{}", isSuccess, accessionNumber);
        if (isSuccess != 1) {
            dicomMapper.updateDicomGroupStatus(DicomStatus.FAILED.getCode(), accessionNumber);
            return ResponseResult.error(500, "大模型分析失败");
        }

        //获取处理后的，将dicom转为png
        File uploadDir = new File(uploadPath + accessionNumber + "/result/sc_dicom");
        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] files = uploadDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String dicomPath = file.getAbsolutePath();
                    if (!dicomPath.endsWith(".dcm")) {
                        continue;
                    }
                    String pngPath = dicomPath.replace(".dcm", ".png");
                    try {
                        DicomUtil.convertDicomToPng(dicomPath, pngPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseResult.error(500, "dicom文件转png失败");
                    }
                }
            }
        }
        log.info("图片已转换完成，pngUrl：{}", uploadDir);
        dicomMapper.updateDicomGroupStatus(DicomStatus.SUCCESS.getCode(), accessionNumber);
        return ResponseResult.success();
    }

    /**
     * PCI评分模型回调接口
     * 回传分析报告
     */
    @Override
    public ResponseResult<String> dicomAnalysisPciCallback(ReportDO reportDO) {
        log.info("收到PCI评分模型回调请求，accessionNumber：{}，reportDO：{}", reportDO.getAccessionNumber(), reportDO);
        // 获取患者id、名称
        DicomDO dicomDO = dicomMapper.findDicomByAccessionNumber(reportDO.getAccessionNumber());
        reportDO.setPatientId(dicomDO.getPatientId());
        reportDO.setPatientName(dicomDO.getPatientName());

        // 添加结论
        StringBuilder conclusion = new StringBuilder();
        conclusion.append("检测结果为：");
        if (reportDO.getIsPositive() == 1) {
            conclusion.append("阳性（阳性概率为").append(reportDO.getPositiveRate()).append("）。");
        } else {
            conclusion.append("阴性。");
        }
        conclusion.append("病理分级为").append(DiseaseLevel.getByCode(reportDO.getDiseaseLevel()).getName()).append("，");
        conclusion.append(DiseaseLevel.getByCode(reportDO.getDiseaseLevel()).getDescription());
        if (reportDO.getMesentericContracture() == 1) {
            conclusion.append("存在肠系膜挛缩现象。");
        } else {
            conclusion.append("不存在肠系膜挛缩现象。");
        }
//        conclusion.append("可考虑的治疗策略：").append(DiseaseLevel.getByCode(reportDO.getDiseaseLevel()).getTreatmentStrategies());
        reportDO.setConclusion(conclusion.toString());

        //新增分析报告
        dicomMapper.insertReport(reportDO);
        log.info("分析报告已完成，reportDO：{}", reportDO);
        return ResponseResult.success();
    }

    /**
     * 调用模型分析dimcom文件
     *
     * @param accessionNumber
     */
    @Override
    public ResponseResult<String> dicomAnalysisFeign(String accessionNumber) {
        //病灶识别模型
        Map<String, String> requestBody1 = Collections.singletonMap("dicomUrl", uploadPath + accessionNumber);
        // 异步执行HTTP请求
        CompletableFuture.runAsync(() -> {
            try {
                HttpUtil.post(analysisPath, requestBody1);
            } catch (Exception e) {
                log.error("调用病灶识别模型分析失败：accessionNumber：{}", accessionNumber);
                dicomMapper.updateDicomGroupStatus(DicomStatus.FAILED.getCode(), accessionNumber);
            }
        });

        //PCI评分模型
        Map<String, String> requestBody2 = Collections.singletonMap("dcm_path", uploadPath + accessionNumber);
        // 异步执行HTTP请求
        CompletableFuture.runAsync(() -> {
            try {
                HttpUtil.post(pciPath, requestBody2);
            } catch (Exception e) {
                log.error("调用PCI评分模型分析失败：accessionNumber：{}", accessionNumber);
            }
        });

        //将dicom状态改为”分析中“
        dicomMapper.updateDicomGroupStatus(DicomStatus.PROCESSING.getCode(), accessionNumber);

        return ResponseResult.success("请求成功");
    }

    /**
     * 删除dicom文件
     *
     * @param accessionNumber
     * @return
     */
    @Override
    public ResponseResult<String> deleteDicom(String accessionNumber) {
        dicomMapper.deleteDicom(accessionNumber);
        return ResponseResult.success();
    }
}
