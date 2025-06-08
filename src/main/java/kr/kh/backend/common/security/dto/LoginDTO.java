package kr.kh.backend.common.security.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class LoginDTO {
    private String username;
    private String password;
    private String email;
    private String roles;
}
