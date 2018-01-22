package com.unipi.vnikolis.askisi3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements LocationListener{

    MyTTS myTTS;
    private static final int VOICE_REC_RESULT = 6537;
    LocationManager locationManager;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTTS = new MyTTS(this);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==VOICE_REC_RESULT && resultCode==RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //Με το που πεις location on ανοιγει το gps
            // Toast.makeText(this,matches.toString(),Toast.LENGTH_LONG).show();
            if (matches.contains("location on"))
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 543);
                else
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);


                //myTTS.speak("Your latitude is " + latitude + "and your longitude is" + longitude);

                //και σου λεει σε ποια διευθυνση βρισκεσαι
                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latitude,longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses.size() > 0 )
                {
                    String ad = addresses.get(0).getLocality();
                    myTTS.speak("Your address is"+ ad);
                    Toast.makeText(this,addresses.get(0).toString(),Toast.LENGTH_LONG).show();





                }
                Locale aa [] = Locale.getAvailableLocales();
                for(Locale a : aa){
                    System.out.println(a.toString());
                }


            }

            if (matches.contains("location off"))
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 543);
                else
                    locationManager.removeUpdates(this);
            }

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void go(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el_GR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please say something");
        startActivityForResult(intent,VOICE_REC_RESULT);
    }
}
