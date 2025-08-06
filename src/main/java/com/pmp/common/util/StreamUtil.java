package com.pmp.common.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 流处理工具类
 */
public class StreamUtil {

    /**
     * 复制流
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] getCopyInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        // 将原始流复制到缓冲区
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * 将文件流存储为文件
     *
     * @param inputStream
     * @param targetFilePath
     * @throws IOException
     */
    public static void saveInputStreamToFile(InputStream inputStream, String targetFilePath) throws IOException {
        Path targetPath = Paths.get(targetFilePath);

        // 创建父目录（如果不存在）
        Path parentDir = targetPath.getParent();
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        // 写入文件（覆盖已存在的文件）
        Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}

