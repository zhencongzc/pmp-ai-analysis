package com.pmp.infrastructure.mapper;

import com.pmp.domain.model.labelData.LabelDataDO;
import com.pmp.interfaces.web.vo.LabelDataVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LabelDataMapper {

    /**
     * 新增患者CT的标注数据
     *
     * @param labelDataDO
     */
    void insertLabelData(LabelDataDO labelDataDO);

    /**
     * 根据条件查询患者CT的标注数据
     *
     * @param labelDataVO
     * @return
     */
    List<LabelDataDO> selectLabelDataByCondition(LabelDataVO labelDataVO);

}