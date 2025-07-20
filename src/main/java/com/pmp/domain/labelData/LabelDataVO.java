package com.pmp.domain.labelData;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 病人CT的标注数据VO
 */
@Data
public class LabelDataVO {
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
