package com.example.warehouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.MainActivity;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.adapter.AddressDetailsAdapter;
import com.example.warehouse.url.AppURL;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressDetails extends AppCompatActivity {

    GoogleMap googleMap;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private double latitude;
    private double longitude;

    String latitude1, longitude1;

    EditText edit_House,edit_street,edit_locality,edit_city,edit_state,edit_country,edit_zip,edit_LatLong;
    String str_House,str_street,str_locality,str_city,str_state,str_country,str_zip,str_LatLong,token;
    Button btn_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        edit_House = findViewById(R.id.edit_House);
        edit_street = findViewById(R.id.edit_street);
        edit_locality = findViewById(R.id.edit_locality);
        edit_city = findViewById(R.id.edit_city);
        edit_state = findViewById(R.id.edit_state);
        edit_country = findViewById(R.id.edit_country);
        edit_zip = findViewById(R.id.edit_zip);
        edit_LatLong = findViewById(R.id.edit_LatLong);
        btn_Submit = findViewById(R.id.btn_Submit);

        token = SharedPrefManager.getInstance(AddressDetails.this).getUser().getToken();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps

            showSettingsAlert();

            Toast.makeText(AddressDetails.this, "Enable Your Location", Toast.LENGTH_SHORT).show();

        }else{

            getLocation();
        }

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edit_House.getText())){

                    edit_House.setError("Please fill HouseNo");

                }else if(TextUtils.isEmpty(edit_street.getText())){

                    edit_street.setError("Please fill street");

                }else if(TextUtils.isEmpty(edit_locality.getText())){

                    edit_locality.setError("Please fill locality");

                }else if(TextUtils.isEmpty(edit_city.getText())){

                    edit_city.setError("Please fill city");

                }else if(TextUtils.isEmpty(edit_state.getText())){

                    edit_state.setError("Please fill state");

                }else if(TextUtils.isEmpty(edit_country.getText())){

                    edit_country.setError("Please fill country");

                }else if(TextUtils.isEmpty(edit_zip.getText())){

                    edit_zip.setError("Please fill zip");

                }else if(edit_zip.getText().toString().trim().length()!=6){

                    edit_zip.setError("only 6 number Allow");

                }else if(TextUtils.isEmpty(edit_LatLong.getText())){

                    edit_LatLong.setError("Please fill LatLong");

                }else{

                    str_House = edit_House.getText().toString().trim();
                    str_street = edit_street.getText().toString().trim();
                    str_locality = edit_locality.getText().toString().trim();
                    str_city = edit_city.getText().toString().trim();
                    str_country = edit_country.getText().toString().trim();
                    str_state = edit_state.getText().toString().trim();
                    str_zip = edit_zip.getText().toString().trim();
                    str_LatLong = edit_LatLong.getText().toString().trim();

                    int zipcode = Integer.valueOf(str_zip);

                    addaddressDetails(str_House,str_street,str_locality,str_city,str_state,str_country,
                            zipcode,longitude1,latitude1,token);

                    Toast.makeText(AddressDetails.this, "Success", Toast.LENGTH_SHORT).show();

                }

            }
        });
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

                edit_LatLong.setText(latitude1+","+longitude1);


            } else if (LocationNetwork != null) {

                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

                edit_LatLong.setText(latitude1+","+longitude1);

            } else if (LocationPassive != null) {

                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

                edit_LatLong.setText(latitude1+","+longitude1);


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

    public void addaddressDetails(String houseNo,String street,String locality,String city,String state,
                                  String country,int zip,String longitude,String latitude,String token){

        ProgressDialog progressDialog = new ProgressDialog(AddressDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Add Address Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,Object> params = new HashMap<>();

        params.put("house",houseNo);
        params.put("street",street);
        params.put("locality",locality);
        params.put("city",city);
        params.put("state",state);
        params.put("country",country);
        params.put("zip",zip);
        params.put("longitude",longitude);
        params.put("latitude",latitude);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppURL.addAddressDetails, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    String message = response.getString("msg");
                    Toast.makeText(AddressDetails.this, message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddressDetails.this, MainActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (AddressDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();
                header.put("auth-token",token);
                return header;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AddressDetails.this);
        requestQueue.add(jsonObjectRequest);


    }


}