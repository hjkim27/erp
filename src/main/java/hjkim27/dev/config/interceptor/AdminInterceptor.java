package hjkim27.dev.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <pre>
 *     관리자 권한 확인을 위한 interceptor
 *     - 관리자 권한이 필요한 요청에 대해 접근을 제어
 * </pre>
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 관리자 권한 확인 로직 추가
        // 예: 세션에서 사용자 권한 확인
        // 관리자 권한이 없는 경우 접근 거부 처리

        return true; // 관리자 권한이 있는 경우 true 반환
    }
}
