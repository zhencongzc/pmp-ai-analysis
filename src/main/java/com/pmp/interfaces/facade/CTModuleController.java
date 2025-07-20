package com.pmp.interfaces.facade;

import com.pmp.domain.base.ResponseResult;
import com.pmp.domain.labelData.LabelDataDTO;
import com.pmp.domain.labelData.LabelDataVO;
import com.pmp.service.dicom.DicomService;
import com.pmp.service.labelData.LabelDataService;
import com.pmp.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * CT管理模块
 */
@RestController
@RequestMapping("/ct-module")
public class CTModuleController {

    @Autowired
    LabelDataService labelDataService;

    @Autowired
    PatientService patientService;

    @Autowired
    DicomService dicomService;

    /**
     * 添加病人CT的标注数据
     *
     * @param labelDataVO
     * @return
     */
    @PostMapping("/labelData/add")
    public ResponseResult<String> addLabelData(@RequestBody LabelDataVO labelDataVO) {
        labelDataService.addLabelData(labelDataVO);
        return ResponseResult.success();
    }

    /**
     * 查询病人的标注数据
     *
     * @param labelDataVO
     * @return
     */
    @PostMapping("/labelData/find")
    public ResponseResult<List<LabelDataDTO>> findLabelData(@RequestBody LabelDataVO labelDataVO) {
        return labelDataService.findLabelData(labelDataVO);
    }

    /**
     * 导入CT文件
     *
     * @return
     */
    @PostMapping(value = "/dicom/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> uploadDicomFile(@RequestParam("file") MultipartFile file) {
        dicomService.saveDicom(file);
        return ResponseResult.success();
    }


}
