package com.ztman.gateway.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ztman.gateway.handler.LogHttpRequestHandler;
import com.ztman.gateway.config.EsLog;
import com.ztman.gateway.config.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

//import com.ztman.common.security.DTO.SecruityUser;
//import com.ztman.common.security.util.SecurityUtils;

/**
 * 请求日志全局拦截处理
 */
@Slf4j
@Component
public class LogHandlerFilter extends AbstractGatewayFilterFactory {


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            //TODO:获取当前用户信息，存在jar冲突 待解决
            //SecruityUser user=SecurityUtils.getUserInfo();

            //从request获取请求信息
            ServerHttpRequest request = exchange.getRequest();
            LogHttpRequestHandler requestDecorator = new LogHttpRequestHandler(request);
            InetSocketAddress address = requestDecorator.getRemoteAddress();

            HttpMethod method = requestDecorator.getMethod();
            URI url = requestDecorator.getURI();
            HttpHeaders headers = requestDecorator.getHeaders();
            String userAgent = headers.get("user-agent").get(0);
            Flux<DataBuffer> body = requestDecorator.getBody();
            //读取requestBody传参
            AtomicReference<String> requestBody = new AtomicReference<>("");
            body.subscribe(buffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
                requestBody.set(charBuffer.toString());
            });
            String requestParams = requestBody.get();
            //获取路由信息
            Map<String, Object> attributes = exchange.getAttributes();
            Route route = (Route) attributes.get("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRoute");
            String serviceName = route.getId();

            EsLog el = new EsLog();
		/*	el.setUserid(user.getId()+"");
			el.setUsername((user.getUserName()));
			el.setUserDeptId(user.getDeptId());
			el.setUserEmpNo(user.getEmpNo());*/
            el.setIp(address.getHostName());
            el.setAddress(address.getHostName() + ":" + address.getPort());
            el.setMethods(method.name());
            el.setParams(requestParams);
            el.setUrl(url.getPath());
            el.setServiceName(serviceName);
            el.setLogDate(DateUtil.now());
            el.setUserAgent(userAgent);
            el.setTag(LogType.ZTMAN_GATEWAY.toString());
            log.info("es_begin:" + JSONUtil.toJsonStr(el) + "es_end!");
            //return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build());
            return chain.filter(exchange.mutate().request(requestDecorator).build());
        };

    }
}
