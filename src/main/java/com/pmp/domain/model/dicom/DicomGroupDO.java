package com.pmp.domain.model.dicom;

import lombok.Data;

/**
 * CT的dicom文件组
 * 按照accessionNumber划分组
 */
@Data
public class DicomGroupDO {
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 医院唯一标识号
     * 医院为某次检查分配的唯一标识号，不同医院会重复
     */
    private String accessionNumber;
    /**
     * 研究编号
     * 医院内部使用
     */
    private String studyId;
    /**
     * 系列编号
     */
    private String seriesNumber;
    /**
     * 实例编号
     * 由所有切片文件的编号拼接而成
     */
    private String instanceNumbers;
    /**
     * CT扫描日期
     */
    private String seriesDate;
    /**
     * CT扫描时间
     */
    private String seriesTime;
    /**
     * 研究描述
     */
    private String studyDescription;
    /**
     * 成像模态
     * 例如：CT、MR、DX等
     */
    private String modality;
    /**
     * 系列描述
     */
    private String seriesDescription;
    /**
     * dicom文件地址
     */
    private String dicomPath;
    /**
     * 图片文件地址
     */
    private String pngPath;
}