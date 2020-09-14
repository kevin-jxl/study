package com.ztman.tools.email;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.*;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送工具类
 */
@Slf4j
public class EmailUtils {
    /**
     * 发送普通纯文本邮件
     *
     * @param fromEmail 发件邮箱
     * @param toEmail   收件地址
     * @param title     邮件标题
     * @param content   邮件内容
     * @return
     */
    public static String sendEmail(String fromEmail, String[] toEmail, String title, String content) {
        return sendEmail(null, fromEmail, null, toEmail, title, content);
    }

    /**
     * 发送普通纯文本邮件
     *
     * @param emailServer 邮件发送服务器地址
     * @param fromEmail   发件邮箱
     * @param password    发件邮箱密码
     * @param toEmail     收件地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @return
     */
    public static String sendEmail(String emailServer, String fromEmail, String password, String[] toEmail, String title, String content) {

        SimpleEmail email = new SimpleEmail();
        if (StringUtils.isNotBlank(emailServer)) {
            email.setHostName(emailServer);
        } else {
            // 默认使用本地 postfix 发送，这样就可以将postfix 的 mynetworks 配置为 127.0.0.1 或 127.0.0.0/8 了
            email.setHostName("127.0.0.1");
        }

        // 如果密码为空，则不进行认证
        if (StringUtils.isNotBlank(password)) {
            email.setAuthentication(fromEmail, password);
        }

        email.setCharset("utf-8");
        try {
            email.addTo(toEmail);
            email.setFrom(fromEmail);
            email.setSubject(title);
            email.setMsg(content);
            return email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param fromEmail   发件邮箱
     * @param toEmail     收件地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件信息列表（List泛型对象为EmailAttachment）
     * @return
     */
    public static String sendEmail4ATM(String fromEmail, String[] toEmail, String title, String content, List attachments) {
        return sendEmail4ATM(null, fromEmail, null, toEmail, title, content, attachments);
    }

    /**
     * 发送带附件的邮件
     *
     * @param emailServer 邮件发送服务器地址
     * @param fromEmail   发件邮箱
     * @param password    发件邮箱密码
     * @param toEmail     收件地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件信息列表（List泛型对象为EmailAttachment）
     * @return
     */
    public static String sendEmail4ATM(String emailServer, String fromEmail, String password, String[] toEmail, String title, String content, List attachments) {
        MultiPartEmail email = new MultiPartEmail();
        if (StringUtils.isNotBlank(emailServer)) {
            email.setHostName(emailServer);
        } else {
            // 默认使用本地 postfix 发送，这样就可以将postfix 的 mynetworks 配置为 127.0.0.1 或 127.0.0.0/8 了
            email.setHostName("127.0.0.1");
        }

        // 如果密码为空，则不进行认证
        if (StringUtils.isNotBlank(password)) {
            email.setAuthentication(fromEmail, password);
        }

        email.setCharset("utf-8");
        try {
            email.addTo(toEmail);
            email.setFrom(fromEmail);
            email.setSubject(title);
            email.setMsg(content);

            // 添加附件
            if (null != attachments) {
                for (int i = 0; i < attachments.size(); i++) {
                    EmailAttachment attachment = (EmailAttachment) attachments.get(i);
                    if (null != attachment) {
                        email.attach(attachment);
                    }
                }
            }
            return email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送HTML格式的邮件
     *
     * @param fromEmail 发件邮箱
     * @param toEmail   收件地址
     * @param title     邮件标题
     * @param content   邮件内容
     * @param htmlMsg   HTML格式
     * @return
     */
    public static String sendEmail4HTML(String fromEmail, String[] toEmail, String title, String content, String htmlMsg) {
        return sendEmail4HTML(null, fromEmail, null, toEmail, title, content, htmlMsg);
    }

    /**
     * 发送HTML格式的邮件
     *
     * @param emailServer 邮件发送服务器地址
     * @param fromEmail   发件邮箱
     * @param password    发件邮箱密码
     * @param toEmail     收件地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param htmlMsg     HTML格式
     * @return
     */
    public static String sendEmail4HTML(String emailServer, String fromEmail, String password, String[] toEmail, String title, String content, String htmlMsg) {
        HtmlEmail email = new HtmlEmail();
        if (StringUtils.isNotBlank(emailServer)) {
            email.setHostName(emailServer);
        } else {
            // 默认使用本地 postfix 发送，这样就可以将postfix 的 mynetworks 配置为 127.0.0.1 或 127.0.0.0/8 了
            email.setHostName("127.0.0.1");
        }

        // 如果密码为空，则不进行认证
        if (StringUtils.isNotBlank(password)) {
            email.setAuthentication(fromEmail, password);
        }

        email.setCharset("utf-8");
        try {
            email.addTo(toEmail);
            email.setFrom(fromEmail);
            email.setSubject(title);
            email.setTextMsg(content);

            // 添加HTML
            email.setHtmlMsg(htmlMsg);
            return email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //1.普通邮件测试
/*        String result=   EmailUtils.sendEmail("smtp.163.com","18653217930@163.com","XXXXXX",new String[]{"454633390@qq.com","ppboyhai@163.com"},"邮件测试","邮件正文!!!");
        System.out.println("普通邮件发送结果："+result);*/
        //2.附件邮件测试
        List attachments = new ArrayList();
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("C:\\hello.jpg");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachments.add(attachment);

        EmailAttachment attachment1 = new EmailAttachment();
        attachment1.setPath("C:\\hello2.jpg");
        attachment1.setDisposition(EmailAttachment.ATTACHMENT);
        attachments.add(attachment1);

        String ret = sendEmail4ATM(
                "smtp.163.com",    // 邮件发送服务器地址
                "18653217930@163.com",  // 发件邮箱
                "XXXXXX",          // 发件邮箱密码
                new String[]{"454633390@qq.com","ppboyhai@163.com"},   // 收件地址
                "带附件邮件",         // 邮件标题
                "请查收附件",         // 邮件内容
                attachments);
        System.out.println("附件邮件发送返回值: " + ret);
    }
}