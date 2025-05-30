package kr.kh.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // RestClient 학습을 위해 무료 공개 API (JSONPlaceholder) 로 요청
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .build();
    }

}
