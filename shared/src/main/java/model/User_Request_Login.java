package model;

public record User_Request_Login(String username, String password, String authToken)
{
    public User_Request_Login() {
        this("", "", "");
    }



}
