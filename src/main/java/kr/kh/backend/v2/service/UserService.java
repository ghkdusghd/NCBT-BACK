package kr.kh.backend.v2.service;

import kr.kh.backend.v2.entity.User;

public interface UserService {
    User findByNickname(String username);
}
