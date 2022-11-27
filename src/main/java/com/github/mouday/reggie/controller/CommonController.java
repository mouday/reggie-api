package com.github.mouday.reggie.controller;

import com.github.mouday.reggie.common.R;
import com.github.mouday.reggie.common.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${application.public-dirname}")
    private String publicDirname;


    @Value("${application.upload-dirname}")
    private String uploadDirname;


    /**
     * 文件上传
     *
     * @param file
     * @return
     * @url http://127.0.0.1:8080/backend/page/common/upload.html
     */
    @PostMapping("/upload")
    public R<String> uploadFile(MultipartFile file) throws IOException {
        // file 是一个临时文件
        log.info("file: {}", file);

        String originalFilename = file.getOriginalFilename();

        String filename = UploadFile.getUploadFilename(originalFilename);

        // 转存文件
        file.transferTo(new File(UploadFile.getUploadDirectory(), filename));

        return R.success(filename);
    }


    /**
     * 文件下载
     *
     */
    @GetMapping("/download")
    public void downloadFile(String name, HttpServletResponse response) throws IOException {
        log.info("name: {}", name);

        // 输入流 读取文件
        FileInputStream inputStream = new FileInputStream(new File(UploadFile.getUploadDirectory(), name));

        // 输出流 将文件写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];

        while (true) {
            len = inputStream.read(bytes);
            if (len == -1) {
                break;
            }
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }

        // 关闭资源
        outputStream.close();
        inputStream.close();
    }
}
