package ru.netology.CloudStorage.Login;

public class LoginResponse {
    private String sessionId;

    public LoginResponse(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getSessionId() {
        return sessionId;
    }
}
