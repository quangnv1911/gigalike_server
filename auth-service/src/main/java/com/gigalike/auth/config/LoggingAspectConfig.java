package com.gigalike.auth.config;

import com.gigalike.shared.util.CommonUtil;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class LoggingAspectConfig {
    @Around("execution(* com.gigalike.auth.controller..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String url = request.getRequestURI();
        String fullUrl = CommonUtil.getFullUrl(request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "Anonymous";

        MDC.put("trackId", request.getHeader("X-TRACK-ID"));
        MDC.put("username", username);
        MDC.put("method", request.getMethod());
        MDC.put("url", fullUrl);

        Object[] parameters = proceedingJoinPoint.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<>();
        if (parameterNames != null && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                // Kiểm tra xem tham số có phải là MultipartFile hay không
                if (!(parameters[i] instanceof MultipartFile) && !(parameters[i] instanceof MultipartFile[])) {
                    parameterMap.put(parameterNames[i], parameters[i]);
                }
            }
        }
        log.info("{} {} - {} - Parameters: {}", request.getMethod(), url, username, new Gson().toJson(parameterMap));
        final StopWatch stopWatch = new StopWatch();
        // Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();


        log.info(request.getMethod() +
                " " +
                url +
                " - " +
                username +
                " - Response: " +
                stopWatch.getTotalTimeMillis() +
                "ms - " +
                result.toString());


        MDC.remove("trackId");
        MDC.remove("username");
        MDC.remove("method");
        MDC.remove("url");

        return result;
    }
}
