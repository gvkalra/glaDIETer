package cs620.gladieter;

import android.content.Intent;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    protected void onSignup(View v)
    {
        EditText user = (EditText)findViewById(R.id.textUser);
        EditText email = (EditText)findViewById(R.id.textEmail);
        EditText password1 = (EditText)findViewById(R.id.textPassword);
        EditText password2 = (EditText)findViewById(R.id.textPassword2);
        if(password1.getText().toString().equals(password2.getText().toString())) {
            Register register = new Register();
            JSONObject object = new JSONObject();
            try{
                object.put("username",user.getText().toString());
                object.put("email",email.getText().toString());
                object.put("password1",password1.getText().toString());
                object.put("password2",password2.getText().toString());
            }
            catch (JSONException e)
            {

            }
            register.setUsername(user.getText().toString());
            register.setEmail(user.getText().toString());
            register.setPassword(password1.getText().toString());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            try {
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(object.toString(),header);
                ResponseEntity registerResponse = restTemplate.exchange("https://gladieter.herokuapp.com/auth/registration/", HttpMethod.POST, entity, String.class);
                if(registerResponse.getStatusCode()== HttpStatus.CREATED)
                {
                    JSONObject response = new JSONObject(registerResponse.getBody().toString());
                    new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Registration Successful").create().show();
                    String key = response.getString("key");
                    Intent intent = new Intent(this,StartMenu.class);
                    startActivity(intent);
                }
                else
                {
                    JSONObject response = new JSONObject(registerResponse.getBody().toString());
                    String userError = response.getString("username");
                    String emailError = response.getString("email");
                    String password1Error = response.getString("password1");
                    String errorMessage = userError+"\r\n"+emailError+"\r\n"+password1Error+"\r\n";
                    new AlertDialog.Builder(this).setTitle("Login").setMessage(errorMessage).create().show();
                }
            }
            catch(HttpClientErrorException e)
            {
                try {
                    JSONObject response = new JSONObject(e.getResponseBodyAsString());
                    String error = "";
                    if(response.has("username"))
                        error += response.getString("username")+"\r\n";
                    if(response.has("email"))
                        error += response.getString("email")+"\r\n";
                    if(response.has("password1"))
                        error += response.getString("password1")+"\r\n";
                    if(response.has("password2"))
                        error += response.getString("password2");
                    new AlertDialog.Builder(this).setTitle("Login").setMessage(error).create().show();
                } catch (JSONException ej) {
                    new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Invalid Response").create().show();
                }
            }
            catch(JSONException e)
            {
                new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Invalid Response").create().show();
            }
        }
    }

}
