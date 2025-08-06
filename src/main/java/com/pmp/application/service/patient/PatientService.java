package com.pmp.application.service.patient;


import com.pmp.domain.model.patient.PatientDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.interfaces.web.vo.PatientVO;

import java.util.List;

/**
 * 病患管理
 */
public interface PatientService {

    /**
     * 查询病人列表
     * @param patientVO
     * @return
     */
    ResponseResult<List<PatientDO>> findList(PatientVO patientVO);

    /**
     * 查询病人详情
     * @param id
     * @return
     */
    PatientDO findPatientDetail(Integer id);
}
