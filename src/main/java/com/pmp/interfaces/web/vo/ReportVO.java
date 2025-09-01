package com.pmp.interfaces.web.vo;

import lombok.Data;

/**
 * CT分析报告
 */
@Data
public class ReportVO {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 患者ID
     */
    private String patientId;

    /**
     * 患者姓名
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
     * 病理分类
     */
    private String diseaseType;

    /**
     * 病理分级
     */
    private String diseaseLevel;

    /**
     * 肠系膜挛缩
     */
    private Integer mesentericContracture;

    /**
     * PCI评分：0_中央区域
     */
    private Integer pci0Central;

    /**
     * PCI评分：1_右上区域
     */
    private Integer pci1RightUpper;

    /**
     * PCI评分：2_上腹部区域
     */
    private Integer pci2Epigastrium;

    /**
     * PCI评分：3_左上区域
     */
    private Integer pci3LeftUpper;

    /**
     * PCI评分：4_左侧腹部区域
     */
    private Integer pci4LeftFlank;

    /**
     * PCI评分：5_左下区域
     */
    private Integer pci5LeftLower;

    /**
     * PCI评分：6_盆腔区域
     */
    private Integer pci6Pelvis;

    /**
     * PCI评分：7_右下区域
     */
    private Integer pci7RightLower;

    /**
     * PCI评分：8_右侧腹部区域
     */
    private Integer pci8RightFlank;

    /**
     * PCI评分：9_空肠上部区域
     */
    private Integer pci9UpperJejunum;

    /**
     * PCI评分：10_空肠下部区域
     */
    private Integer pci10LowerJejunum;

    /**
     * PCI评分：11_回肠上部区域
     */
    private Integer pci11UpperIleum;

    /**
     * PCI评分：12_回肠下部区域
     */
    private Integer pci12LowerIleum;

    /**
     * PCI总分
     */
    private Integer pciScore;

    /**
     * 结论
     */
    private String conclusion;

    /**
     * 创建时间
     */
    private String createTime;
}