package com.http.qnhlli.myhttptest2.bean;

/**
 * Created by qnhlli on 2016/6/20.
 */
public class LoginBean {
    private String userName;
    private String loginPwd;

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
