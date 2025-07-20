package hjkim27.dev.config.interceptor;

import hjkim27.dev.config.GeneralConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *     UI 에서 사용하기 위한 공통 변수 추가를 위한 interceptor 추가
 *     - preHandle 일 경우 ajax json 응답여부 확인 조건 추가
 *     - postHandle 에서는 modelAndView 로 확인 진행
 * </pre>
 *
 * @since 2025.04
 */
public class GlobalAttributeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAjax = isAjax(request);
        boolean isRestMethod = isRestMethod(handler);

        if (isAjax || isRestMethod) {
            // json 응답
        } else {
            // 일반 view 응답
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // view 기반 html 랜더링이 있는지 확인
        if (modelAndView != null) {
            modelAndView.getModelMap();

            modelAndView.addObject("DEBUG_MODE", GeneralConfig.DEBUG_MODE);

            // 수정페이지 확인용
            String viewName = modelAndView.getViewName();
            modelAndView.addObject("IS_EDIT", viewName.contains("edit"));
        }
    }

    /**
     * <pre>
     *     ajax 통신여부 확인
     *     - 클라이언트가 ajax 임을 명시적으로 알려준 경우에만 true
     *     - fetch, axios, xmlHttpRequest 사용 시 수동 설정 필요 > 누락 시 false
     *     - 클라이언트가 헤더 조작 가능
     * </pre>
     *
     * @param request
     * @return
     */
    public boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Request-With"));
    }

    /**
     * <pre>
     *     서버 응답 방식이 json 인지 확인 (view rendering X)
     *     - @{@link RestController} 혹은 @{@link ResponseBody} 사용여부로 확인
     *     - 클라이언트 방식과 관계없이 서버 응답이 json 이면 true 지만
     *       일반 form 요청일 경우에도 json 으로 받을 수 있는 문제가 있음.
     * </pre>
     *
     * @param handler
     * @return
     */
    public boolean isRestMethod(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 1. @ResponseBody 확인
            boolean isResponseBody = handlerMethod.getMethodAnnotation(ResponseBody.class) != null;
            // 2. @RestController / @Controller + @ResponseBody 확인 
            boolean isRestController = handlerMethod.getBeanType().isAnnotationPresent(RestController.class);
            return isResponseBody || isRestController;
        }
        return false;
    }
}
