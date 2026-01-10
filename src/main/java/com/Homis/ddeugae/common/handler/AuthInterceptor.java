package com.Homis.ddeugae.common.handler;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.domain.Auth.jwt.JwtProvider;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    private String getBearerToken(final String bearer){
        if (bearer == null || !bearer.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.NOT_BEARER_TOKEN);
        }

        return bearer.substring("Bearer ".length());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 화이트리스트 URI
        if (request.getRequestURI().equals("/api/auth/refresh")) {
            return true; // refresh api는 accessToken 없이 통과
        }

        final String authorization = request.getHeader("Authorization");
        final String accessToken = getBearerToken(authorization);

        try{
            Claims claim = jwtProvider.extractToken(accessToken);
            final Long userDataId = ((Number) claim.get("userDataId")).longValue();

            // 존재하는 사용자인지 확인
            if (userRepository.findById(userDataId).isEmpty()) {
                throw new CustomException(ErrorCode.NOT_FOUND_USER);
            }

            final String userNickname = claim.get("userNickname").toString();

            request.setAttribute("userDataId", userDataId);
            request.setAttribute("userNickname", userNickname);

            return true;

        }catch (CustomException ce) {
            throw ce;
        } catch (ExpiredJwtException eje){
            throw new CustomException(ErrorCode.EXPIRED_ACCESS);
        } catch (JwtException je){
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_AUTH_ERROR);
        }
    }
}
