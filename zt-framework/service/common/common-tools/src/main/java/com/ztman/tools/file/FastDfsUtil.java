package com.ztman.tools.file;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFS 上传工具类
 */

public class FastDfsUtil {
    public static Logger log = LoggerFactory.getLogger(FastDfsUtil.class);
    private static String filePort;
//    private static String filePort = "80";
//    private static String PRE_URL = "http://10.138.22.15:8028";


    /**
     * @Description:初始化fastDfs
     * @author: joy
     * @date: 2017年10月31日 上午9:52:16
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //String classPath = new File(FastDfsUtil.class.getResource("/").getFile()).getCanonicalPath();
            //String configFilePath = classPath + File.separator + "fdfs.properties";
            //System.out.println("配置文件：" + configFilePath);
            //获取资源文件路径
            ClassPathResource resource = new ClassPathResource("fdfsBy.properties");
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            System.out.println("Properties: {}" + properties);
            // 初始化参数
            /*ClientGlobal.init(configFilePath);*/
            ClientGlobal.initByProperties(properties);
            System.out.println(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT);
            filePort =  properties.getProperty("fastdfs.http_tracker_http_port");
            //System.out.println("资源路径："+resource.getPath());
            //ClientGlobal.initByProperties(resource.getPath());
            // 初始化跟踪器
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            // 初始化存储器
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            map.put("trackerClient", trackerClient);
            map.put("trackerServer", trackerServer);
            map.put("storageClient", storageClient);
            map.put("storageServer", storageServer);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description:上传文件
     * @author: joy
     * @date: 2017年10月25日 上午9:28:52
     */
    public static Map<String, Object> upload(String filePath) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            // 初始化参数
            NameValuePair[] meta_list = new NameValuePair[3];
            meta_list[0] = new NameValuePair("width", "120");
            meta_list[1] = new NameValuePair("heigth", "120");
            meta_list[2] = new NameValuePair("author", "jhy");
            File file = new File(filePath);
            String fileName = file.getName();
            map.put("ViewName", fileName);
            //获取文件后缀名
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            FileInputStream fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            System.out.println("文件长度: " + file_buff.length);
            map.put("fileSize", file_buff.length);
            // 声明组
            String group_name = null;
            // 根据跟踪器和组查找存储器
            StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                System.out.println("无法找到存储器服务：" + storageClient.getErrorCode());
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "无法找到存储器服务");
                return map;
            } else {
                System.out.println("存储器数量: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++) {
                    System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                    //map.put("url", storageServers[k] + filePort + "group1");
                }

            }
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 文件上传返回结果
            String[] results = storageClient.upload_file(file_buff, prefix, meta_list);
            System.out.println("上传耗费时间: " + (System.currentTimeMillis() - startTime) + " ms");
            if (results == null) {
                System.out.println("上传失败：" + storageClient.getErrorCode());
                map.put("resCode", storageClient.getErrorCode());
                map.put("resMsg", "上传失败");
                return map;
            }
            //返回结果赋值给组
            group_name = results[0];
            String remote_filename = results[1];
            System.out.println("组名: " + group_name + ", 文件路径: " + remote_filename);
            map.put("fileName", remote_filename);
            map.put("filePath", remote_filename);
            System.out.println(storageClient.get_file_info(group_name, remote_filename).toString());
            //根据跟踪器，组和文件路径查找跟踪服务器
            ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
            if (servers == null) {
                System.out.println("无法找到跟踪器服务：" + storageClient.getErrorCode());
                map.put("resCode", trackerClient.getErrorCode());
                map.put("resMsg", "无法找到跟踪器服务");
                return map;
            } else {
                System.out.println("跟踪服务器数量: " + servers.length);
                for (int k = 0; k < servers.length; k++) {
                    System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + servers[k].getIpAddr() + ":" + servers[k].getPort());
                }
            }
            map.put("resCode", "200");//代表上传成功
            map.put("resMsg", remote_filename);
            System.out.println("上传成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("resCode", "9999");
            map.put("resMsg", "文件服务器连接异常");
        }
        return map;
    }

    /**
     * <B>方法名称：</B>下载方法<BR>
     * <B>概要说明：</B>通过文件id进行下载<BR>
     *
     * @param fileId 文件id
     * @return 返回InputStream
     */
    public static byte[] getFileByte(String groupName, String fileId) {
        Map<String, Object> initMap = FastDfsUtil.init();
        TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
        TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
        StorageClient storageClient = (StorageClient) initMap.get("storageClient");
        StorageServer storageServer = (StorageServer) initMap.get("storageServer");
        try {
            storageServer = trackerClient.getStoreStorage(trackerServer, groupName);
            byte[] bytes = storageClient.download_file(groupName, fileId);
            // InputStream inputStream = new ByteArrayInputStream(bytes);
            return bytes;
        } catch (Exception ex) {
            //logger.error(ex);
            return null;
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            storageClient = null;
        }
    }

    /**
     * 字符串字节数组  以json文件 保存 至文件服务器
     *
     * @param bytes
     * @return
     */
    public static AttackmentDto uploadStringFile(byte[] bytes) {
        AttackmentDto dto = new AttackmentDto();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            // 初始化参数
            NameValuePair[] meta_list = new NameValuePair[3];
            meta_list[0] = new NameValuePair("width", "120");
            meta_list[1] = new NameValuePair("heigth", "120");
            meta_list[2] = new NameValuePair("author", "jhy");

            String group_name = null;
            // 根据跟踪器和组查找存储器
            StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                System.out.println("无法找到存储器服务：" + storageClient.getErrorCode());

                dto.setCode(500);
                dto.setMsg("无法找到存储器服务");
                return dto;
            } else {
                System.out.println("存储器数量: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++) {
                    System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                    //map.put("url", storageServers[k] + filePort + "group1");
                }

            }
            // 开始时间
            long startTime = System.currentTimeMillis();
            // 文件上传返回结果
            String[] results = storageClient.upload_file(bytes, "json", meta_list);
            System.out.println("上传耗费时间: " + (System.currentTimeMillis() - startTime) + " ms");
            if (results == null) {
                System.out.println("上传失败：" + storageClient.getErrorCode());
                dto.setCode(500);
                dto.setMsg("上传失败");
                return dto;
            }
            //返回结果赋值给组
            group_name = results[0];
            String remote_filename = results[1];
            System.out.println("组名: " + group_name + ", 文件路径: " + remote_filename);
            dto.setAttFileName(remote_filename);
            dto.setAttPath(remote_filename);
            System.out.println(storageClient.get_file_info(group_name, remote_filename).toString());
            //根据跟踪器，组和文件路径查找跟踪服务器
            ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
            if (servers == null) {
                System.out.println("无法找到跟踪器服务：" + storageClient.getErrorCode());
                dto.setCode(500);
                dto.setMsg("上传失败");
                return dto;
            } else {
                System.out.println("跟踪服务器数量: " + servers.length);
                for (int k = 0; k < servers.length; k++) {
                    System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + servers[k].getIpAddr() + ":" + servers[k].getPort());
                }
            }
            String pre_url = "http://" + storageServers[0].getInetSocketAddress().getAddress().getHostAddress() + ":" + filePort;
            dto.setAttRelationId(remote_filename);
            dto.setAttUploadTime(LocalDateTime.now());
            dto.setAttUrl(pre_url + "/" + group_name + "/" + remote_filename);
            dto.setCode(200);
            dto.setMsg("上传成功");
            System.out.println("上传成功");
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            dto.setCode(500);
            dto.setMsg("上传失败");
        }
        return dto;
    }

    /**
     * web端文件上传
     *
     * @param files
     * @return
     */
    public static List<AttackmentDto> webUpload(MultipartFile[] files, String userId) {
        List<AttackmentDto> list = new ArrayList<>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            // 初始化参数
            NameValuePair[] meta_list = new NameValuePair[3];
            meta_list[0] = new NameValuePair("width", "120");
            meta_list[1] = new NameValuePair("heigth", "120");
            meta_list[2] = new NameValuePair("author", "jhy");
            for (MultipartFile file : files) {
                AttackmentDto dto = new AttackmentDto();
                //获取真实的文件名
                String fileName = file.getOriginalFilename();
                //获取文件后缀名
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                InputStream fis = file.getInputStream();
                byte[] file_buff = null;
                if (fis != null) {
                    int len = fis.available();
                    file_buff = new byte[len];
                    fis.read(file_buff);
                }
                System.out.println("文件长度: " + file_buff.length);
//				map.put("fileSize",file_buff.length);
                // 声明组
                String group_name = null;
                // 根据跟踪器和组查找存储器
                StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
                if (storageServers == null) {
                    System.out.println("无法找到存储器服务：" + storageClient.getErrorCode());
                    dto.setCode(500);
                    dto.setMsg("无法找到存储器服务");
                    list.add(dto);
                    return list;
                } else {
                    System.out.println("存储器数量: " + storageServers.length);
                    for (int k = 0; k < storageServers.length; k++) {
                        System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());

                    }

                }
                // 开始时间
                long startTime = System.currentTimeMillis();
                // 文件上传返回结果
                String[] results = storageClient.upload_file(file_buff, prefix, meta_list);
                System.out.println("上传耗费时间: " + (System.currentTimeMillis() - startTime) + " ms");
                if (results == null) {
                    System.out.println("上传失败：" + storageClient.getErrorCode());
                    dto.setCode(500);
                    dto.setMsg("上传失败");
                    list.add(dto);
//					map.put("resCode", storageClient.getErrorCode());
//					map.put("resMsg", "上传失败");
//					list.add(map);
                    return list;
                }
                //返回结果赋值给组
                group_name = results[0];
                String remote_filename = results[1];
                System.out.println("组名: " + group_name + ", 文件路径: " + remote_filename);

                System.out.println(storageClient.get_file_info(group_name, remote_filename).toString());
                //根据跟踪器，组和文件路径查找跟踪服务器
                ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
                if (servers == null) {
                    System.out.println("无法找到跟踪器服务：" + storageClient.getErrorCode());
                    dto.setCode(500);
                    dto.setMsg("无法找到跟踪器服务");
                    list.add(dto);
//					map.put("resCode", trackerClient.getErrorCode());
//					map.put("resMsg", "无法找到跟踪器服务");
//					list.add(map);
                    return list;
                } else {
                    System.out.println("跟踪服务器数量: " + servers.length);
                    for (int k = 0; k < servers.length; k++) {
                        System.out.println("当前文件：" + k + 1 + ". " + "当前ip和端口" + servers[k].getIpAddr() + ":" + servers[k].getPort());
                    }
                }
                String pre_url = "http://" + storageServers[0].getInetSocketAddress().getAddress().getHostAddress() + ":" + filePort;
                dto.setAttViewName(fileName);
                dto.setAttFileName(remote_filename);
                dto.setAttFileSize(BigDecimal.valueOf(file_buff.length));
//				dto.setAttFileType(prefix);
                dto.setAttPath(remote_filename);
                dto.setAttRelationId(remote_filename);
                dto.setAttUploadTime(LocalDateTime.now());
                dto.setAttUrl(pre_url + "/" + group_name + "/" + remote_filename);
                dto.setUserId(userId);
                dto.setCode(200);
                dto.setMsg("上传成功");
//				map.put("ViewName",fileName);
//				map.put("fileName",fileName);
//				map.put("filePath",remote_filename);
//				map.put("url",PRE_URL+"/group1/"+remote_filename);
//				map.put("resCode", "200");//代表上传成功
//				map.put("resMsg", remote_filename);
//				System.out.println("上传成功");
                list.add(dto);
            }


        } catch (Exception e) {
            e.printStackTrace();
//			map.put("resCode", "9999");
//			map.put("resMsg", "文件服务器连接异常");
            AttackmentDto errDto = new AttackmentDto();
            errDto.setCode(500);
            errDto.setMsg("文件服务器连接异常");
            list.add(errDto);
        }
        return list;
    }


    /**
     * @Description:下载文件
     * @author: joy
     * @date: 2017年10月31日 上午9:56:33
     */
    public static Map<String, Object> download(String group_name, String remote_filename) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 初始化fastDfs
            Map<String, Object> initMap = FastDfsUtil.init();
            TrackerClient trackerClient = (TrackerClient) initMap.get("trackerClient");
            TrackerServer trackerServer = (TrackerServer) initMap.get("trackerServer");
            StorageClient storageClient = (StorageClient) initMap.get("storageClient");
            StorageServer storageServer = (StorageServer) initMap.get("storageServer");
            FileInfo fi = storageClient.get_file_info(group_name, remote_filename);
            String sourceIpAddr = fi.getSourceIpAddr();
            long size = fi.getFileSize();
            System.out.println("ip地址:" + sourceIpAddr + ",文件大小:" + size);
            map.put("resCode", "200");//代表下载成功
            map.put("resMsg", remote_filename);
            map.put("ip", sourceIpAddr + ":" + filePort + "/group1/");
            System.out.println("下载成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description:测试
     * @author: joy
     * @date: 2017年10月25日 上午11:04:39
     */
    public static void main(String[] args) {
        upload("D:\\1.jpg");
//        String str = "{'kk':'yy'}";
//        AttackmentDto dto = uploadStringFile(str.getBytes());
//        System.out.println(dto.getAttFileName());
//        byte[] bytes = "hello world".getBytes();
//
//        //Base64 Encoded
//        String encoded = Base64.getEncoder().encodeToString(bytes);
//        //Base64 Decoded
//        byte[] decoded = Base64.getDecoder().decode(encoded);
//        System.out.println(new String(decoded) );
    }


    public static List<String> getUpdatedFileIds(List<String> delFileIds, String OldFileIds) {
        List<String> listOld = null;
        String[] listOldids = OldFileIds.split(",");
        if (listOldids != null && listOldids.length > 0) {
            listOld = Arrays.asList(listOldids);
            for (int i = 0; i < listOld.size(); i++) {
                for (int j = 0; j < delFileIds.size(); j++) {
                    if (i == j) {
                        delFileIds.remove(j);
                    }
                }
            }
            for (String str : listOld) {
                OldFileIds += str + ",";
            }
        }
        return listOld;
    }

    public static String upId(String id, Map<String, String> map) {
        System.out.println(id + "======");
        System.out.println(map.get("kk") + "MMMMAP");
        map.replace("kk", "newmapvaule");
        System.out.println(map.get("kk") + "-------");
        String xx = "222" + id;
        id = "777";
        return xx;
    }
}
