package com.pmp.application.service.labelData;

import com.pmp.common.pojo.ResponseResult;
import com.pmp.domain.model.labelData.LabelDataDTO;
import com.pmp.interfaces.web.vo.LabelDataVO;

import java.util.List;

/**
 * CT标注数据
 */
public interface LabelDataService {

    /**
     * 新增患者CT的标注数据
     *
     * @param labelDataVO
     */
    void addLabelData(LabelDataVO labelDataVO);

    /**
     * 查询患者CT的标注数据
     *
     * @param labelDataVO
     * @return
     */
    ResponseResult<List<LabelDataDTO>> findLabelData(LabelDataVO labelDataVO);

}
