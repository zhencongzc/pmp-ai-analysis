package com.pmp.service.patient.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.mapper.LabelDateMapper;
import com.pmp.mapper.PatientMapper;
import com.pmp.service.patient.PatientService;
import com.pmp.web.vo.PatientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = Logger.getLogger(PatientServiceImpl.class.getName());

    @Autowired
    PatientMapper patientMapper;

    @Override
    public ResponseResult<List<PatientDO>> findList(PatientVO patientVO) {
        Page<Object> page = PageHelper.startPage(patientVO.getPage(), patientVO.getPageRow(), true);
        List<PatientDO> list = patientMapper.findPatientByCondition(patientVO);
        return ResponseResult.success(list, page.getTotal());
    }

    @Override
    public PatientDO findPatientDetail(Integer id) {
        return patientMapper.selectPatientById(id);
    }
}
