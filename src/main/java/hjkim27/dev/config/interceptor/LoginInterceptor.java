package hjkim27.dev.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <pre>
 *     로그인 여부 확인 interceptor
 * </pre>
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 로그인 여부 확인 로직 추가
        // ex: 세션에서 사용자 정보 확인
        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트

        return true; // 로그인된 경우 true 반환
    }
}
