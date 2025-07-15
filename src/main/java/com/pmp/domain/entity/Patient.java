package com.pmp.domain.entity;

import lombok.Data;

@Data
public class Patient {

    private Integer id;
    private String computerName;
    private String patientId;
    private String labelData;
    private String createTime;
}