package com.pmp.controller;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.io.DicomInputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DicomConverter {
    public static void main(String[] args) {
        try {
            convertDicomToImage("path/to/your/dicom_file.dcm", "output_image.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertDicomToImage(String dicomPath, String outputPath) throws IOException {
        // 读取DICOM文件
        File dicomFile = new File(dicomPath);
        DicomInputStream dis = new DicomInputStream(dicomFile);
        Attributes attrs = dis.readDataset(-1, -1);
        dis.close();

        // 获取图像
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("PNG");
        ImageWriter writer = writers.next();

        BufferedImage image = ImageIO.read(dicomFile);

        // 保存图像
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        File output = new File(outputPath);
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), writeParam);
        }
        writer.dispose();
        System.out.println("图像已保存至: " + outputPath);
    }
}