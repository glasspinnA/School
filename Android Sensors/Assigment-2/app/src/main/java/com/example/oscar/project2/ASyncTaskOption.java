package com.example.oscar.project2;

import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ASyncTaskOption extends AppCompatActivity {

    private RetrieveFinished delegate = null;
    private TextView tvTemp, tvPressure, tvHumidity, tvValuesFromSensor, tvAltitude;
    private final double convertToCelsius = 272.15;
    private String apiHumidity;
    private double apiPressure;

    /**
     * OnCreate method that receive the JSON and processes it to the
     * textviews
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        new RetrieveFeedTask().execute();
        tvValuesFromSensor = (TextView)findViewById(R.id.tvValuesFromSensor);
        tvTemp = (TextView)findViewById(R.id.tvTemp);
        tvPressure = (TextView)findViewById(R.id.tvPressure);
        tvHumidity = (TextView)findViewById(R.id.tvHumidity);
        tvAltitude = (TextView)findViewById(R.id.tvAltitude);
        delegate = new RetrieveFinished() {
            @Override
            public void retrieveFinish(String output) throws JSONException {
                JSONObject top = new JSONObject(output);
                JSONObject main = top.getJSONObject("main");

                double temp = main.getDouble("temp") - convertToCelsius;
                temp = Math.round(temp * 100.0) / 100.0;
                apiPressure = main.getInt("pressure");
                String humidity = String.valueOf(main.getInt("humidity"));
                Bundle extras = getIntent().getExtras();

                tvTemp.setText("Temperature  " + temp + " \u00B0");
                tvPressure.setText("Pressure " + apiPressure + " mbar");
                tvHumidity.setText("Humidity " + humidity + " %");
                tvValuesFromSensor.setText("Pressure: " + extras.getString("PRESSURE-SENSOR") + " mbar"+
                        "\n" + extras.getString("TEMP-SENSOR") + "\n" + extras.getString("HUMIDITY-SENSOR"));

                //Calculates the altitude
                float p1 = (float)apiPressure;
                float pressureFromSensor = Float.parseFloat(extras.getString("PRESSURE-SENSOR"));
                Log.d("p1", String.valueOf(p1));
                Log.d("p2", String.valueOf(pressureFromSensor));
                float altitude = SensorManager.getAltitude(p1,pressureFromSensor);
                tvAltitude.setText("Current altitude: " + Math.abs(altitude));


            }
        };
    }

    /***
     * Class that makes to connection to the API
     */
    private class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        private String mKey, mURL;
        protected void onPreExecute(){
        }

        protected void onPostExecute(String response){
            if(response == null)
                response = "THERE WAS AN ERROR";
            try {
                delegate.retrieveFinish(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            mKey = "f0b41cd9c0a8883eca83d7e66fd58722";
            mURL = "http://api.openweathermap.org/data/2.5/weather?id=2692969&appid=f0b41cd9c0a8883eca83d7e66fd58722";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(mURL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.d("ERROR ",e.getMessage(),e);
                e.printStackTrace();
                return null;
            }
        }
    }

}
