/**
 * Created by wladimir on 27.11.16.
 */
package cs620.gladieter;

public class Register {
    private String username;
    private String email;
    private String password1;
    private String password2;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password1 = password;
        this.password2 = password;
    }

    public String getUserName()
    {
        return this.username;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getPassword1()
    {
        return this.password1;
    }

    public String getPassword2()
    {
        return this.password2;
    }
}
