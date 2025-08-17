package com.example.YakTong.domain.user.service;

import com.example.YakTong.domain.user.entity.User;
import com.example.YakTong.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 전체 조회
    @Transactional(readOnly = true)
    public List<User> getAllMembers() {
        return userRepository.findAll();
    }

    // 관리자 (Admin) 용 정보 취합 (통계용)
    @Transactional(readOnly = true)
    public List<User> getUsersByRole() {

    }

    // 보건소 정보 취합(통계용)
    @Transactional(readOnly = true)
    public List<User> getUsersByRegion() {

    }

    // 유저 찾기
    @Transactional(readOnly = true)
    public User getUserById() {

    }

    // 비밀번호 찾기용
    @Transactional(readOnly = true)
    public User getUserByEmail() {

    }
}
