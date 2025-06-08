package kr.kh.backend.common.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OauthLoginDTO {

    private String code;
    private String state;

}
