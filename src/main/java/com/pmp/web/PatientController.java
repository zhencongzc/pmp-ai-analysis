package com.pmp.web;

import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.pojo.ResponseCode;
import com.pmp.infrastructure.pojo.ResponseResult;
import com.pmp.service.patient.PatientService;
import com.pmp.web.vo.PatientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 病患管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    /**
     * 查询病人列表
     *
     * @param patientVO 病人信息
     * @return
     */
    @PostMapping("/findList")
    public ResponseResult<List<PatientDO>> findList(@RequestBody PatientVO patientVO) {
        ResponseCode.SUCCESS.getMessage();
        return patientService.findList(patientVO);

    }

    /**
     * 查看病人详情
     *
     * @param id 病人主键ID
     * @return
     */
    @GetMapping("/detail")
    public ResponseResult<PatientDO> findPatientDetail(@RequestParam Integer id) {
        PatientDO res = patientService.findPatientDetail(id);
        return ResponseResult.success(res);
    }
}
