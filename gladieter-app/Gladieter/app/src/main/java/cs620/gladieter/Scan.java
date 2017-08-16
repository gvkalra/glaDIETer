package cs620.gladieter;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidplot.util.PixelUtils;
import com.androidplot.util.SeriesUtils;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

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

import java.math.BigInteger;
import java.util.Date;

public class Scan extends AppCompatActivity {
    private String key;
    private BigInteger gtin;
    private int cookingTime;
    private boolean recalled;
    private boolean expired;
    private XYPlot mySimpleXYPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        this.key = getIntent().getStringExtra("key");
        this.gtin = (BigInteger) getIntent().getExtras().get("gtin");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            HttpHeaders header = new HttpHeaders();
            AuthorizationToken token = new AuthorizationToken(key);
            header.setContentType(MediaType.APPLICATION_JSON);
            header.setAuthorization(token);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            ResponseEntity nutritionResponse = restTemplate.exchange("https://gladieter.herokuapp.com/v1/product/"+gtin, HttpMethod.GET, entity, String.class);
            if(nutritionResponse.getStatusCode()== HttpStatus.OK)
            {
                float cholesterol = 0.0f;
                float fat = 0.0f;
                float sodium = 0.0f;
                float protein = 0.0f;
                JSONObject response = new JSONObject(nutritionResponse.getBody().toString());
                JSONObject info = response.getJSONObject("nutritional_information");
                this.cookingTime = response.getInt("cooking_time");
                this.recalled = response.getBoolean("recalled");
                /*Date expiry = new Date(response.getString("expiry_date"));
                if(expiry.before(new Date()))
                {
                    this.expired = true;
                }*/
                cholesterol += Math.abs(info.getDouble("cholesterol"));
                fat += Math.abs(info.getDouble("fat"));
                sodium += Math.abs(info.getDouble("sodium"));
                protein += Math.abs(info.getDouble("protein"));

                // initialize our XYPlot reference:
                mySimpleXYPlot = (XYPlot) findViewById(R.id.nutritionHistory);

                // Create Bar formaters
                BarFormatter cholesterolFormatter = new BarFormatter(Color.RED,Color.WHITE);
                BarFormatter fatFormatter = new BarFormatter(Color.BLUE,Color.WHITE);
                BarFormatter sodiumFormatter = new BarFormatter(Color.YELLOW,Color.WHITE);
                BarFormatter proteinFormatter = new BarFormatter(Color.GREEN,Color.WHITE);
                // Turn the above arrays into XYSeries':
                XYSeries cholesterolSeries = new SimpleXYSeries(
                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                        "Choles.",cholesterol);                             // Set the display title of the series
                XYSeries fatSeries = new SimpleXYSeries(
                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                        "Fat",fat);                             // Set the display title of the series
                XYSeries sodiumSeries = new SimpleXYSeries(
                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                        "Sodium",sodium);                             // Set the display title of the series
                XYSeries proteinSeries = new SimpleXYSeries(
                        SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                        "Protein",protein);                             // Set the display title of the series
                mySimpleXYPlot.addSeries(cholesterolSeries,cholesterolFormatter);
                mySimpleXYPlot.addSeries(fatSeries,fatFormatter);
                mySimpleXYPlot.addSeries(sodiumSeries,sodiumFormatter);
                mySimpleXYPlot.addSeries(proteinSeries,proteinFormatter);

                BarRenderer renderer = mySimpleXYPlot.getRenderer(BarRenderer.class);
                renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
                renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(100));

                mySimpleXYPlot.setLinesPerDomainLabel(4);
                mySimpleXYPlot.setDomainBoundaries(-0.5,0.5, BoundaryMode.FIXED);
                mySimpleXYPlot.setRangeBoundaries(0.0, SeriesUtils.minMax(fatSeries,cholesterolSeries,sodiumSeries,proteinSeries).getMaxY().doubleValue() + 1,BoundaryMode.FIXED);


                mySimpleXYPlot.redraw();

                // reduce the number of range labels
                //mySimpleXYPlot.setTicksPerRangeLabel(3);
            }
            else
            {
                new AlertDialog.Builder(this).setTitle("Login").setMessage("Unable to query Cloud").create().show();
            }
        }
        catch(HttpClientErrorException e)
        {
            new AlertDialog.Builder(this).setTitle("Login").setMessage("Unable to query Cloud").create().show();
        }
        catch(JSONException e)
        {
            new AlertDialog.Builder(this).setTitle("Sign Up").setMessage("Unable to query Cloud").create().show();
        }
    }

    protected void onCook(View v)
    {
        if(expired)
        {
            new AlertDialog.Builder(this).setTitle("Product expired").setMessage("This product is expired!").create().show();
        }
        if(recalled)
        {
            new AlertDialog.Builder(this).setTitle("Product recalled").setMessage("This product is recalled!").create().show();
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            HttpHeaders header = new HttpHeaders();
            AuthorizationToken token = new AuthorizationToken(key);
            header.setContentType(MediaType.APPLICATION_JSON);
            header.setAuthorization(token);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            ResponseEntity nutritionResponse = restTemplate.exchange("http://143.248.134.191/start_cooking.py?q=" + cookingTime, HttpMethod.GET, entity, String.class);
            if (nutritionResponse.getStatusCode() == HttpStatus.OK)
            {
                JSONObject object = new JSONObject();
                try{
                    object.put("gtins",gtin);
                }
                catch(JSONException e)
                {

                }
                HttpEntity<String> entity2 = new HttpEntity<String>(object.toString(),header);
                ResponseEntity consumeResponse = restTemplate.exchange("https://gladieter.herokuapp.com/v1/me/consume", HttpMethod.POST, entity2, String.class);
                if(consumeResponse.getStatusCode()==HttpStatus.CREATED)
                {
                    new AlertDialog.Builder(this).setTitle("Product cooked").setMessage("This product is cooked!").create().show();
                }
            }
        }
        catch (Exception e)
        {
            new AlertDialog.Builder(this).setTitle("Unable to query Microwave").setMessage("Unable to query Microwave").create().show();
        }
    }
}
