package kr.kh.backend.v2.service;

import kr.kh.backend.v2.entity.User;

import java.util.List;

public interface UserService {

    User findByNickname(String username);

    List<User> findAdmin();

}
