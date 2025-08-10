package com.pmp.interfaces.web.assembler;

import com.pmp.domain.model.report.ReportDO;
import com.pmp.domain.model.report.enums.DiseaseLevel;
import com.pmp.domain.model.report.enums.DiseaseType;
import com.pmp.interfaces.web.vo.ReportVO;
import org.springframework.stereotype.Component;

/**
 * 报告数据转换器
 * 用于在ReportDO和ReportVO之间进行数据转换
 */
@Component
public class ReportConverter {

    /**
     * 将ReportDO转换为ReportVO
     *
     * @param reportDO 报告数据库对象
     * @return 报告视图对象
     */
    public static ReportVO toVO(ReportDO reportDO) {
        if (reportDO == null) {
            return null;
        }

        ReportVO reportVO = new ReportVO();
        reportVO.setId(reportDO.getId());
        reportVO.setPatientId(reportDO.getPatientId());
        reportVO.setPatientName(reportDO.getPatientName());
        reportVO.setAccessionNumber(reportDO.getAccessionNumber());
        reportVO.setPositiveRate(reportDO.getPositiveRate());
        reportVO.setIsPositive(reportDO.getIsPositive());
        reportVO.setDiseaseType(DiseaseType.getByCode(reportDO.getDiseaseType()).getName());
        reportVO.setDiseaseLevel(DiseaseLevel.getByCode(reportDO.getDiseaseLevel()).getName());
        reportVO.setMesentericContracture(reportDO.getMesentericContracture());
        reportVO.setPci0Central(reportDO.getPci0Central());
        reportVO.setPci1RightUpper(reportDO.getPci1RightUpper());
        reportVO.setPci2Epigastrium(reportDO.getPci2Epigastrium());
        reportVO.setPci3LeftUpper(reportDO.getPci3LeftUpper());
        reportVO.setPci4LeftFlank(reportDO.getPci4LeftFlank());
        reportVO.setPci5LeftLower(reportDO.getPci5LeftLower());
        reportVO.setPci6Pelvis(reportDO.getPci6Pelvis());
        reportVO.setPci7RightLower(reportDO.getPci7RightLower());
        reportVO.setPci8RightFlank(reportDO.getPci8RightFlank());
        reportVO.setPci9UpperJejunum(reportDO.getPci9UpperJejunum());
        reportVO.setPci10LowerJejunum(reportDO.getPci10LowerJejunum());
        reportVO.setPci11UpperIleum(reportDO.getPci11UpperIleum());
        reportVO.setPci12LowerIleum(reportDO.getPci12LowerIleum());
        reportVO.setPciScore(reportDO.getPciScore());
        reportVO.setConclusion(reportDO.getConclusion());
        reportVO.setCreateTime(reportDO.getCreateTime());

        return reportVO;
    }
}
