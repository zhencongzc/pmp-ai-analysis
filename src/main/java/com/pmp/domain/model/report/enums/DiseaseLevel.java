package com.pmp.domain.model.report.enums;

/**
 * 病理分级枚举
 * 描述PM腺癌的不同病理分级及其治疗策略
 */
public enum DiseaseLevel {

    /**
     * 低级别黏液性肿瘤(G1,高分化)
     */
    G1(1, "低级别黏液性肿瘤(G1,高分化)",
            "腹膜病变表现为丰富的黏液湖和少量的黏液性上皮条索(细胞占比<20%)，细胞轻度异型，与LAMN相似。",
            "细胞减灭术(CRS)、腹腔热灌注治疗(HIPEC)。"),

    /**
     * 高级别黏液腺癌(G2,中分化)
     */
    G2(2, "高级别黏液腺癌(G2,中分化)",
            "细胞占比>20%，细胞高度异型/侵袭性浸润呈锯齿状/促纤维结缔组织增生中可见不规则的腺管结构。",
            "全身化疗、细胞减灭术(CRS)、腹腔热灌注治疗(HIPEC)。"),

    /**
     * 高级别黏液腺癌(G3,低分化)
     */
    G3(3, "高级别黏液腺癌(G3,低分化)",
            "伴有印戒细胞的高级别腹膜黏液癌，印戒细胞在肿瘤中的占比为>10%。",
            "可切除患者：如果有证据显示对全身化疗有反应，则可受益于全身化疗，随后进行CRS +HIPEC治疗。" +
                    "不可切除高级别(G2或G3)：常单独采用全身化疗进行治疗。");

    /**
     * 分级编码
     */
    private final Integer code;

    /**
     * 分级名称
     */
    private final String name;

    /**
     * 分级描述
     */
    private final String description;

    /**
     * 治疗策略列表
     */
    private final String treatmentStrategies;

    /**
     * 构造方法
     *
     * @param code                分级编码
     * @param name                分级名称
     * @param description         分级描述
     * @param treatmentStrategies 治疗策略列表
     */
    DiseaseLevel(Integer code, String name, String description, String treatmentStrategies) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.treatmentStrategies = treatmentStrategies;
    }

    /**
     * 获取分级编码
     *
     * @return 分级编码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取分级名称
     *
     * @return 分级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取分级描述
     *
     * @return 分级描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取治疗策略列表
     *
     * @return 治疗策略列表
     */
    public String getTreatmentStrategies() {
        return treatmentStrategies;
    }

    /**
     * 根据编码获取对应的病理分级
     *
     * @param code 分级编码
     * @return 对应的病理分级枚举，如果未找到返回null
     */
    public static DiseaseLevel getByCode(Integer code) {
        for (DiseaseLevel grade : DiseaseLevel.values()) {
            if (grade.getCode().equals(code)) {
                return grade;
            }
        }
        return G1;
    }
}
