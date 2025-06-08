package kr.kh.backend.v2.service;

import kr.kh.backend.common.exception.custom.UserNotFoundException;
import kr.kh.backend.v2.entity.User;
import kr.kh.backend.v2.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByNickname(String username) {
        return userRepository.findByNickname(username)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다.", LocalDateTime.now(), HttpStatus.BAD_REQUEST));
    }

}
