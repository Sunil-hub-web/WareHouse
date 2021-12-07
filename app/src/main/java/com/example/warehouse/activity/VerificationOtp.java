package com.example.warehouse.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.url.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerificationOtp extends AppCompatActivity {

    TextView text_back,text_timer,text_resend;
    Button btn_Submit;
    OtpTextView otp_view;
    String str_UserName,str_MobileNumber,str_EMail,str_Password,str_CountryCode,verify_otp;
    long millisUntilFinished;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_otp);

        btn_Submit = findViewById(R.id.btn_Submit);
        text_back = findViewById(R.id.text_back);
        text_timer = findViewById(R.id.text_timer);
        otp_view = findViewById(R.id.otp_view);
        text_resend = findViewById(R.id.text_resend);
/*
        Intent intent = getIntent();

        str_UserName = intent.getStringExtra("str_UserName");
        str_MobileNumber = intent.getStringExtra("str_MobileNumber");
        str_EMail = intent.getStringExtra("str_EMail");
        str_Password = intent.getStringExtra("str_Password");
        str_CountryCode = intent.getStringExtra("str_CountryCode");*/



        timer();


        text_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer();
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verify_otp = otp_view.getOTP();

                if(otp_view.getOTP().length() < 4){

                    Toast.makeText(VerificationOtp.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                }else{

                    //verifayOtp(str_CountryCode,str_MobileNumber,verify_otp);


                }
            }
        });



    }


    public void verifayOtp(String countryCode,String Mobile,String Otp){

        ProgressDialog progressDialog = new ProgressDialog(VerificationOtp.this);
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.PUT, AppURL.verifayOtp, jsonObject1, new Response.Listener<JSONObject> ( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss ();

                try {

                    String message = response.getString("msg");
                    Toast.makeText(VerificationOtp.this, message, Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (VerificationOtp.this, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (VerificationOtp.this);
        requestQueue.add (jsonObjectRequest);

    }



    public void timer(){

        //Initialize time duration
        //long duration = TimeUnit.MINUTES.toMillis(1);
        //Initialize countdown timer

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                text_timer.setText(millisUntilFinished/1000+"s");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                text_timer.setText("00");
            }

        }.start();
    }
}
