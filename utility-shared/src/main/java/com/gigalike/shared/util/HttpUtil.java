package com.gigalike.shared.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigalike.shared.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HttpUtil {
    /**
     * Call post api
     *
     * @return response object
     * @author LoiDV
     */
    public static String callAPI(String url, String requestBody) throws HttpException {
        HttpHeaders httpHeaders = new HttpHeaders();
        log.info("url: " + url);
        log.info("requestBody: " + requestBody);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    public <T> T callPostAPIAuth(String url, Object requestBody, Class<T> responseClass) throws HttpException {
        return callAPIAuth(url, HttpMethod.POST, requestBody, responseClass);
    }

    public <T> T callAPIAuth(String url, HttpMethod method, Object requestBody, Class<T> responseClass) throws HttpException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + CommonUtil.getToken());
        return callAPI(url, header, method, requestBody, responseClass);
    }

    public <T> T callAPI(String url, Map<String, String> mapHeader, HttpMethod method, Object requestBody, Class<T> responseClass) throws HttpException {
        T response = null;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            if (mapHeader != null) {
                mapHeader.forEach(httpHeaders::set);
            }

            HttpEntity<String> httpEntity;
            if (requestBody != null) {
                String body;
                if (requestBody instanceof Map) {
                    ObjectMapper mapper = new ObjectMapper();
                    body = mapper.writeValueAsString(requestBody);
                } else {
                    body = requestBody.toString();
                }

                httpEntity = new HttpEntity<>(body, httpHeaders);
            } else {
                httpEntity = new HttpEntity<>(httpHeaders);
            }
            RestTemplate restTemplate = new RestTemplate();
            if (HttpMethod.POST.equals(method)) {
                response = restTemplate.postForObject(url, httpEntity, responseClass);
            } else {
                response = restTemplate.exchange(url, method, httpEntity, responseClass).getBody();
            }
            assert response != null;
            log.info(response.toString());
        } catch (Exception ex) {
            log.error("Call external API Exception: ", ex);
            throw new HttpException("call API lỗi " + url);
        }
        return response;
    }

    public static String callPostAPIByToken(String url, String requestBody, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.setBearerAuth(token);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    public static String callPostAPIWithPublicKey(String url, String requestBody, String publicKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Set public key vào header "key"
        httpHeaders.set("key", publicKey);

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(url, httpEntity, String.class);
    }
}
