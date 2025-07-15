package com.pmp.service;

import com.alibaba.fastjson.JSONObject;
import com.pmp.domain.entity.Patient;
import com.pmp.domain.entity.PatientDTO;
import com.pmp.domain.mapper.CTAnalysisMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CTAnalysisService {

    @Resource
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
}
