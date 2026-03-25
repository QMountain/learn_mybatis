package com.qm.learnmybatis.util;

import com.qm.learnmybatis.entity.UserSession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    
    private final Map<String, UserSession> sessionMap = new ConcurrentHashMap<>();
    
    public String createSession(Long userId, String username) {
        String sessionId = UUID.randomUUID().toString();
        UserSession session = new UserSession(sessionId, userId, username);
        sessionMap.put(sessionId, session);
        return sessionId;
    }
    
    public UserSession getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        
        UserSession session = sessionMap.get(sessionId);
        if (session != null && session.isExpired()) {
            sessionMap.remove(sessionId);
            return null;
        }
        return session;
    }
    
    public void removeSession(String sessionId) {
        if (sessionId != null) {
            sessionMap.remove(sessionId);
        }
    }
    
    public boolean isValidSession(String sessionId) {
        return getSession(sessionId) != null;
    }
}