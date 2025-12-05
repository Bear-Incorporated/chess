package model;

public record userRequestLogin(String username, String password)
{
    public userRequestLogin() {
        this("", "");
    }



}
