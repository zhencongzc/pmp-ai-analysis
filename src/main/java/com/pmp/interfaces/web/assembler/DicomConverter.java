package com.pmp.interfaces.web.assembler;

import com.pmp.domain.model.dicom.DicomGroupDTO;
import com.pmp.domain.model.dicom.enums.DicomStatus;
import com.pmp.interfaces.web.vo.DicomGroupVO;
import org.springframework.stereotype.Component;

/**
 * DICOM数据转换器
 * 用于在DicomGroupDTO和DicomGroupVO之间进行数据转换
 */
@Component
public class DicomConverter {

    /**
     * 将DicomGroupDTO转换为DicomGroupVO
     *
     * @param dicomGroupDTO DICOM组数据传输对象
     * @return DICOM组视图对象
     */
    public static DicomGroupVO toVO(DicomGroupDTO dicomGroupDTO) {
        if (dicomGroupDTO == null) {
            return null;
        }

        DicomGroupVO dicomGroupVO = new DicomGroupVO();
        dicomGroupVO.setPatientId(dicomGroupDTO.getPatientId());
        dicomGroupVO.setPatientName(dicomGroupDTO.getPatientName());
        dicomGroupVO.setStudyId(dicomGroupDTO.getStudyId());
        dicomGroupVO.setAccessionNumber(dicomGroupDTO.getAccessionNumber());
        dicomGroupVO.setInstanceCount(dicomGroupDTO.getInstanceCount());
        dicomGroupVO.setSeriesDate(dicomGroupDTO.getSeriesDate());
        dicomGroupVO.setModality(dicomGroupDTO.getModality());
        // 使用枚举转换状态
        if (dicomGroupDTO.getStatus() != null) {
            DicomStatus status = DicomStatus.getByCode(dicomGroupDTO.getStatus());
            if (status != null) {
                dicomGroupVO.setStatus(status.getDescription());
            }
        }
        dicomGroupVO.setCreateTime(dicomGroupDTO.getCreateTime());
        return dicomGroupVO;
    }
}