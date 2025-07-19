package com.pmp.service.ct.impl;

import com.alibaba.fastjson.JSONObject;
import com.pmp.domain.ct.Patient;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;
import com.pmp.mapper.CTAnalysisMapper;
import com.pmp.service.ct.CTAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CTAnalysisServiceImpl implements CTAnalysisService {

    @Autowired
    CTAnalysisMapper ctAnalysisMapper;

    public void addLabelData(String computerName, String patientId, JSONObject labelData) {
        Patient patient = new Patient();
        patient.setComputerName(computerName);
        patient.setPatientId(patientId);
        patient.setLabelData(labelData.toJSONString());
        ctAnalysisMapper.insertPatient(patient);
    }

    public List<PatientDTO> findLabelData(String computerName, String patientId, String dateStart, String dateEnd) {
        List<Patient> list = ctAnalysisMapper.selectPatientByCondition(computerName, patientId, dateStart, dateEnd);
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
        return res;
    }

    @Override
    public void addLabelData(PatientVO patientVO) {
        ctAnalysisMapper.insertPatient(patientVO);
    }

    @Override
    public List<PatientDTO> findLabelData(PatientVO patientVO) {
        List<Patient> list = ctAnalysisMapper.selectPatientByCondition(patientVO);
        return new ArrayList<>();
    }
}
