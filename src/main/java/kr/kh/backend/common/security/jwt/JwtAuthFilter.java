package kr.kh.backend.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("do JWT Filter ! request = {}", request);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = ((HttpServletRequest) request).getRequestURI();

        // Swagger 관련 경로가 있는지 확인
        if (isSwaggerPath(path)) {
            log.info("Swagger path detected: {}", path);
            chain.doFilter(request, response); // Swagger 경로는 필터를 거치지 않고 바로 통과
            return;
        }

        String requestPath = httpRequest.getRequestURI();
        log.info("요청 경로: {}", requestPath);

        // 모든 로그인 요청에 대해 예외 처리 (필터를 통과시킴)
        if (path.startsWith("/form/") || path.startsWith("/login/") || path.startsWith("/ranking/")) {
            log.info("httpUri = {}", httpRequest.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        // Request Header 에서 access-token 추출
        String accessToken = resolveToken((HttpServletRequest) request);
        log.info("accessT$oken is null ? {}", accessToken);

        // Cookie 에서 refresh-token 추출
        String refreshToken = getCookie((HttpServletRequest) request);
        log.info("refreshToken is null ? {}", refreshToken);

        // validate Token 으로 유효성 검사
        if(accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            // 액세스 토큰이 유효한 경우
            log.info("validate token : {}", accessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
            // 액세스 토큰이 없거나 혹은 유효하지 않지만 리프레시 토큰이 유효한 경우
            log.info("validate refresh token : {}", refreshToken);
            Authentication authentication = jwtTokenProvider.createAuthentication(refreshToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 둘다 유효하지 않은 경우
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Both access token and refresh token are invalid");
        }

        log.info("Result of JWT Filter ! SecurityContextHolder = {}", SecurityContextHolder.getContext());
        chain.doFilter(request, response);
    }

    /**
     * 액세스 토큰 추출 : 주어진 HttpServletRequest 의 Authorization 헤더에서 Bearer 접두사로 시작하는 토큰을 추출하여 반환
     */
    private String resolveToken(HttpServletRequest request) {
        log.info("resolveToken request = {}", request);
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer")) {
            log.info("bearerToken = {}", bearerToken);
            return bearerToken.substring(7);
        }
        log.info("Access Token null");
        return null;
    }

    /**
     * 리프레시 토큰 추출 : HttpOnly 쿠키에서 'refreshToken' 이름의 쿠키를 추출하여 반환
     */
    private String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        log.info("Refresh Token null");
        return "Refresh Token null";
    }

    /**
     * Swagger 관련 경로를 체크하는 메서드
     */
    private boolean isSwaggerPath(String path) {
        return path.startsWith("/swagger-ui.html") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/api-docs/json/swagger-config") ||
                path.startsWith("/api-docs/json");
    }

}
