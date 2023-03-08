package com.example.sensorreader;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorAmbient;
    private Sensor mSensorPress;
    private Sensor mSensorHumid;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorAmbient;
    private TextView mTextSensorPress;
    private TextView mTextSensorHumid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initialize sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

//        get sensor list
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();

//        list looping
        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName())
                    .append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

//        initialize textview
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorAmbient = findViewById(R.id.label_ambient);
        mTextSensorPress = findViewById(R.id.label_pressure);
        mTextSensorHumid = findViewById(R.id.label_rel_humidity);

//        getting sensor from sensor manager
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPress = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorHumid = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

//        if no sensor
        String sensor_error = "No Sensor";
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorAmbient == null) {
            mTextSensorAmbient.setText(sensor_error);
        }
        if (mSensorPress == null) {
            mTextSensorPress.setText(sensor_error);
        }
        if (mSensorHumid == null) {
            mTextSensorHumid.setText(sensor_error);
        }
    }

//    register sensor
    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAmbient != null) {
            mSensorManager.registerListener(this, mSensorAmbient, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPress != null) {
            mSensorManager.registerListener(this, mSensorPress, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumid != null) {
            mSensorManager.registerListener(this, mSensorHumid, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

//    unregister sensor
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

//    event listener method 1: when sensor has detect some change
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        get sensor type
        int sensorType = sensorEvent.sensor.getType();

//        get sensor value
        float currentValue = sensorEvent.values[0];

//        take action
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue));
                    changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbient.setText(String.format("Ambient temperature sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPress.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumid.setText(String.format("Relative humidity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

//    a method to change bg color based on light
    private void changeBackgroundColor(float currentValue){
        ConstraintLayout layout = findViewById(R.id.app_layout);
        if (currentValue >= 20000 && currentValue <= 40000){
            layout.setBackgroundColor(rgb(194,237,255));
        } else if (currentValue >= 0 && currentValue < 20000) {
            layout.setBackgroundColor(rgb(224,194,255));
        }
    }

//    event listener method 2
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}