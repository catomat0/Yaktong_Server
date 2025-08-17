package com.example.YakTong.domain.user.repository;

import com.example.YakTong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
