package com.pmp.domain.model.dicom;

import lombok.Data;

/**
 * CT的dicom文件组
 * 按照accessionNumber划分组
 */
@Data
public class DicomGroupDTO {
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
     * 图像数
     */
    private Integer instanceCount;
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
     * 0-未处理、1-分析完成、2-处理中、3-处理失败
     */
    private Integer status;
    /**
     * 系列描述
     */
    private String seriesDescription;
    /**
     * 创建时间
     */
    private String createTime;
}