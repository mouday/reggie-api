package com.github.mouday.reggie.common;

import java.io.File;
import java.util.UUID;

public class UploadFile {
    /**
     * 获取文件保存路径
     * 参考：https://www.bbsmax.com/A/GBJrE67Wz0/
     *
     * @return File
     */
    public static File getUploadDirectory() {

        // 获取目录
        File path = new File("");

        File upload = new File(path.getAbsolutePath(), "upload");

        // 不存在则创建
        if (!upload.exists()) {
            upload.mkdirs();
        }

        return upload;
    }

    /**
     * 获取文件名
     * @param originalFilename
     * @return
     */
    public static String getUploadFilename(String originalFilename) {
        String suffix = UploadFile.getFilenameSuffix(originalFilename);

        // 使用uuid生成文件名，防止文件名重复造成文件覆盖
        String filename = UUID.randomUUID().toString() + suffix;

        return filename;
    }

    /**
     * 获取文件名后缀，带有.
     *
     * @param filename
     * @return
     */
    public static String getFilenameSuffix(String filename) {
        if (filename == null) {
            return null;
        }

        return filename.substring(filename.lastIndexOf("."));
    }
}
