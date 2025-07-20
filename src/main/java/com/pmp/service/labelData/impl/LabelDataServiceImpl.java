package com.pmp.service.labelData.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pmp.infrastructure.base.ResponseResult;
import com.pmp.domain.labelData.LabelDataDO;
import com.pmp.domain.labelData.LabelDataDTO;
import com.pmp.web.vo.LabelDataVO;
import com.pmp.mapper.LabelDateMapper;
import com.pmp.service.labelData.LabelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LabelDataServiceImpl implements LabelDataService {

    private static final Logger logger = Logger.getLogger(LabelDataServiceImpl.class.getName());

    @Autowired
    LabelDateMapper labelDateMapper;

    @Override
    public void addLabelData(LabelDataVO labelDataVO) {
        LabelDataDO labelDataDO = new LabelDataDO();
        labelDataDO.setComputerName(labelDataVO.getComputerName());
        labelDataDO.setPatientId(labelDataVO.getPatientId());
        labelDataDO.setLabelData(labelDataVO.getLabelData().toJSONString());
        labelDateMapper.insertLabelData(labelDataDO);
    }

    @Override
    public ResponseResult<List<LabelDataDTO>> findLabelData(LabelDataVO labelDataVO) {
        Page<Object> page = PageHelper.startPage(labelDataVO.getPage(), labelDataVO.getPageRow(), true);
        List<LabelDataDO> list = labelDateMapper.selectLabelDataByCondition(labelDataVO);
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
