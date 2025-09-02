package com.pmp.infrastructure.mapper;

import com.pmp.domain.model.dicom.DicomDO;
import com.pmp.domain.model.dicom.DicomGroupDTO;
import com.pmp.domain.model.report.ReportDO;
import com.pmp.interfaces.web.vo.DicomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DicomMapper {


    /**
     * 新增Dicom数据
     *
     * @param dicomDO
     */
    void insertDicom(DicomDO dicomDO);

    /**
     * 根据唯一识别码查询Dicom文件
     *
     * @param dicomDO
     * @return
     */
    DicomDO selectDicomBySopInstanceUid(DicomDO dicomDO);

    /**
     * 根据条件查询dicom列表
     *
     * @param dicomVO
     * @return
     */
    List<DicomDO> findDicomByCondition(DicomVO dicomVO);

    /**
     * 根据id查询dicom文件
     *
     * @param dicomVO
     */
    DicomDO findDicomById(DicomVO dicomVO);

    /**
     * 根据医院唯一标识号查询Dicom文件
     *
     * @param accessionNumber
     * @return
     */
    DicomDO findDicomByAccessionNumber(String accessionNumber);

    /**
     * 根据医院唯一标识号查询整组图片
     *
     * @param accessionNumber
     * @return
     */
    List<String> findGroupPictureByAccessionNumber(String accessionNumber);

    /**
     * 根据医院唯一标识号查询CT分析报告
     *
     * @param accessionNumber
     * @return
     */
    ReportDO findReport(String accessionNumber);

    /**
     * 新增CT分析报告
     *
     * @param reportDO
     */
    void insertReport(ReportDO reportDO);

    /**
     * 根据医院唯一标识号查询Dicom组信息
     *
     * @param accessionNumber
     * @return
     */
    DicomGroupDTO findDicomGroupByAccessionNumber(String accessionNumber);

    /**
     * 根据条件查询Dicom组信息
     *
     * @param dicomVO
     * @return
     */
    List<DicomGroupDTO> findDicomGroupByCondition(DicomVO dicomVO);

    /**
     * 更新Dicom组信息
     *
     * @param status
     * @param accessionNumber
     */
    void updateDicomGroupStatus(@Param("status") Integer status, @Param("accessionNumber") String accessionNumber);

    /**
     * 删除Dicom组信息
     *
     * @param accessionNumber
     */
    void deleteDicom(String accessionNumber);
}