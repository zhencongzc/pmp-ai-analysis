package com.pmp.mapper;

import com.pmp.domain.patient.PatientDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper {

    /**
     * 新增病人个人信息
     *
     * @param patientDO
     */
    void insertPatient(PatientDO patientDO);

    /**
     * 根据病人ID查询病人个人信息
     *
     * @param patientDO
     * @return
     */
    PatientDO selectPatientById(PatientDO patientDO);

}