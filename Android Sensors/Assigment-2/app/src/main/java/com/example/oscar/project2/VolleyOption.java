package com.example.oscar.project2;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VolleyOption extends AppCompatActivity {
    private TextView tvTemp, tvHumidity, tvPressure, tvValuesFromSensor, tvAltitude;
    private final double convertToCelsius = 272.15;
    private String apiHumidity;
    private double apiPressure;
    private float pressureFromSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        Bundle extras = getIntent().getExtras();
        volleyRequest();
        tvTemp = (TextView)findViewById(R.id.tvTemp);
        tvPressure = (TextView)findViewById(R.id.tvPressure);
        tvHumidity = (TextView)findViewById(R.id.tvHumidity);
        tvValuesFromSensor = (TextView)findViewById(R.id.tvValuesFromSensor);
        tvAltitude = (TextView)findViewById(R.id.tvAltitude);


        pressureFromSensor = Float.parseFloat(extras.getString("PRESSURE-SENSOR"));
        tvValuesFromSensor.setText("Pressure: " + extras.getString("PRESSURE-SENSOR") + " mbar"+
                "\n" + extras.getString("TEMP-SENSOR") + "\n" + extras.getString("HUMIDITY-SENSOR"));
    }

    /**
     * Method that handles the connection to the API by using the Volley library
     */
    private void volleyRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String key = "here you place your API key ";
        String url= "http://api.openweathermap.org/data/2.5/weather?id=2692969&appid=f0b41cd9c0a8883eca83d7e66fd58722";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    processJSON(response);
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage(), error);
            }
        });
        // Add the request to the RequestQueue.queue.add(stringRequest);
        queue.add(stringRequest);
    }

    /**
     * Method that processes the JSON from the API
     * so it can be set to the textviews
     * @param response
     */
    public void processJSON(String response){
        JSONObject top = null;
        try {
            top = new JSONObject(response);
            JSONObject main = top.getJSONObject("main");
            double apiTemp = main.getDouble("temp") - convertToCelsius;
            apiTemp = Math.round(apiTemp * 100.0) / 100.0;
            apiPressure = main.getInt("pressure");
            apiHumidity = String.valueOf(main.getInt("humidity"));
            Log.d("JSON",String.valueOf(apiTemp));
            tvTemp.setText("Temperature  " + apiTemp + " \u00B0");
            tvHumidity.setText("Humidity: " + apiHumidity + " %");
            tvPressure.setText("Pressure " + apiPressure + " mbar");

            //Calculates the altitude
            float p1 = (float)apiPressure;
            float altitude = SensorManager.getAltitude(p1,pressureFromSensor);
            tvAltitude.setText("Current altitude: " + Math.abs(altitude));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
