package com.atguigu.springcloud.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UriKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
      //  String path = exchange.getRequest().getURI().getPath();
        String path = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        log.info("path="+path);
        return Mono.just(path);
    }
}
