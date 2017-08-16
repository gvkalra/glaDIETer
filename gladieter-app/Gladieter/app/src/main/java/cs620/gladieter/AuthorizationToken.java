package cs620.gladieter;

import org.springframework.http.HttpAuthentication;

/**
 * Created by wladimir on 23.12.16.
 */

public class AuthorizationToken extends HttpAuthentication
{
    private String key;
    public AuthorizationToken(String key)
    {
        this.key = key;
    }
    public String getHeaderValue()
    {
        return "Token "+key;
    }
}
