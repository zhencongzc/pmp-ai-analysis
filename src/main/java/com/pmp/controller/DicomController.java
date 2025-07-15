//package com.pmp.controller;
//
//import org.dcm4che3.data.Attributes;
//import org.dcm4che3.data.Tag;
//import org.dcm4che3.io.DicomInputStream;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class DicomController {
//
//    @PostMapping(value = "/upload/dicom", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Map<String, Object> uploadDicomFile(@RequestParam("file") MultipartFile file) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            // 1. 读取DICOM文件
//            Attributes dicomAttributes = readDicomFile(file);
//
//            // 2. 提取关键信息
//            result.put("success", true);
//            result.put("patientId", dicomAttributes.getString(Tag.PatientID));
//            result.put("patientName", dicomAttributes.getString(Tag.PatientName));
//            result.put("studyDate", dicomAttributes.getString(Tag.StudyDate));
//            result.put("modality", dicomAttributes.getString(Tag.Modality));
//
//            // 3. 可选：返回所有DICOM标签
//            // result.put("allTags", dicomAttributes.toString());
//
//        } catch (Exception e) {
//            result.put("success", false);
//            result.put("error", e.getMessage());
//        }
//        return result;
//    }
//
//    private Attributes readDicomFile(MultipartFile file) throws IOException {
//        try (DicomInputStream dis = new DicomInputStream(file.getInputStream())) {
//            return dis.readDataset(-1, -1);
//        }
//    }
//}