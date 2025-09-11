package com.gigalike.auth.repository;

import com.gigalike.auth.entity.RefreshToken;
import com.gigalike.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    @Query(value = "SELECT * FROM refresh_token rt " +
            "WHERE rt.expires_at < ?1", nativeQuery = true)
    List<RefreshToken> findRefreshTokenByExpiredIsBefore(LocalDateTime expired);
}
