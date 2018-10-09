package com.example.oscar.projekt3;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private Sensor sensorLight, sensorProximity;
    private SensorManager mSensorManager;
    private boolean isSensorPresent, isFlashLightOn;
    private ContentResolver mContentResolver;
    private Window mWindow;
    private float mBrightness;
    private int currentBrightnessLevel;
    private String mCameraID;
    private CameraCharacteristics mParameters;
    private CameraManager mCameraManager;
    private Switch aSwitch;
    private RelativeLayout rl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl = (RelativeLayout)findViewById(R.id.activity_main);
        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new checkListener());
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        //Checks if sensors exsits
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            sensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            sensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }
        initScreenBrightness();
        initCameraFlashLight();
    }

    /**
     * Method that initialize settings so the screen brightness
     * can be changed
     */
    public void initScreenBrightness(){
        mContentResolver = getContentResolver();
        mWindow = getWindow();
    }

    /**
     * Method that initialize settings so the device's camera can be used
     */
    public void initCameraFlashLight(){
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraID = mCameraManager.getCameraIdList()[0];
            mParameters = mCameraManager.getCameraCharacteristics(mCameraID);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which propose is to listen to which radiobutton is checked
     * in the radiogroup. Once a certain radiobutton is checked, currentBrightnessLevel
     * is given a given number which represet the brightness of the screen
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            //HIGHEST LIGHT SETTING
            case R.id.rbUltraUltra:
                if (checked)
                    currentBrightnessLevel = 1;
                break;
            case R.id.rbUltra:
                if (checked)
                    currentBrightnessLevel = 3;
                break;
            case R.id.rbHigh:
                if (checked)
                    currentBrightnessLevel = 30;
                break;
            case R.id.rbStandardLight:
                if (checked)
                    currentBrightnessLevel = 60;
                break;
            //LOWEST LIGHT SETTING
            case R.id.rbLowLight:
                if (checked)
                    currentBrightnessLevel = 200;
                break;
            default:
                currentBrightnessLevel = 0;
                break;

        }
    }

    /**
     * Method that checks the reading from the LIGHT and PROXIMITY Sensors
     * Depending what radiobutton that's checked and what value that are read from the sensors
     * the method behaves differently.
     * Each radiobutton has it's own value. The screenbrigtness toggles between that value and to the max value of the screen
     * depending on how close your hand is to the proximity sensor. The closer you have your hand to the sensor the brighter the screen gets
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == event.sensor.TYPE_LIGHT){
            float light = event.values[0];
            Log.d("Light value", String.valueOf(light));
            if(light>=1 && light<3000 && currentBrightnessLevel !=0 ) {
                if(light>currentBrightnessLevel){
                    light = currentBrightnessLevel;
                }
                Log.d("NEW LIGHT value", String.valueOf(light));
                changeScreenBrightness(1/light);
            }else if( light>=1 && light<100){
                changeScreenBrightness(1/light);
            }
        }

        if(event.sensor.getType() == event.sensor.TYPE_PROXIMITY){
            float distanceFromPhone = event.values[0];
            Log.d("Distance", String.valueOf(distanceFromPhone));
            if(distanceFromPhone < sensorProximity.getMaximumRange() && currentBrightnessLevel == 200) {
                if(!isFlashLightOn) {
                    turnTorchLightOn();
                }
            } else {
                if(isFlashLightOn) {
                    turnTorchLightOff();
                }
            }
        }
    }

    /**
     *  Method that changes the screen brightness or the system brightness
     *  If the switch component is checked the method changes the system brightness otherwise
     *  it changes the screen brightness
     * @param brightness - value from the light sensor or from currentBrightnessLevel variable
     */
    public void changeScreenBrightness(float brightness){
        mBrightness = brightness;
        if(aSwitch.isChecked()){
            if (!Settings.System.canWrite(this)){
                Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                startActivity(i);
            }else {
                Settings.System.putInt(mContentResolver, Settings.System.SCREEN_BRIGHTNESS, (int)(mBrightness*255));
            }
        }else if(!aSwitch.isChecked()){
            WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
            mLayoutParams.screenBrightness = mBrightness;
            mWindow.setAttributes(mLayoutParams);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorPresent) {
            mSensorManager.registerListener(this, sensorLight,90000000);
            mSensorManager.registerListener(this, sensorProximity,90000000);
            Toast.makeText(getApplicationContext(), sensorLight.getName() + " is registered", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
    }

    /**
     * Method that turns on the device's flashlight
     * by checking if the camera IS using it's CameraCharacteristics object
     */
    public void turnTorchLightOn(){
        if (mParameters.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
            try {
                mCameraManager.setTorchMode(mCameraID, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashLightOn = true;
        }
    }

    /**
     * Method that turns off the device flashlight
     * by checking if the camera IS NOT using it's CameraCharacteristics object
     */
    public void turnTorchLightOff(){
        try {
            mCameraManager.setTorchMode(mCameraID, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        isFlashLightOn = false;
    }

    private class checkListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(aSwitch.isChecked())
                Toast.makeText(getApplicationContext(),"Changing system brightness is permanent", Toast.LENGTH_SHORT).show();

        }
    }
}