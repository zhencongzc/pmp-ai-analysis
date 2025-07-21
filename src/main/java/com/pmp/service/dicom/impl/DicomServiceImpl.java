package com.pmp.service.dicom.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.labelData.LabelDataDO;
import com.pmp.domain.labelData.LabelDataDTO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.mapper.DicomMapper;
import com.pmp.mapper.LabelDateMapper;
import com.pmp.mapper.PatientMapper;
import com.pmp.service.dicom.DicomService;
import com.pmp.web.vo.DicomVO;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public ResponseResult<List<DicomDO>> findDicom(DicomVO dicomVO) {
        Page<Object> page = PageHelper.startPage(dicomVO.getPage(), dicomVO.getPageRow(), true);
        List<DicomDO> list = dicomMapper.findDicomByCondition(dicomVO);
        return ResponseResult.success(list, page.getTotal());
    }

    @Override
    public ResponseResult<DicomDO> findDicomDetail(Integer id) {
        DicomVO dicomVO = new DicomVO();
        dicomVO.setId(id);
        DicomDO res = dicomMapper.findDicomById(dicomVO);
        return ResponseResult.success(res);
    }
}
