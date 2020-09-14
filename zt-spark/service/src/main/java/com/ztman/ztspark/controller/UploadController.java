package com.ztman.ztspark.controller;

import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.tools.number.RandomCodeUtils;
import com.ztman.ztspark.entity.workorder.Attachment;
import com.ztman.ztspark.service.AttachmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(value = "/upload")
@Api(description = "文件上传")
public class UploadController {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Value("${prop.virtual-folder}")
    private String VIRTUAL_FOLDER;

    @Autowired
    private AttachmentService attachmentService;

//    @RequestMapping(value = "/singlefile", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    @PostMapping(value = "/singlefile", headers = "content-type=multipart/form-data")
    public R singlefile(MultipartFile file) throws IOException {
        R<Object> r = new R<>(Boolean.TRUE);
        SecruityUser user = SecurityUtils.getUserInfo();

        if (Objects.isNull(file) || file.isEmpty()) {
            r.setMsg("上传文件为空");
            r.setData(Boolean.FALSE);
            return r;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        String newDate = dateFormat.format(new Date());
        String filePath = "" + UPLOAD_FOLDER + newDate + "//";
        System.out.println(filePath);
        File dir = new File(filePath);

        if (!dir.exists()) {
            dir.mkdir();
        }
        Attachment attachment = new Attachment();
        LocalDateTime dateTime = LocalDateTime.now();

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String url = UUID.randomUUID() + "." + suffix;
        attachment.setSuffix(suffix);
        attachment.setGroupId(RandomCodeUtils.getCode(32).toUpperCase());
        attachment.setFileName(url);
        attachment.setTenantId(user.getTenantId());
        attachment.setPath(newDate + "/" + url);
        attachment.setType("2");
        attachment.setCreateTime(dateTime);
        attachment.setUpUserId(String.valueOf(user.getId()));

        FileInputStream fis = null;
        FileOutputStream fos = null;
        Map map = new HashMap();

        try {
            fis = (FileInputStream) file.getInputStream();
            fos = new FileOutputStream(new File(filePath + url));

            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();

            int capacity = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(capacity);
            int len = -1;
            while ((len = inChannel.read(buffer)) != -1) {
                buffer.flip();

                int length = 0;
                while ((length = outChannel.write(buffer)) != 0) {

                }
                buffer.clear();
            }
            inChannel.close();
            outChannel.close();


//			byte[] bytes = file.getBytes();
//			Path path = Paths.get(filePath + url);
//			//如果没有files文件夹，则创建
//			if (!Files.isWritable(path)) {
//				Files.createDirectories(Paths.get(filePath));
//			}
//			//文件写入指定路径
//			Files.write(path, bytes);

            this.attachmentService.insert(attachment);

            map.put("attachmentId", attachment.getId());
            map.put("filePath", VIRTUAL_FOLDER.replaceAll("\\*", "") + newDate + "/" + url);
            r.setMsg("文件上传成功");
            r.setData(map);
            return r;
        } catch (IOException e) {
            e.printStackTrace();
            r.setData(Boolean.FALSE);
            r.setMsg("文件上传失败");
            return r;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return r;
        }
    }
}