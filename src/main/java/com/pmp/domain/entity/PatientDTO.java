package com.pmp.domain.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class PatientDTO {

    private Integer id;
    private String computerName;
    private String patientId;
    private JSONObject labelData;
    private String createTime;
}