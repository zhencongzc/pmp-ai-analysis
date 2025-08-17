package com.pmp.application.service.dicom.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pixelmed.dicom.DicomException;
import com.pmp.common.util.HttpUtil;
import com.pmp.domain.model.dicom.DicomDO;
import com.pmp.domain.model.patient.PatientDO;
import com.pmp.domain.model.report.ReportDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.common.util.DicomUtil;
import com.pmp.common.util.FileUtil;
import com.pmp.common.util.StreamUtil;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@Slf4j
@RequiredArgsConstructor
public class DicomServiceImpl implements DicomService {

    @Value("${document.upload.path}")
    private String uploadPath;

    private final LabelDataMapper labelDataMapper;
    private final PatientMapper patientMapper;
    private final DicomMapper dicomMapper;

    /**
     * 保存病人的dicom文件
     *
     * @param file
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String saveDicom(MultipartFile file) throws IOException, DicomException {
        InputStream is = file.getInputStream();
        byte[] data = StreamUtil.getCopyInputStream(is);
        //读取DICOM元数据
        DicomInputStream dis = new DicomInputStream(new ByteArrayInputStream(data));
        Attributes attributes = dis.readDataset();

        //封装病人信息
        PatientDO patientDO = new PatientDO();
        patientDO.setPatientId(attributes.getString(Tag.PatientID));
        patientDO.setPatientName(attributes.getString(Tag.PatientName));
//            patientDO.setSex(attributes.getString(Tag.PatientSex));//性别有乱码，后续优化
        patientDO.setBirthDay(attributes.getString(Tag.PatientBirthDate));
        //查询是否录入过当前病人信息，如果没有就新增数据
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
        String dicomPath = uploadPath + accessionNumber + "/" + FileUtil.getFileName(file, true);
        StreamUtil.saveInputStreamToFile(new ByteArrayInputStream(data), dicomPath);

        //先将处理后的dicom文件和png地址存储起来
        String dicomPathNew = uploadPath + accessionNumber + "/result/sc_dicom/" + accessionNumber + "_" + FileUtil.getFileName(file, false) + "_sc.dcm";
        String pngPath = uploadPath + accessionNumber + "/result/sc_dicom/" + accessionNumber + "_" + FileUtil.getFileName(file, false) + "_sc.png";
        dicomDO.setDicomPath(dicomPathNew);
        dicomDO.setPngPath(pngPath);

        //新增dicom数据
        dicomMapper.insertDicom(dicomDO);
        return accessionNumber;
    }

    /**
     * 查询dicom列表
     * 以accessionNumber为纬度做分组展示
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
        //todo 等待集成
        return dicomMapper.findReport("20241225004460");
    }

    /**
     * 大模型分析后回调接口
     * 回传是否成功标记和对应的唯一标识号
     */
    @Override
    public ResponseResult<String> dicomAnalysisCallback(Integer isSuccess, String accessionNumber) {
        log.info("收到回调请求，isSuccess：{}，accessionNumber：{}", isSuccess, accessionNumber);
        if (isSuccess != 1) {
            return ResponseResult.error(500, "分析失败");
        }

        //获取处理后的，将dicom转为png
        File uploadDir = new File(uploadPath + accessionNumber + "/result");
        if (uploadDir.exists() && uploadDir.isDirectory()) {
            File[] files = uploadDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String dicomPath = file.getAbsolutePath();
                    String pngPath = dicomPath.replace(".dcm", ".png");
                    try {
                        DicomUtil.convertDicomToPng(dicomPath, pngPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseResult.error(500, "转换失败");
                    }
                }
            }
        }
        return ResponseResult.success();
    }

    /**
     * 大模型分析（PCI）回调接口
     * 回传分析报告
     */
    @Override
    public ResponseResult<String> dicomAnalysisPciCallback(ReportDO reportDO) {
        return ResponseResult.success();
    }

    /**
     * 调用大模型分析dimcom文件
     *
     * @param accessionNumber
     */
    @Override
    public void dicomAnalysisFeign(String accessionNumber) {
        String url = "http://192.168.5.126:8000/ct-module/dicom/analysis";
        // 构造请求体
        Map<String, String> requestBody = Collections.singletonMap("dicomUrl", uploadPath + accessionNumber);
        // 发送POST请求
        HttpUtil.post(url, requestBody);
    }
}
