package com.gigalike.payment.feign;

import com.gigalike.payment.dto.request.UpdateUserAmount;
import com.gigalike.shared.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @PutMapping("/users/amount/{userName}")
    ApiResponse<?> updateUserAmount(@PathVariable("userName") String userName, @RequestBody UpdateUserAmount body);
}

