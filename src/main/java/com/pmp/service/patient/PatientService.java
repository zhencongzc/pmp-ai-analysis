package com.pmp.service.patient;


import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.web.vo.PatientVO;

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
