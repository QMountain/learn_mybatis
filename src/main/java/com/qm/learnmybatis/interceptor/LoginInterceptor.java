package com.qm.learnmybatis.interceptor;

import com.qm.learnmybatis.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    
    @Autowired
    private SessionManager sessionManager;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String requestUri = request.getRequestURI();
        
        // 登录和登出接口不需要拦截
        if (requestUri.contains("/login") || requestUri.contains("/logout")) {
            return true;
        }
        
        // 从请求头或参数中获取sessionId
        String sessionId = request.getHeader("sessionId");
        if (sessionId == null) {
            sessionId = request.getParameter("sessionId");
        }
        
        // 验证sessionId
        if (sessionId == null || !sessionManager.isValidSession(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":false,\"message\":\"请先登录\"}");
            return false;
        }
        
        // 将用户信息添加到请求属性中，方便后续使用
        request.setAttribute("sessionId", sessionId);
        request.setAttribute("userSession", sessionManager.getSession(sessionId));
        
        return true;
    }
}