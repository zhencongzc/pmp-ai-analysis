package com.pmp.application.service.patient.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.domain.model.patient.PatientDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.infrastructure.mapper.PatientMapper;
import com.pmp.application.service.patient.PatientService;
import com.pmp.interfaces.web.vo.PatientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = Logger.getLogger(PatientServiceImpl.class.getName());

    private final PatientMapper patientMapper;

    /**
     * 查询患者列表
     *
     * @param patientVO
     * @return
     */
    @Override
    public ResponseResult<List<PatientDO>> findList(PatientVO patientVO) {
        Page<Object> page = PageHelper.startPage(patientVO.getPage(), patientVO.getPageRow(), true);
        List<PatientDO> list = patientMapper.findPatientByCondition(patientVO);
        return ResponseResult.success(list, page.getTotal());
    }

    /**
     * 查询患者详情
     *
     * @param id
     * @return
     */
    @Override
    public PatientDO findPatientDetail(Integer id) {
        return patientMapper.selectPatientById(id);
    }
}
