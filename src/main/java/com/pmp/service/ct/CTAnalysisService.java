package com.pmp.service.ct;

import com.alibaba.fastjson.JSONObject;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CTAnalysisService {

    void addLabelData(String computerName, String patientId, JSONObject labelData);

    List<PatientDTO> findLabelData(String computerName, String patientId, String dateStart, String dateEnd);

    void addLabelData(PatientVO patientVO);

    List<PatientDTO> findLabelData(PatientVO patientVO);

}
