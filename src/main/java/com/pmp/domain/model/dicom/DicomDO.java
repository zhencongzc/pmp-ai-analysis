package com.pmp.domain.model.dicom;

import lombok.Data;

/**
 * CT的dicom文件
 */
@Data
public class DicomDO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 全球唯一标识符
     */
    private String sopInstanceUid;
    /**
     * 患者ID
     */
    private String patientId;
    /**
     * 患者姓名
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
     * 代表了当前dicom文件在整组内的序号
     */
    private String instanceNumber;
    /**
     * 检查日期
     */
    private String seriesDate;
    /**
     * 扫描时间
     */
    private String seriesTime;
    /**
     * 研究描述
     */
    private String studyDescription;
    /**
     * 设备类型
     * 例如：CT、MR、DX等
     */
    private String modality;
    /**
     * 状态
     * 对应的枚举类：DicomStatus
     */
    private Integer status;
    /**
     * 系列描述
     */
    private String seriesDescription;
    /**
     * 行数
     */
    private String rows;
    /**
     * 列数
     */
    private String columns;
    /**
     * dicom文件地址
     */
    private String dicomPath;
    /**
     * 图片文件地址
     */
    private String pngPath;
    /**
     * 创建时间
     */
    private String createTime;
}