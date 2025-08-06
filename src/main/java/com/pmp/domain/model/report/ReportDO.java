package com.pmp.domain.model.report;

import lombok.Data;

/**
 * CT分析报告
 */
@Data
public class ReportDO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 病人ID
     */
    private String patientId;

    /**
     * 病人姓名
     */
    private String patientName;

    /**
     * 医院唯一标识号（一组CT）
     */
    private String accessionNumber;

    /**
     * 是否阳性（0-阴性，1-阳性）
     */
    private Integer isPositive;

    /**
     * 阳性概率
     */
    private Double positiveRate;

    /**
     * PCI评分：中央区域
     */
    private Integer pci0Central;

    /**
     * PCI评分：右上区域
     */
    private Integer pci1RightUpper;

    /**
     * PCI评分：上腹部区域
     */
    private Integer pci2Epigastrium;

    /**
     * PCI评分：左上区域
     */
    private Integer pci3LeftUpper;

    /**
     * PCI评分：左侧腹部区域
     */
    private Integer pci4LeftFlank;

    /**
     * PCI评分：左下区域
     */
    private Integer pci5LeftLower;

    /**
     * PCI评分：盆腔区域
     */
    private Integer pci6Pelvis;

    /**
     * PCI评分：右下区域
     */
    private Integer pci7RightLower;

    /**
     * PCI评分：右侧腹部区域
     */
    private Integer pci8RightFlank;

    /**
     * PCI评分：空肠上部区域
     */
    private Integer pci9UpperJejunum;

    /**
     * PCI评分：空肠下部区域
     */
    private Integer pci10LowerJejunum;

    /**
     * PCI评分：回肠上部区域
     */
    private Integer pci11UpperIleum;

    /**
     * PCI评分：回肠下部区域
     */
    private Integer pci12LowerIleum;

    /**
     * PCI总分
     */
    private Integer pciScore;

    /**
     * 创建时间
     */
    private String createTime;
}