package com.example.warehouse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.modelclass.Login_ModelClass;
import com.example.warehouse.url.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLoginPage extends AppCompatActivity {

    EditText eidt_mobileNumber,edit_Password;
    Button btn_Login;
    TextView text_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);

        eidt_mobileNumber = findViewById(R.id.eidt_mobileNumber);
        edit_Password = findViewById(R.id.edit_Password);
        btn_Login = findViewById(R.id.btn_Login);
        text_signUp = findViewById(R.id.text_signUp);

        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserLoginPage.this,UserSignUpPage.class);
                startActivity(intent);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = eidt_mobileNumber.getText().toString().trim();
                String password = edit_Password.getText().toString().trim();

                userLogin(mobile,password);

            }
        });

    }

    public void userLogin(String mobileNo,String password){

        ProgressDialog progressDialog = new ProgressDialog(UserLoginPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Login Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        Map<String,String> params = new HashMap<>();

        params.put("mobile",mobileNo);
        params.put("password",password);

        JSONObject jsonObject1 = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppURL.userLogin, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String message = response.getString("msg");

                    if(message.equals("Successfully logged in!")){

                        Toast.makeText(UserLoginPage.this, message, Toast.LENGTH_SHORT).show();

                        String mobile = response.getString("mobile");
                        String email = response.getString("emailID");
                        String token = response.getString("token");
                        String password = edit_Password.getText().toString().trim();

                        Login_ModelClass login_modelClass = new Login_ModelClass(mobile,email,token,password);

                        SharedPrefManager.getInstance(UserLoginPage.this).userLogin(login_modelClass);

                        Intent intent = new Intent(UserLoginPage.this,ViewUserDetails.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Toast.makeText (UserLoginPage.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserLoginPage.this);
        requestQueue.add(jsonObjectRequest);
    }
}