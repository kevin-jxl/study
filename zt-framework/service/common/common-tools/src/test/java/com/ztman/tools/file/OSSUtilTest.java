package com.ztman.tools.file;

import com.aliyun.oss.OSSClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 2018/11/5.
 */
public class OSSUtilTest {
    //log
    private static Log LOG = LogFactory.getLog(OSSUtilTest.class);

    private OSSClient client = null;
    private String bucketName = "adm-picture";
    @Before
    public void initUnit(){
        client = OSSUtil.getOSSClient();
    }

    @Test
    public void testCreateBucket() {
        String bucketName = "adm-picture";
        //创建bucket
        assertEquals(true, OSSUtil.createBucket(client, bucketName));
    }

    @Test
    public void testDeleteBucket(){
        String bucketName = "adm-picture";
        //删除bucket
        OSSUtil.deleteBucket(client, bucketName);
        //console log :删除oss-test-myBucket成功
    }

    @Test
    public void testUploadObject2OSS(){
        //上传文件
        String flilePathName = "C:/hello.jpg";
        File file = new File(flilePathName);
        String diskName = "public/tmp/";
        String md5key = OSSUtil.uploadObject2OSS(client, file, bucketName, diskName);
        LOG.info("上传后的文件MD5数字唯一签名:" + md5key);  //上传后的文件MD5数字唯一签名:A30B046A34EB326C4A3BBD784333B017
    }

    @Test
    public void testGetOSS2InputStream(){
        //获取文件
        try {
            BufferedInputStream bis = new BufferedInputStream(OSSUtil.getOSS2InputStream(client, bucketName, "public/tmp/", "hello.jpg"));
            String resfile = "c:/hello.jpg";
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(resfile)));
            int itemp = 0;
            while((itemp = bis.read()) != -1){
                bos.write(itemp);
            }
            LOG.info("文件获取成功"); //console log :文件获取成功
            bis.close();
            bos.close();
        } catch (Exception e) {
            LOG.error("从OSS获取文件失败:" + e.getMessage(), e);
        }
    }

    @Test
    public void testDeleteFile(){
        //注：文件夹下只有一个文件，则文件夹也会一起删除；如果多个文件，只会删除指定文件名的文件
        //删除文件
        OSSUtil.deleteFile(client, bucketName, "public/tmp/", "");
        //console log : 删除oss-test-my下的文public/tmp/he-1.jpg成功
    }

    @Test
    public void testlistallfiles(){
        OSSUtil.listAllFiles(client, bucketName);
    }

    @Test
    public void testGetAllFiles(){
        OSSUtil.getAllFiles("public/tmp/");
    }

    @Test
    public void testDelAllFiles(){
        OSSUtil.delAllFiles("public/tmp/");
    }
}