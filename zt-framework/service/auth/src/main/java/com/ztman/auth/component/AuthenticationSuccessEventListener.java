package com.ztman.auth.component;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.feign.RemoteLogService;
import com.ztman.admin.api.feign.RemoteUserService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final String PASSWORD = "password";
    @Autowired
    private RemoteLogService remoteLogService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        //这里的事件源除了登录事件（UsernamePasswordAuthenticationToken）还有可能是token验证事件源（OAuth2Authentication）
        if(!event.getSource().getClass().getName().equals("org.springframework.security.authentication.UsernamePasswordAuthenticationToken")){
            return ;
        }
        System.out.println("进入登录监听");
        //这里还有oAuth2的客户端认证的事件，需要做一个判断
        if(event.getAuthentication().getDetails() != null){
            //登录日志逻辑。。。
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            StringBuilder params = new StringBuilder();
            request.getParameterMap().forEach((key, values) -> {
                params.append(key).append("＝");
                if (PASSWORD.equalsIgnoreCase(key)) {
                    params.append("******");
                } else {
                    params.append(ArrayUtil.toString(values));
                }
                params.append("＆");
            });
            String username = request.getParameter("username");
            SysLog sysLog = new SysLog();
            sysLog.setCreateBy(username);
            sysLog.setType("9");
            sysLog.setRemoteAddr(HttpUtil.getClientIP(request));
            sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            sysLog.setMethod(request.getMethod());
            sysLog.setUserAgent(request.getHeader("user-agent"));
            sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
            sysLog.setServiceId(SecurityUtils.getClientId());
            sysLog.setTitle("账号"+username+"登录");
            // 发送异步日志事件
            remoteLogService.save(sysLog);
            System.out.println("***登录成功了***");
        }else{
        }
    }
}