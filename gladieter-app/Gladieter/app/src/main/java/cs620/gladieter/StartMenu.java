package cs620.gladieter;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Size;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidplot.ui.SizeMetric;
import com.androidplot.util.PixelUtils;
import com.androidplot.util.SeriesUtils;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.StepModel;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
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
import java.util.Arrays;

public class StartMenu extends AppCompatActivity {
    private String key;
    private BluetoothAdapter BA;
    private XYPlot mySimpleXYPlot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        this.key = getIntent().getStringExtra("key");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            HttpHeaders header = new HttpHeaders();
            AuthorizationToken token = new AuthorizationToken(key);
            header.setContentType(MediaType.APPLICATION_JSON);
            header.setAuthorization(token);
            HttpEntity<String> entity = new HttpEntity<String>(header);
            ResponseEntity nutritionResponse = restTemplate.exchange("https://gladieter.herokuapp.com/v1/me/products/1440", HttpMethod.GET, entity, String.class);
            if(nutritionResponse.getStatusCode()== HttpStatus.OK)
            {
                float cholesterol = 0.0f;
                float fat = 0.0f;
                float sodium = 0.0f;
                float protein = 0.0f;
                JSONArray response = new JSONArray(nutritionResponse.getBody().toString());
                for(int i=0;i<response.length();i++)
                {
                    JSONObject prod = response.getJSONObject(i);
                    JSONObject prodinfo = prod.getJSONObject("product_information");
                    JSONObject info = prodinfo.getJSONObject("nutritional_information");
                    cholesterol += Math.abs(info.getDouble("cholesterol"));
                    fat += Math.abs(info.getDouble("fat"));
                    sodium += Math.abs(info.getDouble("sodium"));
                    protein += Math.abs(info.getDouble("protein"));
                }

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
                mySimpleXYPlot.setDomainBoundaries(-0.5,0.5,BoundaryMode.FIXED);
                mySimpleXYPlot.setRangeBoundaries(0.0,SeriesUtils.minMax(fatSeries,cholesterolSeries,sodiumSeries,proteinSeries).getMaxY().doubleValue() + 1,BoundaryMode.FIXED);


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
    protected void onScan(View v)
    {
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.initiateScan();
    }

    protected void onPairing(View v)
    {
        BA = BluetoothAdapter.getDefaultAdapter();
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivity(turnOn);
        BA.startDiscovery();
    }

    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        //Successfull Scan
        if(result!=null)
        {
            Intent intent2 = new Intent(this,Scan.class);
            intent2.putExtra("key",this.key);
            intent2.putExtra("gtin",new BigInteger(result.getContents()));
            startActivity(intent2);
        }
    }
}
