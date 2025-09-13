package com.gigalike.auth.dto.response;

import com.gigalike.auth.entity.Collaborator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CollaboratorResponse {
    UUID id;
    UUID userId;
    String domain;

    public static CollaboratorResponse fromEntity(Collaborator collaborator) {

        return CollaboratorResponse.builder()
                .id(collaborator.getId())
                .userId(collaborator.getUserId())
                .domain(collaborator.getDomain())
                .build();
    }
}
