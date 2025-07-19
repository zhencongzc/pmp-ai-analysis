package com.pmp.interfaces.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class PatientVO {
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
     * 开始时间
     */
    private String dateStart;
    /**
     * 结束时间
     */
    private String dateEnd;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer pageRow;
}
