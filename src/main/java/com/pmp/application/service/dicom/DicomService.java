package com.pmp.application.service.dicom;

import com.pixelmed.dicom.DicomException;
import com.pmp.domain.model.dicom.DicomDO;
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
     * 保存病人的dicom文件
     *
     * @param file
     */
    void saveDicom(MultipartFile file) throws IOException, DicomException;

    /**
     * 查询dicom列表
     *
     * @param dicomVO
     * @return
     */
    ResponseResult<List<DicomDO>> findDicom(DicomVO dicomVO);

    /**
     * 查看dicom详情
     *
     * @param id
     * @return
     */
    DicomDO findDicomDetail(Integer id);

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
}
