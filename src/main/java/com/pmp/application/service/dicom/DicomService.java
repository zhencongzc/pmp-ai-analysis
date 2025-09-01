package com.pmp.application.service.dicom;

import com.pixelmed.dicom.DicomException;
import com.pmp.domain.model.dicom.DicomDO;
import com.pmp.domain.model.dicom.DicomGroupDTO;
import com.pmp.domain.model.report.ReportDO;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.interfaces.web.vo.DicomVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * Dicom处理
 */
public interface DicomService {

    /**
     * 保存患者的dicom文件
     *
     * @param file
     */
    String saveDicom(MultipartFile file) throws IOException, DicomException;

    /**
     * 查询dicom列表
     *
     * @param dicomVO
     * @return
     */
    ResponseResult<List<DicomDO>> findDicom(DicomVO dicomVO);

    /**
     * 查询dicom组列表
     *
     * @param dicomVO
     * @return
     */
    ResponseResult<List<DicomGroupDTO>> findDicomGroup(DicomVO dicomVO);

    /**
     * 查看dicom详情
     *
     * @param id
     * @return
     */
    DicomDO findDicomDetail(Integer id);

    /**
     * 根据医院唯一标识号查询dicom组详情
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    DicomGroupDTO findDicomDetailByAccessionNumber(String accessionNumber);

    /**
     * 根据医院唯一标识号查询整组图片
     *
     * @param accessionNumber 医院唯一标识号
     * @return
     */
    List<String> findGroupPicture(String accessionNumber);

    /**
     * 根据医院唯一标识号查询CT分析报告
     *
     * @param accessionNumber
     * @return
     */
    ReportDO findReport(String accessionNumber);

    /**
     * 病灶识别模型-回调接口
     * 回传是否成功标记和对应的唯一标识号
     */
    ResponseResult<String> dicomAnalysisCallback(Integer isSuccess, String accessionNumber);

    /**
     * PCI评分模型-回调接口
     * 回传分析报告
     */
    ResponseResult<String> dicomAnalysisPciCallback(ReportDO reportDO);

    /**
     * 调用模型分析dimcom文件
     *
     * @param accessionNumber
     */
    ResponseResult<String> dicomAnalysisFeign(String accessionNumber);

}
