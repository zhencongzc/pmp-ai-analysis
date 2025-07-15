//package com.pmp.controller;
//
//import org.dcm4che3.data.Attributes;
//import org.dcm4che3.io.DicomInputStream;
//import org.dcm4che3.io.DicomOutputStream;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//public class DicomAnnotator {
//
//    public static void main(String[] args) {
//        try {
//            annotateDicomImage("input.dcm", "output.dcm");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void annotateDicomImage(String inputPath, String outputPath) throws IOException {
//        // 读取DICOM文件
//        File dicomFile = new File(inputPath);
//        DicomInputStream dis = new DicomInputStream(dicomFile);
//        Attributes attrs = dis.readDataset(-1, -1);
//        dis.close();
//
//        // 读取图像
//        BufferedImage image = ImageIO.read(dicomFile);
//        Graphics2D g2d = image.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // 设置标注样式
//        g2d.setColor(Color.RED);
//        g2d.setStroke(new BasicStroke(2.0f));
//
//        // 添加矩形标注
//        int x = 100, y = 100, width = 200, height = 150;
//        g2d.drawRect(x, y, width, height);
//
//        // 添加文本标注
//        g2d.setFont(new Font("Arial", Font.BOLD, 16));
//        g2d.drawString("异常区域", x + 5, y - 5);
//
//        g2d.dispose();
//
//        // 更新DICOM元数据（可选）
////        attrs.setString(Tag.SeriesDescription,"PN", "标注后的图像");
//
//        // 保存修改后的DICOM文件
//        try (DicomOutputStream dos = new DicomOutputStream(new File(outputPath))) {
//            dos.writeDataset(attrs.createFileMetaInformation(""), attrs);
//        }
//
//        System.out.println("标注完成，已保存至: " + outputPath);
//    }
//}