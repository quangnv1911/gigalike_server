package com.gigalike.auth.controller;

import com.gigalike.auth.dto.request.RegisterCollaboratorDto;
import com.gigalike.auth.service.ICollaboratorService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collaborators")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollaboratorController {
    ICollaboratorService collaboratorService;

    @GetMapping("/domain/{domain}")
    public ResponseEntity<ApiResponse<?>> getCollaboratorByHostName(@PathVariable String domain) {
        var user = collaboratorService.getCollaboratorByHostName(domain);
        return ResponseEntity.ok(ApiResponse.success("Get collaborator by host name successfully", user));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> registerCollaborator(@RequestBody RegisterCollaboratorDto request) {
        collaboratorService.registerCollaborator(request);
        return ResponseEntity.ok(ApiResponse.success("Register collaborator successfully", null));
    }
}
