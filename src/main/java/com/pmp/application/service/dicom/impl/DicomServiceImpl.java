package com.pmp.application.service.dicom.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pixelmed.dicom.DicomException;
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
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DicomServiceImpl implements DicomService {

    private static final Logger logger = Logger.getLogger(DicomServiceImpl.class.getName());

    @Value("${document.upload.path}")
    private String uploadPath;

    private final LabelDataMapper labelDataMapper;
    private final PatientMapper patientMapper;
    private final DicomMapper dicomMapper;

    @Override
    @Transactional(rollbackFor = {IOException.class, DicomException.class})
    public void saveDicom(MultipartFile file) throws IOException, DicomException {
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
        if (!Objects.isNull(existDicom)) {
            return;
        }

        //将dicom文件存储到服务器
        String dicomPath = uploadPath + dicomDO.getAccessionNumber() + "/" + FileUtil.getFileName(file, true);
        StreamUtil.saveInputStreamToFile(new ByteArrayInputStream(data), dicomPath);
        //在当前路径，将dicom转为png
        String dicomName = FileUtil.getFileName(file, false);
        String pngPath = uploadPath + dicomDO.getAccessionNumber() + "/" + dicomName + ".png";
        DicomUtil.convertDicomToPng(dicomPath, pngPath);

        //新增dicom数据
        dicomDO.setDicomPath(dicomPath);
        dicomDO.setPngPath(pngPath);
        dicomMapper.insertDicom(dicomDO);
    }

    @Override
    public ResponseResult<List<DicomDO>> findDicom(DicomVO dicomVO) {
        Page<Object> page = PageHelper.startPage(dicomVO.getPage(), dicomVO.getPageRow(), true);
        List<DicomDO> list = dicomMapper.findDicomByCondition(dicomVO);
        return ResponseResult.success(list, page.getTotal());
    }

    @Override
    public DicomDO findDicomDetail(Integer id) {
        DicomVO dicomVO = new DicomVO();
        dicomVO.setId(id);
        return dicomMapper.findDicomById(dicomVO);
    }

    @Override
    public List<String> findGroupPicture(String accessionNumber) {
        return dicomMapper.findGroupPictureByAccessionNumber(accessionNumber);
    }

    @Override
    public ReportDO findReport(String accessionNumber) {
        return dicomMapper.findReport(accessionNumber);
    }
}
