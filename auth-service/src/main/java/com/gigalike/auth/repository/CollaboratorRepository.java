package com.gigalike.auth.repository;

import com.gigalike.auth.entity.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {
    Optional<Collaborator> findFirstByDomain(String domain);

    List<Collaborator> findAllByParentId(UUID parentId);
}
