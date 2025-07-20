package com.pmp.service.dicom;

import org.springframework.web.multipart.MultipartFile;


/**
 * Dicom处理
 */
public interface DicomService {

    /**
     * 保存病人的dicom文件
     *
     * @param file
     */
    void saveDicom(MultipartFile file);
}
