package me.project.funding.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[Interceptor][Login][PRE]");
        HttpSession session = request.getSession();
        // 로그인 상태일 경우 세션 만료
        if (session.getAttribute("loginId") != null) {
            session.removeAttribute("loginId");
            session.removeAttribute("loginMemberNo");
            log.info("로그인 세션 만료");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[Interceptor][Login][POST]");
        HttpSession session = request.getSession();

        if (session.getAttribute("loginId") != null && session.getAttribute("dest") != null) {
            log.info("목적지: {}", session.getAttribute("dest"));
            modelAndView.addObject("dest", session.getAttribute("dest"));
        }
    }
}
