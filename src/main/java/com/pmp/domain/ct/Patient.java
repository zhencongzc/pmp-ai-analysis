package com.pmp.domain.ct;

import lombok.Data;

/**
 * 病人CT标注数据
 */
@Data
public class Patient {
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