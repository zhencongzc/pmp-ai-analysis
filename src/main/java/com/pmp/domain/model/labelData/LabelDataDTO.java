package com.pmp.domain.model.labelData;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 患者CT的标注数据
 */
@Data
public class LabelDataDTO {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 设备名称
     */
    private String computerName;
    /**
     * 患者ID
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