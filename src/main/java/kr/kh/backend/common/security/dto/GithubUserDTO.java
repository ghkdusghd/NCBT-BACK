package kr.kh.backend.common.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GithubUserDTO {

    private Long id;
    private String login; // GitHub username
    private String name;
    private String email;
    private String avatar_url;

}
