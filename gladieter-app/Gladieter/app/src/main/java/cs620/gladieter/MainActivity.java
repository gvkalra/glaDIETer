package cs620.gladieter;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    protected void onSignUp(View view)
    {
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

    protected void onLogIn(View view)
    {
        EditText user = (EditText)findViewById(R.id.txtUser);
        EditText password = (EditText)findViewById(R.id.txtPassword);
        JSONObject object = new JSONObject();
        try{
            object.put("username",user.getText().toString());
            object.put("password",password.getText().toString());
        }
        catch (JSONException e)
        {

        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(object.toString(),header);
            ResponseEntity registerResponse = restTemplate.exchange("https://gladieter.herokuapp.com/auth/login/", HttpMethod.POST, entity, String.class);
            if(registerResponse.getStatusCode()== HttpStatus.OK)
            {
                JSONObject response = new JSONObject(registerResponse.getBody().toString());
                new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Login Successfull").create().show();
                String key = response.getString("key");
                Intent intent = new Intent(this,StartMenu.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
            else
            {
                new AlertDialog.Builder(this).setTitle("Login").setMessage("Could not Log In").create().show();
            }
        }
        catch(HttpClientErrorException e)
        {
            new AlertDialog.Builder(this).setTitle("Login").setMessage("Could not Log In").create().show();
        }
        catch(JSONException e)
        {
            new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Invalid Response").create().show();
        }
    }
}
