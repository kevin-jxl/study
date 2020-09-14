package com.ztman.admin.controller;

import com.ztman.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * EXCEL 文件上传与保存DEMO
 * Created by Lenovo on 2018/12/29.
 */
@RestController
@RequestMapping("/excelup")
@Slf4j
public class ExcelDemoController {
    @PostMapping(value="/doImport" , consumes = { "multipart/mixed", "multipart/form-data" } )
    public R doImport(@RequestParam(value = "file", required = false ) MultipartFile ufile ,String fileName, HttpServletRequest request) {
        System.out.println("上传的文件是:" + ufile.getName() );
        String localpath="C:\\excelup";
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        File tempFile = new File(localpath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }

        OutputStream os = null;
        InputStream ins =null;
        try {
             os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            ins=ufile.getInputStream();
            // 开始读取
            while ((len = ins.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new R<>(Boolean.TRUE);

    }

}
