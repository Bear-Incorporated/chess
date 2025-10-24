package model;

public record User_Request_Login(String username, String password)
{
    public User_Request_Login() {
        this("", "");
    }



}
