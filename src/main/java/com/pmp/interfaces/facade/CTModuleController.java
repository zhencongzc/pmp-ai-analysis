package com.pmp.interfaces.facade;

import com.pmp.domain.base.ResponseResult;
import com.pmp.domain.ct.PatientDTO;
import com.pmp.interfaces.vo.PatientVO;
import com.pmp.service.ct.CTAnalysisService;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CT管理模块控制层
 */
@RestController
@RequestMapping("/ct-module")
public class CTModuleController {

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

    /**
     * 导入CT文件
     *
     * @return
     */
    @PostMapping(value = "/dicom/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> uploadDicomFile(@RequestParam("file") MultipartFile file) {
        ctAnalysisService.saveDicom(file);
        return ResponseResult.success();
    }


}
