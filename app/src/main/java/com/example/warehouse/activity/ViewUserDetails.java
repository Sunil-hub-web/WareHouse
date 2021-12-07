package com.example.warehouse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.url.AppURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewUserDetails extends AppCompatActivity {

    EditText edit_UserName,edit_MobileNo,edit_EmailId;
    TextView text_EditUserdata;
    String token,user_password,str_UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        edit_EmailId = findViewById(R.id.edit_EmailId);
        edit_UserName = findViewById(R.id.edit_UserName);
        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        text_EditUserdata = findViewById(R.id.text_EditUserdata);

        edit_EmailId.setFocusable(false);
        edit_EmailId.setFocusableInTouchMode(false);
        edit_UserName.setFocusable(true);
        edit_UserName.setFocusableInTouchMode(true);
        edit_MobileNo.setFocusable(false);
        edit_MobileNo.setFocusableInTouchMode(false);

         token = SharedPrefManager.getInstance(ViewUserDetails.this).getUser().getToken();
         user_password = SharedPrefManager.getInstance(ViewUserDetails.this).getUser().getPassword();

        viewUserDetails(token);

        text_EditUserdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* edit_EmailId.setFocusable(true);
                edit_EmailId.setFocusableInTouchMode(true);
                edit_UserName.setFocusable(true);
                edit_UserName.setFocusableInTouchMode(true);
                edit_MobileNo.setFocusable(true);
                edit_MobileNo.setFocusableInTouchMode(true);*/

                str_UserName = edit_UserName.getText().toString().trim();

                updateUserDetails(str_UserName,user_password);

                Toast.makeText(ViewUserDetails.this, "Edit User Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewUserDetails(String token){

        ProgressDialog progressDialog = new ProgressDialog(ViewUserDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Retrive User Details Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.viewUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String userData = jsonObject.getString("userData");
                    JSONObject jsonObject_Data = new JSONObject(userData);

                    String name = jsonObject_Data.getString("name");
                    String email = jsonObject_Data.getString("emailID");
                    String mobile = jsonObject_Data.getString("mobile");

                    edit_UserName.setText(name);
                    edit_MobileNo.setText(mobile);
                    edit_EmailId.setText(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                error.printStackTrace();
                Toast.makeText(ViewUserDetails.this, error+"", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();
                header.put("auth-token",token);
                return header;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewUserDetails.this);
        requestQueue.add(stringRequest);


    }

    public void updateUserDetails(String username,String password){

        ProgressDialog progressDialog = new ProgressDialog(ViewUserDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Retrive User Details Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        Map<String,String> params = new HashMap<>();

        params.put("name",username);
        params.put("password1",password);
        params.put("password2",password);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, AppURL.updateUserDetails, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String message = response.getString("msg");

                    if(message.equals("Successfully updated password.")){

                        Toast.makeText(ViewUserDetails.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText (ViewUserDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> headre = new HashMap<>();
                headre.put("auth-token",token);
                return headre;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewUserDetails.this);
        requestQueue.add(jsonObjectRequest);


    }
}