package com.pmp.infrastructure.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 获取文件名
     *
     * @param file
     * @param carrySuffix 是否携带文件后缀
     * @return
     */
    public static String getFileName(MultipartFile file, boolean carrySuffix) {
        String fullName = file.getOriginalFilename();
        if (carrySuffix) return fullName;
        return fullName.substring(0, fullName.lastIndexOf('.'));
    }
}
