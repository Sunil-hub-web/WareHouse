package com.example.warehouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warehouse.R;
import com.google.android.gms.maps.GoogleMap;

public class AddressDetails extends AppCompatActivity {

    GoogleMap googleMap;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private double latitude;
    private double longitude;

    String latitude1, longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps

            showSettingsAlert();

            Toast.makeText(AddressDetails.this, "Enable Your Location", Toast.LENGTH_SHORT).show();

        }else{

            getLocation();
        }
    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(AddressDetails.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddressDetails.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {

                latitude = LocationGps.getLatitude();
                longitude = LocationGps.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);


            } else if (LocationNetwork != null) {

                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

            } else if (LocationPassive != null) {

                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);


            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // All Permission For Access Location

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddressDetails.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }



}