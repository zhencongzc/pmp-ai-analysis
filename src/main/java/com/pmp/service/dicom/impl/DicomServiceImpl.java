package com.pmp.service.dicom.impl;

import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.mapper.DicomMapper;
import com.pmp.mapper.LabelDateMapper;
import com.pmp.mapper.PatientMapper;
import com.pmp.service.dicom.DicomService;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class DicomServiceImpl implements DicomService {

    private static final Logger logger = Logger.getLogger(DicomServiceImpl.class.getName());

    @Autowired
    LabelDateMapper labelDateMapper;
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    DicomMapper dicomMapper;

    @Override
    public void saveDicom(MultipartFile file) {
        try {
            //读取DICOM元数据
            DicomInputStream dis = new DicomInputStream(file.getInputStream());
            Attributes attributes = dis.readDataset();

            //封装病人信息
            PatientDO patientDO = new PatientDO();
            patientDO.setPatientId(attributes.getString(Tag.PatientID));
            patientDO.setPatientName(attributes.getString(Tag.PatientName));
//            patientDO.setSex(attributes.getString(Tag.PatientSex));//性别有乱码，后续优化
            patientDO.setBirthDay(attributes.getString(Tag.PatientBirthDate));
            //查询是否录入过当前病人信息，如果没有就新增数据
            PatientDO existPatient = patientMapper.selectPatientById(patientDO);
            if (existPatient == null)
                patientMapper.insertPatient(patientDO);

            //新增dicom文件数
            DicomDO dicomDO = new DicomDO();
            dicomDO.setSopInstanceUid(attributes.getString(Tag.SOPInstanceUID));
            dicomDO.setPatientId(attributes.getString(Tag.PatientID));
            dicomDO.setPatientName(attributes.getString(Tag.PatientName));
            dicomDO.setAccessionNumber(attributes.getString(Tag.AccessionNumber));
            dicomDO.setStudyId(attributes.getString(Tag.StudyID));
            dicomDO.setSeriesNumber(attributes.getString(Tag.SeriesNumber));
            dicomDO.setInstanceNumber(attributes.getString(Tag.InstanceNumber));
            dicomDO.setSeriesDate(attributes.getString(Tag.SeriesDate));
            dicomDO.setSeriesTime(attributes.getString(Tag.SeriesTime));
            dicomDO.setStudyDescription(attributes.getString(Tag.StudyDescription));
            dicomDO.setModality(attributes.getString(Tag.Modality));
            dicomDO.setSeriesDescription(attributes.getString(Tag.SeriesDescription));
            dicomDO.setRows(attributes.getString(Tag.Rows));
            dicomDO.setColumns(attributes.getString(Tag.Columns));
            DicomDO existDicom = dicomMapper.selectDicomBySopInstanceUid(dicomDO);
            if (existDicom == null)
                dicomMapper.insertDicom(dicomDO);

            //todo 将dicom文件存储到服务器/upload/pmp-ai-analysis


        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("DICOM 文件解析失败");
        }

    }


}
