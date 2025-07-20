package com.pmp.domain.patient;

import lombok.Data;

/**
 * 病人个人信息
 */
@Data
public class PatientDO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 姓名
     */
    private String patientName;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private String birthDay;
    /**
     * 创建时间
     */
    private String createTime;
}