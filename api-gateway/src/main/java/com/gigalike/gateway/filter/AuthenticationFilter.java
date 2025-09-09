package com.gigalike.gateway.filter;

import com.gigalike.shared.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GatewayFilter {
    JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        if (!request.getHeaders().containsKey("Authorization")) {
            return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        }
        
        String authHeader = request.getHeaders().get("Authorization").get(0);
        String token = null;
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else {
            return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
        }
        
        if (!jwtUtil.validateToken(token)) {
            return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
        }
        
        // Add user information to headers for downstream services
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);
        
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", username)
                .header("X-User-Role", role != null ? role : "USER")
                .build();
        
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error("Authentication error: {}", err);
        return response.setComplete();
    }
}
