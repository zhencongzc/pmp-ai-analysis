package com.pmp.application.service.labelData.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.common.pojo.ResponseResult;
import com.pmp.domain.model.labelData.LabelDataDO;
import com.pmp.domain.model.labelData.LabelDataDTO;
import com.pmp.interfaces.web.vo.LabelDataVO;
import com.pmp.infrastructure.mapper.LabelDataMapper;
import com.pmp.application.service.labelData.LabelDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class LabelDataServiceImpl implements LabelDataService {

    private static final Logger logger = Logger.getLogger(LabelDataServiceImpl.class.getName());

    private final LabelDataMapper labelDataMapper;

    /**
     * 新增病人CT的标注数据
     *
     * @param labelDataVO
     */
    @Override
    public void addLabelData(LabelDataVO labelDataVO) {
        LabelDataDO labelDataDO = new LabelDataDO();
        labelDataDO.setComputerName(labelDataVO.getComputerName());
        labelDataDO.setPatientId(labelDataVO.getPatientId());
        labelDataDO.setLabelData(labelDataVO.getLabelData().toJSONString());
        labelDataMapper.insertLabelData(labelDataDO);
    }

    /**
     * 查询病人CT的标注数据
     *
     * @param labelDataVO
     * @return
     */
    @Override
    public ResponseResult<List<LabelDataDTO>> findLabelData(LabelDataVO labelDataVO) {
        Page<Object> page = PageHelper.startPage(labelDataVO.getPage(), labelDataVO.getPageRow(), true);
        List<LabelDataDO> list = labelDataMapper.selectLabelDataByCondition(labelDataVO);
        //转换对象
        List<LabelDataDTO> res = new ArrayList<>();
        list.forEach(a -> {
            LabelDataDTO labelDataDTO = new LabelDataDTO();
            labelDataDTO.setId(a.getId());
            labelDataDTO.setComputerName(a.getComputerName());
            labelDataDTO.setPatientId(a.getPatientId());
            labelDataDTO.setLabelData(JSONObject.parseObject(a.getLabelData()));
            labelDataDTO.setCreateTime(a.getCreateTime());
            res.add(labelDataDTO);
        });
        return ResponseResult.success(res, page.getTotal());
    }

}
