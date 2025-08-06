package com.pmp.infrastructure.mapper;

import com.pmp.domain.model.patient.PatientDO;
import com.pmp.interfaces.web.vo.PatientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientMapper {

    /**
     * 新增病人个人信息
     *
     * @param patientDO
     */
    void insertPatient(PatientDO patientDO);

    /**
     * 根据病人主键ID查询病人个人信息
     *
     * @param id
     * @return
     */
    PatientDO selectPatientById(Integer id);

    /**
     * 根据病人ID查询病人个人信息
     *
     * @param patientDO
     * @return
     */
    PatientDO selectPatientByPatientId(PatientDO patientDO);

    /**
     * 根据条件查询病人列表
     *
     * @param patientVO
     * @return
     */
    List<PatientDO> findPatientByCondition(PatientVO patientVO);


}