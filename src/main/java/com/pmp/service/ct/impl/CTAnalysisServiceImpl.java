package com.pmp.service.ct.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.domain.base.ResponseResult;
import com.pmp.domain.ct.Patient;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;
import com.pmp.mapper.CTAnalysisMapper;
import com.pmp.service.ct.CTAnalysisService;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CTAnalysisServiceImpl implements CTAnalysisService {

    @Autowired
    CTAnalysisMapper ctAnalysisMapper;

    @Override
    public void addLabelData(PatientVO patientVO) {
        Patient patient = new Patient();
        patient.setComputerName(patientVO.getComputerName());
        patient.setPatientId(patientVO.getPatientId());
        patient.setLabelData(patientVO.getLabelData().toJSONString());
        ctAnalysisMapper.insertPatient(patient);
    }

    @Override
    public ResponseResult<List<PatientDTO>> findLabelData(PatientVO patientVO) {
        Page<Object> page = PageHelper.startPage(patientVO.getPage(), patientVO.getPageRow(), true);
        List<Patient> list = ctAnalysisMapper.selectPatientByCondition(patientVO);
        //转换对象
        List<PatientDTO> res = new ArrayList<>();
        list.forEach(a -> {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(a.getId());
            patientDTO.setComputerName(a.getComputerName());
            patientDTO.setPatientId(a.getPatientId());
            patientDTO.setLabelData(JSONObject.parseObject(a.getLabelData()));
            patientDTO.setCreateTime(a.getCreateTime());
            res.add(patientDTO);
        });
        return ResponseResult.success(res, page.getTotal());
    }

    @Override
    public void saveDicom(MultipartFile file) {
        try {
            // 直接从 MultipartFile 获取输入流
            try (DicomInputStream dis = new DicomInputStream(file.getInputStream())) {
                // 读取 DICOM 元数据
                Attributes attributes = dis.readDataset(-1, -1);

                // 提取关键信息
                String patientId = attributes.getString(Tag.PatientID);
                String patientName = attributes.getString(Tag.PatientName);
                String modality = attributes.getString(Tag.Modality);
                int rows = attributes.getInt(Tag.Rows, 0);
                int columns = attributes.getInt(Tag.Columns, 0);

                // 返回结果
                String a = String.format("DICOM 文件上传成功：\n" +
                                "患者ID: %s\n" +
                                "患者姓名: %s\n" +
                                "设备类型: %s\n" +
                                "图像尺寸: %d x %d",
                        patientId, patientName, modality, rows, columns);
            }
        } catch (IOException e) {
            e.printStackTrace();
            String a = "DICOM 文件解析失败";
        }

    }


}
