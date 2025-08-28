package com.example.YakTong.domain.auth.user.repository;

import com.example.YakTong.domain.auth.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> existsByEmail(String email);

    Optional<UserEntity> existsByUsername(String username);

    Optional<UserEntity> findByUsernameAndIsLockAndIsSocial(String username, Boolean isLock, Boolean isSocial);

    Optional<UserEntity> findByUsernameAndIsSocial(String username, boolean isSocial);

    Optional<UserEntity> findByUsernameAndIsLock(String username, Boolean isLock);

    @Transactional
    void deleteByUsername(String username);

    Boolean existsByLoginId(String loginId);

    Optional<UserEntity> findByLoginIdAndIsLockAndIsSocial(String loginId, boolean isLock, boolean isSocial);

    Optional<UserEntity> findByLoginIdAndIsSocial(String loginId, boolean isSocial);

    void deleteByLoginId(String loginId);
}
