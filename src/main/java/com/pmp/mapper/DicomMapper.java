package com.pmp.mapper;

import com.pmp.domain.dicom.DicomDO;
import com.pmp.domain.patient.PatientDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DicomMapper {


    /**
     * 新增Dicom数据
     *
     * @param dicomDO
     */
    void insertDicom(DicomDO dicomDO);

    /**
     * 根据唯一识别码查询Dicom文件
     * @param dicomDO
     * @return
     */
    DicomDO selectDicomBySopInstanceUid(DicomDO dicomDO);

}