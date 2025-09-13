package com.gigalike.auth.service.impl;

import com.gigalike.auth.dto.request.RegisterCollaboratorDto;
import com.gigalike.auth.dto.response.CollaboratorResponse;
import com.gigalike.auth.dto.response.CollaboratorViewResponse;
import com.gigalike.auth.entity.Collaborator;
import com.gigalike.auth.repository.CollaboratorRepository;
import com.gigalike.auth.service.ICollaboratorService;
import com.gigalike.shared.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollaboratorService implements ICollaboratorService {
    CollaboratorRepository collaboratorRepository;

    @Override
    public CollaboratorResponse getCollaboratorByHostName(String hostName) {
        var response = getCollaboratorByDomain(hostName);
        return CollaboratorResponse.fromEntity(response);
    }

    @Override
    public void registerCollaborator(RegisterCollaboratorDto request) {
        var parrentCollaborator = getCollaboratorByDomain(request.getHostName());
        Collaborator newCollaborator = Collaborator.builder()
                .parentId(parrentCollaborator.getParentId())
                .domain(parrentCollaborator.getDomain())
                .userId(request.getUserId())
                .build();
        var newEntity = collaboratorRepository.save(newCollaborator);
        log.info("Collaborator with id {}has been registered successfully", newEntity.getId());
    }

    @Override
    public List<CollaboratorViewResponse> getAllCollaboratorOfMe(UUID userId) {
        var response = collaboratorRepository.findAllByParentId(userId);
        return List.of();
    }

    private CollaboratorViewResponse convertToResponse(Collaborator collaborator) {


        return CollaboratorViewResponse.builder()
                .domain(collaborator.getDomain())
                .email()
                .userName()
                .totalBalance()
                .build();
    }

    private Collaborator getCollaboratorByDomain(String domain) {
        return collaboratorRepository.findFirstByDomain(domain).orElseThrow(() -> new NotFoundException("Collaborator not found"));
    }
}
