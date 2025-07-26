package com.pmp.service.dicom;

import com.pixelmed.dicom.DicomException;
import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.labelData.LabelDataDTO;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.web.vo.DicomVO;
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
    ResponseResult<DicomDO> findDicomDetail(Integer id);
}
