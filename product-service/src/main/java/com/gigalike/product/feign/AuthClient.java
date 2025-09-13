package com.gigalike.product.feign;

import com.gigalike.product.dto.response.CollaboratorResponse;
import com.gigalike.shared.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @PutMapping("/collaborators/domain/{domain}")
    ApiResponse<CollaboratorResponse> getCollaboratorByHostName(@PathVariable("domain") String domain);
}

