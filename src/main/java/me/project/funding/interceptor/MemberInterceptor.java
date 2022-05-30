package me.project.funding.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Slf4j
public class MemberInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[Interceptor][Member][PRE]");
        HttpSession session = request.getSession();

        // 로그인 여부 겸증
        if (session.getAttribute("loginId") == null) {
            log.info(">> 로그인 정보가 존재하지 않음");
            // 현재 URI 저장 (GET 요청일 경우에만)
            if (request.getMethod().equals("GET")) saveDest(request);

            // 로그인 정보가 존재하지 않을 경우 알림창을 띄우고 로그인 페이지로 이동
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인이 필요한 서비스입니다.');location.href='/member/login'</script>");
            return false;
        }
        log.info(">> 로그인 인증 완료");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private void saveDest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        HttpSession session = request.getSession();

        if (query == null || query.equals("null")) {
            query = "";
        } else {
            query = "?" + query;
        }

        log.info("uri: {}, query: {}", uri, query);

        session.setAttribute("dest", uri + query);
        log.info("저장된 주소: {}", session.getAttribute("dest"));

        // (참고)
        // referer header 사용 가능성 체크해보기
        String referer = request.getHeader("referer");
        log.info("referer: {}", referer);
    }
}
