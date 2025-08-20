package com.pmp.interfaces.web.vo;

import lombok.Data;

/**
 * 患者
 */
@Data
public class PatientVO {
    /**
     * 患者主键ID
     */
    private Integer id;
    /**
     * 患者ID
     */
    private String patientId;
    /**
     * 患者名称
     */
    private String patientName;
    /**
     * 患者性别
     */
    private String sex;
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
