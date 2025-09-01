package com.pmp.application.service.patient;


import com.pmp.domain.model.patient.PatientDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.interfaces.web.vo.PatientVO;

import java.util.List;

/**
 * 患者管理
 */
public interface PatientService {

    /**
     * 查询患者列表
     * @param patientVO
     * @return
     */
    ResponseResult<List<PatientDO>> findList(PatientVO patientVO);

    /**
     * 查询患者详情
     * @param id
     * @return
     */
    PatientDO findPatientDetail(Integer id);
}
