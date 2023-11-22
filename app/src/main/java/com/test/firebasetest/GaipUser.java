package com.test.firebasetest;

public class GaipUser {
    private String userId;
    private String userPassword;
    private String userPassword_chk;
    private String userEmail;

    // 생성자, 게터, 세터 등 필요한 메서드 추가

    public GaipUser() {
        // 기본 생성자가 필요합니다.
    }

    public GaipUser(String userId, String userPassword, String userPassword_chk, String userEmail) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userPassword_chk = userPassword_chk;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return userPassword;
    }
    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword_chk() {return userPassword_chk; }
    public void setUserPassword_chk(String userPassword_chk) { this.userPassword_chk = userPassword_chk;}

    public String getUserEmail() {return userEmail; }
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
}