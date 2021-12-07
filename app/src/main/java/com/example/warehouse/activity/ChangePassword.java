package com.example.warehouse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.url.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText edit_RetypePassword,edit_NewPassword;
    TextView text_Back;
    Button btn_Submit;
    String str_RetypePassword,str_NewPassword,str_MobileNo,str_Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        text_Back = findViewById(R.id.text_Back);
        edit_RetypePassword = findViewById(R.id.edit_RetypePassword);
        edit_NewPassword = findViewById(R.id.edit_NewPassword);
        btn_Submit = findViewById(R.id.btn_Submit);

        str_MobileNo = SharedPrefManager.getInstance(ChangePassword.this).getUser().getMobileNo();
        str_Token = SharedPrefManager.getInstance(ChangePassword.this).getUser().getToken();

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_NewPassword.getText().equals("") && edit_RetypePassword.getText().equals("")){

                    Toast.makeText(ChangePassword.this, "Please Fill The Details", Toast.LENGTH_SHORT).show();

                }else{

                    str_NewPassword = edit_NewPassword.getText().toString().trim();
                    str_RetypePassword = edit_RetypePassword.getText().toString().trim();

                    userChangePassword(str_NewPassword,str_RetypePassword,str_MobileNo);

                }

            }
        });

    }

    public void userChangePassword(String newPassword,String retypePassword,String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Password Update Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,String> params = new HashMap<>();

        params.put("password1",newPassword);
        params.put("password2",retypePassword);
        params.put("mobile",mobileNo);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, AppURL.changePassword, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String message = response.getString("msg");

                    if(message.equals("Successfully updated password.")){

                        Toast.makeText(ChangePassword.this, "Successfully updated password.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText (ChangePassword.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();

                header.put("auth-token",str_Token);
                return header;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(jsonObjectRequest);


    }

}