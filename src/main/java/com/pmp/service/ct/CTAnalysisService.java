package com.pmp.service.ct;

import com.pmp.domain.base.ResponseResult;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;

import java.util.List;

/**
 * CT分析服务层
 */
public interface CTAnalysisService {

    /**
     * 新增病人CT的标注数据
     *
     * @param patientVO
     */
    void addLabelData(PatientVO patientVO);

    /**
     * 查询病人的标注数据
     *
     * @param patientVO
     * @return
     */
    ResponseResult<List<PatientDTO>> findLabelData(PatientVO patientVO);

}
