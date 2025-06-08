package kr.kh.backend.common.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GithubLoginDTO {

    private String access_token;

}
