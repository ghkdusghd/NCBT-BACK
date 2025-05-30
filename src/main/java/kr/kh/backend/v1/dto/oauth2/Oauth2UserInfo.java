package kr.kh.backend.v1.dto.oauth2;

public interface Oauth2UserInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
