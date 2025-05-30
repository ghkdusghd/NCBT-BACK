package kr.kh.backend.v1.domain;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class EmailVerification {

    private String email;
    private String authCode;
    private LocalDateTime expirationTime;
}
