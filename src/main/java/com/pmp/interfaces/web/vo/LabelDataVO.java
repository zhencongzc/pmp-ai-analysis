package com.pmp.interfaces.web.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 患者CT的标注数据
 */
@Data
public class LabelDataVO {
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
