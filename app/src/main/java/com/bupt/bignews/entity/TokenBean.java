package com.bupt.bignews.entity;

/**
 * Created by ZhaoJie1234 on 2017/3/20.
 */
public class TokenBean {
    private String name;
    private String Token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "name='" + name + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
