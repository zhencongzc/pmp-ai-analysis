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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
