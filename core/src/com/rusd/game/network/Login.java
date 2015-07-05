package com.rusd.game.network;

/**
 * Created by shane on 7/4/15.
 */
public class Login {


    public Login() {
    }
    

    private String username;
    private String password;
    private String loginResponse;
    private Boolean success;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(String loginResponse) {
        this.loginResponse = loginResponse;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
