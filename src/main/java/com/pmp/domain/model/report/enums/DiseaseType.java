package com.pmp.domain.model.report.enums;

/**
 * 病理分类枚举
 * 描述PM腺癌的不同病理形态分类
 */
public enum DiseaseType {

    /**
     * 果冻型
     */
    JELLY(0, "果冻型", "腹部和盆腔病变以大量的果冻样黏液为主。"),

    /**
     * 板型
     */
    PLATE(1, "板型", "病变融合成板状，质地坚硬侵犯腹腔和盆腔的多个器官。"),

    /**
     * 结节型
     */
    NODULE(2, "结节型", "大量肿瘤结节弥散分布于腹部和骨盆，大小不一，大部分分布在大网膜、肠系膜和肠浆膜。"),

    /**
     * 混合型
     */
    MIXED(3, "混合型", "病变表现为各种类型混合物。");

    /**
     * 分类编码
     */
    private final Integer code;

    /**
     * 分类名称
     */
    private final String name;

    /**
     * 分类描述
     */
    private final String description;

    /**
     * 构造方法
     *
     * @param code        分类编码
     * @param name        分类名称
     * @param description 分类描述
     */
    DiseaseType(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * 获取分类编码
     *
     * @return 分类编码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取分类名称
     *
     * @return 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取分类描述
     *
     * @return 分类描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据编码获取对应的病理分类
     *
     * @param code 分类编码
     * @return 对应的病理分类枚举，如果未找到返回null
     */
    public static DiseaseType getByCode(Integer code) {
        for (DiseaseType type : DiseaseType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
