package com.pmp.interfaces.facade;

import com.pmp.domain.base.ResponseResult;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;
import com.pmp.service.ct.CTAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * CT分析控制层
 */
@RestController
@RequestMapping("/ct-module")
public class CTAnalysisController {

    @Autowired
    CTAnalysisService ctAnalysisService;

    /**
     * 添加病人CT的标注数据
     *
     * @param patientVO
     * @return
     */
    @PostMapping("/labelData/add")
    public ResponseResult<String> addLabelData(@RequestBody PatientVO patientVO) {
        ctAnalysisService.addLabelData(patientVO);
        return ResponseResult.success();
    }

    /**
     * 查询病人的标注数据
     *
     * @param patientVO
     * @return
     */
    @PostMapping("/labelData/find")
    public ResponseResult<List<PatientDTO>> findLabelData(@RequestBody PatientVO patientVO) {
        return ctAnalysisService.findLabelData(patientVO);
    }

}
