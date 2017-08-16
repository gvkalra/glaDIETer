package cs620.gladieter;

/**
 * Created by wladimir on 27.11.16.
 */

public class Login {
    private String username;
    private String password;

    public void setUser(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUser()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }
}
