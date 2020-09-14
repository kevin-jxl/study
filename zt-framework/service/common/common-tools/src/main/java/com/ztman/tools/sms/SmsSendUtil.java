package com.ztman.tools.sms;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ztman.tools.base.HttpClientUtil;
import org.activiti.engine.impl.util.json.XML;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;


/**
 * 短信发送工具类--临时，后期选型短信平台后替换
 *
 * @author joy
 */
public class SmsSendUtil {
    private static Log log = LogFactory.getLog(SmsSendUtil.class);

    /**
     * 单条发送接口
     *
     * @param recvMsisdn 手机号码
     * @param smsText    短信内容
     * @return
     */
    public static boolean SmsSend(String recvMsisdn, String smsText) {
        String userName = "";
        String userpass = "";
        String sendUrl = "http://sms.95ai.cn:1082/wgws/OrderServlet";
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

        params.put("apName", userName);//AP账号：
        params.put("apPassword", userpass);//AP密码：
        params.put("srcId", "");//附加号（可置空）。
        params.put("calledNumber", recvMsisdn);//手机号码，一次只能输入一个手机号码
        params.put("content", smsText);//短信内容
        try {
            String result = HttpClientUtil.httpPostRequest(sendUrl, headers, params);
            String rtn = XML.toJSONObject(result.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "")).getString("response");
            JSONObject jsonObject = JSON.parseObject(rtn);
            if ("0".equals(jsonObject.getString("error"))) {
                log.info("发送成功 msgId:" + jsonObject.getString("msgId"));
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 批量发送接口
     *
     * @param recvMsisdn 手机号码
     * @param smsText    短信内容
     * @return
     */
    public static boolean SmsSendToMultiple(String recvMsisdn, String smsText) {
        String userName = "";
        String userpass = "";
        String sendUrl = "http://sms.95ai.cn:1082/wgws/BatchSubmit";
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

        params.put("apName", userName);//AP账号：
        params.put("apPassword", userpass);//AP密码：
        params.put("srcId", "");//附加号（可置空）。
        params.put("ServiceId", "");//预留，可为空
        params.put("calledNumber", recvMsisdn);//手机号码，一次只能输入一个手机号码
        params.put("content", smsText);//短信内容
        try {
            String result = HttpClientUtil.httpPostRequest(sendUrl, headers, params);
            String rtn = XML.toJSONObject(result.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "")).getString("response");
            JSONObject jsonObject = JSON.parseObject(JSON.parseObject(rtn).getString("submitResp"));
            if ("0".equals(jsonObject.getString("error"))) {
                log.info("发送成功 msgId:" + jsonObject.getString("msgId"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static void main(String[] args) {
//		单内容单被叫
//        SmsSendUtil.SmsSend("15725226867", "短信测试");
//		单内容多被叫
        SmsSendUtil.SmsSendToMultiple("15725226867,18210172064", "多用户测试");
    }
}
