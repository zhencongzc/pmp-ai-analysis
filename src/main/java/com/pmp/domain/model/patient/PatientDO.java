package com.pmp.domain.model.patient;

import lombok.Data;

/**
 * 患者个人信息
 */
@Data
public class PatientDO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 患者ID
     */
    private String patientId;
    /**
     * 姓名
     */
    private String patientName;
    /**
     * 性别 M-男，F-女
     */
    private String sex;
    /**
     * 出生日期
     */
    private String birthDay;
    /**
     * 创建时间
     */
    private String createTime;
}