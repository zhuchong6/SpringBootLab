package com.zhu.message.auth;

import com.zhu.dispacher.Message;

/**
 * @author by zhuhcong
 * @descr
 * @date 2023/2/17 15:13
 */
public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证token
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}