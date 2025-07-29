package com.pmp.web;

import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.patient.PatientDO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.service.patient.PatientService;
import com.pmp.web.vo.PatientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 病患管理
 */
@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    /**
     * 查询病人列表
     *
     * @param patientVO
     * @return
     */
    @PostMapping("/findList")
    public ResponseResult<List<PatientDO>> findList(@RequestBody PatientVO patientVO) {
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
