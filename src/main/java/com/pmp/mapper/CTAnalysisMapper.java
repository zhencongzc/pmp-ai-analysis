package com.pmp.mapper;

import com.pmp.domain.ct.Patient;
import com.pmp.interfaces.vo.PatientVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CTAnalysisMapper {

    void insertPatient(Patient patient);

    List<Patient> selectPatientByCondition(PatientVO patientVO);

}