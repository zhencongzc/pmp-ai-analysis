package com.pmp.service.dicom.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.labelData.LabelDataDO;
import com.pmp.domain.labelData.LabelDataDTO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.infrastructure.util.DicomUtil;
import com.pmp.infrastructure.util.FileUtil;
import com.pmp.infrastructure.util.StreamUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DicomServiceImpl implements DicomService {

    private static final Logger logger = Logger.getLogger(DicomServiceImpl.class.getName());

    private static String uploadUrl = "/opt/upload/";

    @Autowired
    LabelDateMapper labelDateMapper;
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    DicomMapper dicomMapper;

    @Override
    @Transactional
    public void saveDicom(MultipartFile file) throws IOException {
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
        PatientDO existPatient = patientMapper.selectPatientById(patientDO);
        if (existPatient == null)
            patientMapper.insertPatient(patientDO);

        DicomDO dicomDO = DicomUtil.changeAttributesToDicom(attributes);
        String dicomPath = uploadUrl + dicomDO.getAccessionNumber() + "/" + file.getOriginalFilename();
        //将dicom文件存储到服务器
        StreamUtil.saveInputStreamToFile(new ByteArrayInputStream(data), dicomPath);

        String dicomName = FileUtil.getFileName(file, false);
        String pngPath = uploadUrl + dicomDO.getAccessionNumber() + "/" + dicomName + ".png";
        //在当前路径，将dicom转为png
//        DicomUtil.convert(dicomPath, pngPath);

        //新增dicom数据
        dicomDO.setDicomPath(dicomPath);
        dicomDO.setPngPath(pngPath);
        DicomDO existDicom = dicomMapper.selectDicomBySopInstanceUid(dicomDO);
        if (existDicom == null)
            dicomMapper.insertDicom(dicomDO);
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
