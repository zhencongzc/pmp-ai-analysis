package com.pmp.controller;

import com.pmp.base.ResponseResult;
import com.pmp.domain.entity.PatientDTO;
import com.pmp.service.CTAnalysisService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * CT分析控制层
 */
@RestController
@RequestMapping("/ct-module")
public class CTAnalysisController {

    @Resource
    CTAnalysisService ctAnalysisService;

    @PostMapping("/labelData/add")
    public ResponseResult<String> addLabelData(@RequestBody JSONObject param) {
        ctAnalysisService.addLabelData(
                param.getString("computerName"),
                param.getString("patientId"),
                param.getJSONObject("labelData"));
        return ResponseResult.success();
    }

    @PostMapping("/labelData/find")
    public ResponseResult<List<PatientDTO>> findLabelData(@RequestBody JSONObject param) {
        List<PatientDTO> list = ctAnalysisService.findLabelData(
                param.getString("computerName"),
                param.getString("patientId"),
                param.getString("dateStart"),
                param.getString("dateEnd"));
        return ResponseResult.success(list, list.size());
    }

}
