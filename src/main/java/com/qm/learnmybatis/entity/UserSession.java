package com.qm.learnmybatis.entity;

import java.time.LocalDateTime;

public class UserSession {
    private String sessionId;
    private Long userId;
    private String username;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
    
    public UserSession() {
        this.createTime = LocalDateTime.now();
        this.expireTime = this.createTime.plusHours(24);
    }
    
    public UserSession(String sessionId, Long userId, String username) {
        this();
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getExpireTime() {
        return expireTime;
    }
    
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}