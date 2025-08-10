package com.pmp.interfaces.web;

import com.pixelmed.dicom.DicomException;
import com.pmp.domain.model.auth.RequiresRoles;
import com.pmp.domain.model.dicom.DicomDO;
import com.pmp.domain.model.report.ReportDO;
import com.pmp.common.pojo.ResponseCode;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.domain.model.labelData.LabelDataDTO;
import com.pmp.interfaces.web.assembler.ReportConverter;
import com.pmp.interfaces.web.vo.DicomVO;
import com.pmp.interfaces.web.vo.LabelDataVO;
import com.pmp.application.service.dicom.DicomService;
import com.pmp.application.service.labelData.LabelDataService;
import com.pmp.application.service.patient.PatientService;
import com.pmp.interfaces.web.vo.ReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * CT管理模块
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ct-module")
public class CTModuleController {

    private final LabelDataService labelDataService;
    private final PatientService patientService;
    private final DicomService dicomService;

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
     * 批量上传dicom文件
     *
     * @return
     */
    @RequiresRoles("admin")
    @PostMapping(value = "/dicom/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult<String> uploadDicomFile(@RequestParam("file") MultipartFile file[]) {
        for (MultipartFile f : file) {
            try {
                dicomService.saveDicom(f);
            } catch (IOException | DicomException e) {
                e.printStackTrace();
                return ResponseResult.error(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
            }
        }
        return ResponseResult.success();
    }

    /**
     * 查询dicom列表
     *
     * @param dicomVO
     * @return
     */
    @PostMapping("/dicom/find")
    public ResponseResult<List<DicomDO>> findDicom(@RequestBody DicomVO dicomVO) {
        return dicomService.findDicom(dicomVO);
    }

    /**
     * 查看dicom详情
     *
     * @param id dicom文件主键
     * @return
     */
    @GetMapping("/dicom/detail")
    public ResponseResult<DicomDO> findDicomDetail(@RequestParam Integer id) {
        DicomDO res = dicomService.findDicomDetail(id);
        return ResponseResult.success(res);
    }

    /**
     * 预览图片
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    @RequiresRoles({"admin", "user"})
    @GetMapping("/dicom/findGroupPicture")
    public ResponseResult<List<String>> findGroupPicture(@RequestParam String accessionNumber) {
        List<String> groupData = dicomService.findGroupPicture(accessionNumber);
        return ResponseResult.success(groupData);
    }

    /**
     * 分析报告
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    @RequiresRoles({"admin", "user"})
    @GetMapping("/dicom/findReport")
    public ResponseResult<ReportVO> findReport(@RequestParam String accessionNumber) {
        // 获取DO对象
        ReportDO reportDO = dicomService.findReport(accessionNumber);

        // 转换为VO对象
        ReportVO reportVO = ReportConverter.toVO(reportDO);

        return ResponseResult.success(reportVO);
    }
}
