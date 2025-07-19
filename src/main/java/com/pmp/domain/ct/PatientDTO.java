package com.pmp.domain.ct;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 病人CT标注数据DTO
 */
@Data
public class PatientDTO {

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
    private JSONObject labelData;
    /**
     * 创建时间
     */
    private String createTime;

}