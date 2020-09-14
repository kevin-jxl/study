package com.ztman.business.inspection.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.common.util.Jackson2JsonParser;

import com.auth0.jwt.interfaces.Claim;
import com.ztman.business.inspection.util.JwtUtil;
import com.ztman.common.core.util.R;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = {"/attachment/*","/user/info","/user/update","/user/updatePwd","/syscompany/*","/poininspectionjob/*","/equipment/*","/statistics/*","/workorder/*"})
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        
        // Except OPTIONS, other request should be checked by JWT
        else {

            if (token == null) { 
            	response.setContentType("application/json");
                response.getWriter().write("{\"code\":\"1000\",\"data\":null,\"message\":\"无token\"}"); 
                return;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token); 
            if (userData == null) {
            	response.setContentType("application/json");
            	response.getWriter().write("{\"code\":\"1000\",\"data\":null,\"message\":\"无效token\"}"); 
                return;
            }
            Integer id = userData.get("id").asInt();
            String realName = userData.get("realName").asString();
            String userName = userData.get("userName").asString(); 
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
} 
