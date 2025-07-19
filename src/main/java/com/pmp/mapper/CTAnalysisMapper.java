package com.pmp.mapper;

import com.pmp.domain.ct.Patient;
import com.pmp.interfaces.vo.PatientVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CTAnalysisMapper {

    /**
     * 新增患者信息
     */
    @Insert("INSERT INTO ct_label_data (id, computer_name, patient_id, label_data, create_time) " +
            "VALUES (null, #{a.computerName}, #{a.patientId}, #{a.labelData}, now())")
    int insertPatient(@Param("a") Patient patient);

    /**
     * 根据patientId查询患者信息
     */
    Patient selectPatientById(String patientId);

    @Select({
            "<script>",
            "SELECT id, computer_name computerName, patient_id patientId, label_data labelData, create_time createTime",
            "FROM ct_label_data",
            "<where>",
            "    <if test=\"computerName != null\">",
            "        AND computer_name = #{computerName}",
            "    </if>",
            "    <if test=\"patientId != null\">",
            "        AND patient_id = #{patientId}",
            "    </if>",
            "    <if test=\"dateStart != null and dateEnd != null\">",
            "        AND create_time BETWEEN #{dateStart} AND #{dateEnd}",
            "    </if>",
            "</where>",
            "</script>"
    })
    List<Patient> selectPatientByCondition(String computerName, String patientId, String dateStart, String dateEnd);

    void insertPatient(PatientVO patientVO);

    List<Patient> selectPatientByCondition(PatientVO patientVO);
}