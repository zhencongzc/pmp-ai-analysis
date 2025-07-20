package com.pmp.domain.labelData;

import lombok.Data;

/**
 * 病人CT的标注数据
 */
@Data
public class LabelDataDO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 设备名称
     */
    private String computerName;
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 标签数据
     */
    private String labelData;
    /**
     * 创建时间
     */
    private String createTime;
}