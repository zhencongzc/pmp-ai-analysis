package com.pmp.mapper;

import com.pmp.domain.labelData.LabelDataDO;
import com.pmp.domain.labelData.LabelDataVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LabelDateMapper {

    /**
     * 新增病人CT的标注数据
     *
     * @param labelDataDO
     */
    void insertLabelData(LabelDataDO labelDataDO);

    /**
     * 根据条件查询病人CT的标注数据
     *
     * @param labelDataVO
     * @return
     */
    List<LabelDataDO> selectLabelDataByCondition(LabelDataVO labelDataVO);

}