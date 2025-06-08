package kr.kh.backend.v2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kh.backend.common.security.jwt.JwtTokenProvider;
import kr.kh.backend.common.security.service.Oauth2UserService;
import kr.kh.backend.common.security.service.UserService;
import kr.kh.backend.common.security.dto.OauthLoginDTO;
import kr.kh.backend.common.security.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/v2")
@Tag(name = "UserControllerV2 (사용자 API)")
public class UserControllerV2 {

    private final UserService userService;
    private final Oauth2UserService oauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    // 토큰 재발급
    @Operation(summary = "Refresh Token 으로 Access Token 재발급",
            description = "사용자의 인증 정보를 유지하기 위해 Refresh Token 이 유효하다면 Access Token 을 재발급 합니다. " +
                    "이 과정은 보안을 위해 단 한 번만 이루어집니다.")
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken() {
        String newAccessToken = jwtTokenProvider.refreshAccessToken(SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .build();
    }

    // 로그아웃
    @Operation(summary = "사용자가 로그아웃하면 Refresh Token 만료 처리",
            description = "DB 에 저장한 Refresh Token 을 EXPIRED 처리하고, Cookie 의 만료 시간을 0 으로 변경하여 삭제합니다.")
    @PostMapping("/form/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // DB 에 저장된 리프레쉬 토큰 EXPIRED 처리
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("refreshToken")) {
                         String refreshToken = cookie.getValue();
                         userService.logout(refreshToken);
                    }
                }
            }

            // 브라우저 토큰 삭제
            Cookie cookie = new Cookie("refreshToken", "");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0); // 만료 시간을 0 으로 하여 쿠키 삭제 !!
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    // 네이버 로그인
    @Operation(summary = "네이버 소셜 로그인", description = "OAuth2 인증을 통한 소셜 로그인 및 JWT 생성")
    @PostMapping("/login/naver")
    public ResponseEntity<?> loginNaver(@RequestBody OauthLoginDTO oauthLoginDTO, HttpServletResponse response) {
        log.info("네이버 로그인 컨트롤러");

        // 네이버에서 사용자 정보 조회
        Authentication authentication = oauth2UserService.getNaverUser(oauthLoginDTO.getCode(), oauthLoginDTO.getState());
        if(authentication == null) {
            return ResponseEntity.status(400).build();
        }

        // JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // HttpOnly 쿠키에 리프레시 토큰 넣어서 전송
        Cookie refreshCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(24 * 60 * 60);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken.getAccessToken())
                .header("Set-Cookie", "refreshToken=" + jwtToken.getRefreshToken()
                        + "; Path=/; HttpOnly; Max-Age=86400; SameSite=Lax" )
                .build();
    }

    // 깃허브 로그인
    @Operation(summary = "깃허브 소셜 로그인", description = "OAuth2 인증을 통한 소셜 로그인 및 JWT 생성")
    @PostMapping("/login/github")
    public ResponseEntity<?> loginGithub(@RequestBody OauthLoginDTO oauthLoginDTO, HttpServletResponse response) {
        log.info("깃허브 로그인 컨트롤러");

        // 깃허브에서 사용자 정보 조회
        Authentication authentication = oauth2UserService.getGithubUser(oauthLoginDTO.getCode());
        if(authentication == null) {
            return ResponseEntity.status(400).build();
        }

        // JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // HttpOnly 쿠키에 리프레시 토큰 넣어서 전송
        Cookie refreshCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(24 * 60 * 60);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken.getAccessToken())
                .header("Set-Cookie", "refreshToken=" + jwtToken.getRefreshToken()
                        + "; Path=/; HttpOnly; Max-Age=86400; SameSite=Lax" )
                .build();
    }

}
