package com.assassin.assassinandroidgame.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assassin.assassinandroidgame.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by Daniel on 10/3/2015.
 *
 * v1.0 - this class should fetch the user's position from GPS or network provider and display the coordinates in a textview
 *
 */
public class DirectionToVictim extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, SensorEventListener, com.google.android.gms.location.LocationListener {

    TextView txt;
    Button killBtn;
    ImageView comp;
    Location myLoc;
    Location victimLoc;
    double randomLat;
    double randomLong;
    SensorManager sm;
    Sensor mag;
    Sensor acc;
    float[] mMag = new float[3];
    float[] mAcc = new float[3];
    float[] mRotMat = new float[9];
    float[] orientation = new float[3];
    int maxOperations = 30;
    int numOperations;
    float currentCompass;
    float sumAngles;
    Random randGen;
    GoogleApiClient gac;
    LocationRequest locReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.victim_direction);

        comp = (ImageView)findViewById(R.id.compass);
        killBtn = (Button)findViewById(R.id.killButton);
        killBtn.setEnabled(false);
        txt = (TextView)findViewById(R.id.textView);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        mag = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sumAngles = 0;
        numOperations = 0;
        currentCompass = 0;

        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locReq = new LocationRequest();
        locReq.setInterval(0);
        locReq.setFastestInterval(0);
        locReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void updateRelativePosition(Location myLoc, Location victimLoc, float currentCompass)
    {
        double pi = Math.PI;
        double dLon = myLoc.getLongitude() - victimLoc.getLongitude();
        double dLat = myLoc.getLatitude() - victimLoc.getLatitude();
        double angle = Math.atan(Math.abs(dLon)/Math.abs(dLat));
        if(dLon >= 0 && dLat < 0) {/*nothing*/}
        else if(dLon >= 0 && dLat >= 0) {angle=pi-angle;}
        else if(dLon < 0 && dLat >= 0) {angle=pi+angle;}
        else if(dLon < 0 && dLat < 0) {angle=2*pi-angle;}
        angle += currentCompass;
        while(angle>=15*pi/8) angle -= 2*pi;
        if(angle < -pi/8) angle += 2*pi;
        if(angle >= -pi/8 && angle < pi/8) {
            comp.setImageResource(R.drawable.compass_north);
        }
        else if(angle >= pi/8 && angle < 3*pi/8){
            comp.setImageResource(R.drawable.compass_northwest);
        }
        else if(angle >= 3*pi/8 && angle < 5*pi/8) {
            comp.setImageResource(R.drawable.compass_west);
        }
        else if(angle >= 5*pi/8 && angle < 7*pi/8) {
            comp.setImageResource(R.drawable.compass_southwest);
        }
        else if(angle >= 7*pi/8 && angle < 9*pi/8) {
            comp.setImageResource(R.drawable.compass_south);
        }
        else if(angle >= 9*pi/8 && angle < 11*pi/8) {
            comp.setImageResource(R.drawable.compass_southeast);
        }
        else if(angle >= 11*pi/8 && angle < 13*pi/8) {
            comp.setImageResource(R.drawable.compass_east);
        }
        else if(angle >= 13*pi/8 && angle < 15*pi/8) {
            comp.setImageResource(R.drawable.compass_northeast);
        }
        double distance = Math.sqrt(dLat*dLat+dLon*dLon)*111000;
        txt.setText(String.valueOf(distance));
        if(distance <= 15 && !killBtn.isEnabled()) killBtn.setEnabled(true);
        else if(distance > 15 && killBtn.isEnabled()) killBtn.setEnabled(false);
    }

    protected void onStart() {
        super.onStart();
        sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, mag, SensorManager.SENSOR_DELAY_UI);
        gac.connect();
    }

    @Override
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location lastLoc = LocationServices.FusedLocationApi.getLastLocation(gac);
        myLoc = lastLoc;

        /* Comment this code out when attaching to project */
        randGen = new Random();
        randomLat=20*randGen.nextDouble();
        if(randomLat<10) randomLat-=20;
        randomLat/=111000;
        randomLong = 20*randGen.nextDouble();
        if(randomLong<10) randomLong-=20;
        randomLong/=90000;
        victimLoc = myLoc;
        victimLoc.setLatitude(myLoc.getLatitude() + randomLat);
        victimLoc.setLongitude(myLoc.getLongitude() + randomLong);
        /***********************************************/

        updateRelativePosition(myLoc, victimLoc, currentCompass);
        LocationServices.FusedLocationApi.requestLocationUpdates(gac, locReq, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int var)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult res)
    {

    }

    @Override
    public void onLocationChanged(Location location) {
        myLoc = location;
        updateRelativePosition(myLoc, victimLoc, currentCompass);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mAcc = event.values.clone();
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mMag = event.values.clone();
        if(mAcc != null && mMag != null)
        {
            SensorManager.getRotationMatrix(mRotMat, null, mAcc, mMag);
            SensorManager.getOrientation(mRotMat, orientation);
            numOperations++;
            sumAngles += orientation[0];
            if(numOperations == maxOperations) {
                currentCompass = sumAngles / numOperations;
                numOperations = 0;
                sumAngles = 0;
                updateRelativePosition(myLoc, victimLoc, currentCompass);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
