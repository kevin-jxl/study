/**
 * 
 */
package com.ztman.tools.file;

/**
 * @author joy
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里OSS工具类
  */
public class OSSUtil {
      
    //log
    private static final Logger LOG = LoggerFactory.getLogger(OSSUtil.class);
    //外网地址  
    public static String WEB_PUB;
    //内网地址  
    public static String WEB_PRIVATE;
    //bucketName  
    private static String BUCKETNAME;   
    //阿里云API的内或外网域名  
    private static String ENDPOINT;  
    //阿里云API的密钥Access Key ID  
    private static String ACCESS_KEY_ID;  
    //阿里云API的密钥Access Key Secret  
    private static String ACCESS_KEY_SECRET;  
      
    //init static datas  
    static{  
    	//java.lang.ExceptionInInitializerError
    	// ResourceBundle.getBundle(的參數，第一個為basename,是不帶peoperties擴展名的，後面一個參數是語言參數)base name misc/oss.properties, locale zh_CN
        ResourceBundle bundle = PropertyResourceBundle.getBundle("alioss");
        BUCKETNAME = bundle.containsKey("bucketName") == false ? "" : bundle.getString("bucketName"); 
        ENDPOINT = bundle.containsKey("endpoint") == false ? "" : bundle.getString("endpoint");  
        ACCESS_KEY_ID = bundle.containsKey("accessKeyId") == false? "" : bundle.getString("accessKeyId");  
        ACCESS_KEY_SECRET = bundle.containsKey("accessKeySecret") == false ? "" : bundle.getString("accessKeySecret");  
        WEB_PUB = bundle.containsKey("web_pub") == false ? "" : bundle.getString("web_pub");
        WEB_PRIVATE = bundle.containsKey("web_private") == false ? "" : bundle.getString("web_private");
    }  
    
    
    public static String getBUCKETNAME() {
		return BUCKETNAME;
	}

	/**
     * 获取阿里云OSS客户端对象 
     * */
    public static final OSSClient getOSSClient(){  
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);  
    }  
     
    /**
     * 新建Bucket  --Bucket权限:私有 
     * @param bucketName bucket名称 
     * @return true 新建Bucket成功 
     * */
    public static final boolean createBucket(OSSClient client, String bucketName){  
        Bucket bucket = client.createBucket(bucketName);   
        return bucketName.equals(bucket.getName());  
    }  
      
    /**
     * 删除Bucket  
     * @param bucketName bucket名称 
     * */
    public static final void deleteBucket(OSSClient client, String bucketName){  
        client.deleteBucket(bucketName);   
        LOG.info("删除" + bucketName + "Bucket成功");  
    }  
      
    /**
     * 向阿里云的OSS存储中存储文件  --file也可以用InputStream替代 
     * @param client OSS客户端 
     * @param file 上传文件 
     * @param bucketName bucket名称 
     * @param diskName 上传文件的目录  --bucket下文件的路径 
     * @return String 唯一MD5数字签名 
     * */
    public static final String uploadObject2OSS(OSSClient client, File file, String bucketName, String diskName) {  
        String resultStr = null;  
        try {  
            InputStream is = new FileInputStream(file);  
            String fileName = file.getName();  
            Long fileSize = file.length();  
            //创建上传Object的Metadata  
            ObjectMetadata metadata = new ObjectMetadata();  
            metadata.setContentLength(is.available());  
            metadata.setCacheControl("no-cache");  
            metadata.setHeader("Pragma", "no-cache");  
            metadata.setContentEncoding("utf-8");  
            metadata.setContentType(getContentType(fileName));  
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
            //上传文件   
            PutObjectResult putResult = client.putObject(bucketName, diskName + fileName, is, metadata);  
            //解析结果  
            resultStr = putResult.getETag();  
        } catch (Exception e) {  
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);  
        }  
        return resultStr;  
    }  
    
    /**
     * 上傳文件流，主要針對本次excel上傳
     * 直接以filename為session存儲，filename使用全路徑，不帶/public的/
     * @param fileName
     * @param inputStream
     * @return
     */
    public static final String uploadStream(String fileName, InputStream inputStream) {  
        String resultStr = null;
        OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {  
            //上传文件   
            PutObjectResult putResult = client.putObject(BUCKETNAME, fileName, inputStream);  
            //解析结果  
            resultStr = putResult.getETag(); 
            LOG.info("上传阿里云OSS服务器返回." + resultStr);
        } catch (Exception e) {  
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);  
        } finally {
        	client.shutdown();
        }
        return resultStr;  
    }
    
    /**
     * 獲取文件流
     * @param fileName
     * @return
     */
    public static final InputStream getStream(OSSClient client,String fileName){ 
    	OSSObject ossObj = client.getObject(BUCKETNAME, fileName);
    	return ossObj.getObjectContent();
     }
    
    public static void delFile(String fileName){  
    	OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
			client.deleteObject(BUCKETNAME, fileName);   
			LOG.info("删除" + fileName + "成功");
		} catch (Exception e) {
			LOG.error("刪除阿里云OSS文件异常." + e.getMessage(), e);  
		} finally {
        	client.shutdown();
        } 
    } 
    
    /**
     * 根据key获取OSS服务器上的文件输入流 
     * @param client OSS客户端 
     * @param bucketName bucket名称 
     * @param diskName 文件路径 
     * @param key Bucket下的文件的路径名+文件名 
     */
     public static final InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName, String key){   
        OSSObject ossObj = client.getObject(bucketName, diskName + key);  
        return ossObj.getObjectContent();     
     }    
      
   /**
    * 根据key删除OSS服务器上的文件  
    * @param client OSS客户端 
    * @param bucketName bucket名称 
    * @param diskName 文件路径 
    * @param key Bucket下的文件的路径名+文件名 
    */
      public static void deleteFile(OSSClient client, String bucketName, String diskName, String key){    
        client.deleteObject(bucketName, diskName + key);   
        LOG.info("删除" + bucketName + "下的文件" + diskName + key + "成功");  
      }    
       
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType  
     * @param fileName 文件名 
     * @return 文件的contentType    
     */
     public static final String getContentType(String fileName){    
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));  
        if("bmp".equalsIgnoreCase(fileExtension)) return "image/bmp";  
        if("gif".equalsIgnoreCase(fileExtension)) return "image/gif";  
        if("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)  || "png".equalsIgnoreCase(fileExtension) ) return "image/jpeg";  
        if("html".equalsIgnoreCase(fileExtension)) return "text/html";  
        if("txt".equalsIgnoreCase(fileExtension)) return "text/plain";  
        if("vsd".equalsIgnoreCase(fileExtension)) return "application/vnd.visio";  
        if("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) return "application/vnd.ms-powerpoint";  
        if("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) return "application/msword";  
        if("xml".equalsIgnoreCase(fileExtension)) return "text/xml";  
        return "text/html";    
     }    
  
     public static void listAllFiles(OSSClient client, String bucketName){
  // 构造ListObjectsRequest请求
     ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

     // 列出Object 
     ObjectListing listing = client.listObjects(listObjectsRequest);

     // 遍历所有Object
     System.out.println("Objects:");
     for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
         System.out.println(objectSummary.getKey());
     }

     // 遍历所有CommonPrefix
     System.out.println("CommonPrefixs:");
     for (String commonPrefix : listing.getCommonPrefixes()) {
         System.out.println(commonPrefix);
     }
     }
     
     public static List<String> getAllFiles(String prefix){
    	 OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	 List<String> list = new ArrayList<String>();
    	 try {  
    	// 构造ListObjectsRequest请求
    	 ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKETNAME);
    	 listObjectsRequest.setPrefix(prefix);

    	 // 递归列出fun目录下的所有文件
    	 ObjectListing listing = client.listObjects(listObjectsRequest);

    	 // 遍历所有Object
    	 System.out.println("Objects:");
    	 for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
    	     System.out.println(objectSummary.getKey());
    		 list.add(objectSummary.getKey());
    	 }

    	 // 遍历所有CommonPrefix
    	 /*System.out.println("\nCommonPrefixs:");
    	 for (String commonPrefix : listing.getCommonPrefixes()) {
    	     System.out.println(commonPrefix);
    	 }*/
         }
         finally {
        	 client.shutdown();
         }
    	 return list;
     }
     
     public static void delAllFiles(String prefix){
    	 OSSClient client = new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	 List<String> list = new ArrayList<String>();
    	 try {  
    	// 构造ListObjectsRequest请求
    	 ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKETNAME);
    	 listObjectsRequest.setPrefix(prefix);

    	 // 递归列出fun目录下的所有文件
    	 ObjectListing listing = client.listObjects(listObjectsRequest);

    	 // 遍历所有Object
    	 System.out.println("Objects:");
    	 for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
    	     //System.out.println(objectSummary.getKey());
    		 list.add(objectSummary.getKey());
    	 }
    	 client.deleteObjects(new DeleteObjectsRequest(BUCKETNAME).withKeys(list));
    	 /*
	    	 DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(BUCKETNAME).withKeys(list));
	    	 List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
	    	 for(String str:deletedObjects){
	    		 System.out.println(str);
	    	 }
    	 */
         }
         finally {
        	 client.shutdown();
         }
     }
}  