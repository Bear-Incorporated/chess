package model;

public record UserRequestLogin(String username, String password)
{
    public UserRequestLogin() {
        this("", "");
    }



}
