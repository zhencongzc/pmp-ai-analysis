package com.pmp.interfaces.web.vo;

import lombok.Data;

/**
 * Dicom文件
 *
 * @author zhenc
 */
@Data
public class DicomVO {
    /**
     * dicomID
     */
    private Integer id;
    /**
     * 病人ID
     */
    private String patientId;
    /**
     * 病人名称
     */
    private String patientName;
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
