package kr.kh.backend.common.security.dto;

public interface Oauth2UserInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
