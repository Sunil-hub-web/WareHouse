package com.example.warehouse.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.url.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    Button btn_Submit;
    EditText eidt_mobileNumber;
    TextView text_Back;
    String str_CountryCode;
    LinearLayout lin_otpview,lin_forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        lin_forgotpassword = findViewById(R.id.lin_forgotpassword);
        lin_otpview = findViewById(R.id.lin_otpview);
        btn_Submit = findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lin_otpview.setVisibility(View.VISIBLE);
                lin_forgotpassword.setVisibility(View.GONE);

            }
        });

        str_CountryCode = "91";

    }

    public void RequestOtp(String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Otp Send  Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,String> params = new HashMap<>(  );

        params.put("mobile",mobileNo);
        params.put("countryCode",str_CountryCode);

        JSONObject jsonObject = new JSONObject ( params );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, AppURL.forgetPasswordRequestOtp, jsonObject, new Response.Listener<JSONObject> ( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss ();

                try {
                    String msg = response.getString ("msg");

                    Toast.makeText(ForgotPassword.this, msg, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (ForgotPassword.this, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ForgotPassword.this);
        requestQueue.add (jsonObjectRequest);

    }

    public void verifayOtp(String countryCode,String Mobile,String Otp){

        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Verifay Otp  Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,String> params = new HashMap<> (  );

        params.put("countryCode",countryCode);
        params.put("mobile",Mobile);
        params.put("otp",Otp);

        JSONObject jsonObject1 = new JSONObject ( params );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.PUT, AppURL.forgetPasswordVerifayOTP, jsonObject1, new Response.Listener<JSONObject> ( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss ();

                try {

                    String message = response.getString("msg");
                    Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (ForgotPassword.this, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ForgotPassword.this);
        requestQueue.add (jsonObjectRequest);

    }

    public void ResendtOtp(String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Otp Send  Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,String> params = new HashMap<>(  );

        params.put("mobile",mobileNo);
        params.put("countryCode",str_CountryCode);

        JSONObject jsonObject = new JSONObject ( params );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, AppURL.forgetPasswordResendOtp, jsonObject, new Response.Listener<JSONObject> ( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss ();

                try {
                    String msg = response.getString ("msg");

                    Toast.makeText(ForgotPassword.this, msg, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (ForgotPassword.this, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ForgotPassword.this);
        requestQueue.add (jsonObjectRequest);

    }


}