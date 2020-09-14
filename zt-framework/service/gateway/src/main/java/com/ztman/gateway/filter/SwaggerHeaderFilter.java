package com.ztman.gateway.filter;

import com.ztman.gateway.config.SwaggerProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;


@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {
	private static final String HEADER_NAME = "X-Forwarded-Prefix";

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response= exchange.getResponse();
			String path = request.getURI().getPath();
			if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
				return chain.filter(exchange);
			}

			String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));


			ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

			return chain.filter(newExchange);
		};
	}
}
