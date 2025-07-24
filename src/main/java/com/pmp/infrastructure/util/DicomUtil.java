package com.pmp.infrastructure.util;

import com.pmp.domain.dicom.DicomDO;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReader;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReaderSpi;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Dicom工具类
 */
public class DicomUtil {

    /**
     * 在当前路径，将dicom文件转为png
     *
     * @param dicomFilePath
     * @param outputPngPath
     * @throws IOException
     */
    public static void convert(String dicomFilePath, String outputPngPath) throws IOException {
        // 检查输入文件是否存在
        File dicomFile = new File(dicomFilePath);
        if (!dicomFile.exists() || !dicomFile.isFile()) {
            throw new IOException("DICOM 文件不存在: " + dicomFilePath);
        }

        // 创建 DICOM 图像读取器
        DicomImageReaderSpi readerSpi = new DicomImageReaderSpi();
        DicomImageReader reader = new DicomImageReader(readerSpi);

        // 使用 try-with-resources 确保 ImageInputStream 被关闭
        try (ImageInputStream iis = ImageIO.createImageInputStream(dicomFile)) {
            // 设置输入
            reader.setInput(iis);

            // 关键步骤：读取 DICOM 元数据，校验尺寸
            Attributes dataset = reader.getStreamMetadata().getAttributes(); // 获取 DICOM 数据集
            int rows = dataset.getInt(Tag.Rows, -1); // 读取行数（DICOM 标签：0028,0010）
            int columns = dataset.getInt(Tag.Columns, -1); // 读取列数（DICOM 标签：0028,0011）

            // 校验尺寸有效性
            if (rows <= 0 || columns <= 0) {
                throw new IOException("DICOM 文件元数据无效：行数=" + rows + "，列数=" + columns + "（必须为正数）");
            }

            // 读取 DICOM 图像
            BufferedImage image = reader.read(0);
            // 直接写入 PNG 文件（简化版）
            ImageIO.write(image, "PNG", new File(outputPngPath));
        } finally {
            reader.dispose(); // 释放 DICOM 读取器资源
        }
    }

    /**
     * 将dicom数据转为对象
     *
     * @param attributes
     * @return
     */
    public static DicomDO changeAttributesToDicom(Attributes attributes) {
        DicomDO dicomDO = new DicomDO();
        dicomDO.setSopInstanceUid(attributes.getString(Tag.SOPInstanceUID));
        dicomDO.setPatientId(attributes.getString(Tag.PatientID));
        dicomDO.setPatientName(attributes.getString(Tag.PatientName));
        dicomDO.setAccessionNumber(attributes.getString(Tag.AccessionNumber));
        dicomDO.setStudyId(attributes.getString(Tag.StudyID));
        dicomDO.setSeriesNumber(attributes.getString(Tag.SeriesNumber));
        dicomDO.setInstanceNumber(attributes.getString(Tag.InstanceNumber));
        dicomDO.setSeriesDate(attributes.getString(Tag.SeriesDate));
        dicomDO.setSeriesTime(attributes.getString(Tag.SeriesTime));
        dicomDO.setStudyDescription(attributes.getString(Tag.StudyDescription));
        dicomDO.setModality(attributes.getString(Tag.Modality));
        dicomDO.setSeriesDescription(attributes.getString(Tag.SeriesDescription));
        dicomDO.setRows(attributes.getString(Tag.Rows));
        dicomDO.setColumns(attributes.getString(Tag.Columns));
        return dicomDO;
    }
}
