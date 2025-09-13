package com.gigalike.auth.service;

import com.gigalike.auth.dto.request.RegisterCollaboratorDto;
import com.gigalike.auth.dto.response.CollaboratorResponse;
import com.gigalike.auth.dto.response.CollaboratorViewResponse;

import java.util.List;
import java.util.UUID;

public interface ICollaboratorService {
    CollaboratorResponse getCollaboratorByHostName(String hostName);

    void registerCollaborator(RegisterCollaboratorDto request);

    List<CollaboratorViewResponse> getAllCollaboratorOfMe(UUID userId);
}
