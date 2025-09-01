package com.pmp.interfaces.web;

import com.pmp.domain.model.patient.PatientDO;
import com.pmp.common.pojo.ResponseCode;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.application.service.patient.PatientService;
import com.pmp.interfaces.web.vo.PatientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 患者管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    /**
     * 查询患者列表
     *
     * @param patientVO 患者信息
     * @return
     */
    @PostMapping("/findList")
    public ResponseResult<List<PatientDO>> findList(@RequestBody PatientVO patientVO) {
        return patientService.findList(patientVO);

    }

    /**
     * 查看患者详情
     *
     * @param id 患者主键ID
     * @return
     */
    @GetMapping("/detail")
    public ResponseResult<PatientDO> findPatientDetail(@RequestParam Integer id) {
        PatientDO res = patientService.findPatientDetail(id);
        return ResponseResult.success(res);
    }
}
