package com.pmp.domain.model.dicom.enums;

/**
 * DICOM状态枚举
 */
public enum DicomStatus {
    PENDING(0, "待处理"),
    PROCESSING(1, "分析中"),
    SUCCESS(2, "分析成功"),
    FAILED(3, "分析失败");

    private final Integer code;
    private final String description;

    DicomStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static DicomStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (DicomStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}