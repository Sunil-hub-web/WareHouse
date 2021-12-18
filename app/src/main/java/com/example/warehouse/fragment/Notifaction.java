package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.ViewUserDetails;
import com.example.warehouse.adapter.NotificationAdapter;
import com.example.warehouse.fragment.CartActivity;
import com.example.warehouse.modelclass.Notification_ModelClass;
import com.example.warehouse.url.AppURL;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notifaction extends Fragment {

    ImageView image_Notification, image_Cart;
    TextView text_name,textdate;
    RecyclerView recyclerNotification;
    NotificationAdapter notificationAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Notification_ModelClass> notifi = new ArrayList<>();
    String token;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull
                                         LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_notification,container,false);

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);
        textdate = view.findViewById(R.id.date);
        recyclerNotification = view.findViewById(R.id.recyclerNotification);

        image_Notification.setVisibility(View.INVISIBLE);
        image_Cart.setVisibility(View.VISIBLE);
        text_name.setText("Notification");

        getNotification(token);

        image_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text_name.setText("Cart");
                image_Cart.setVisibility(View.INVISIBLE);
                image_Notification.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft.replace(R.id.frame, cart);
                ft.commit();

            }
        });

        return view;
    }

    public void getNotification(String token){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Retrive Notification Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getNotofication, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("msg");

                  if(message.equals("Notifications fetched successfully")){

                      Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                      String notification = jsonObject.getString("notification");

                      JSONArray jsonArray_notification = new JSONArray(notification);

                      for (int i=0;i<jsonArray_notification.length();i++){

                          JSONObject jsonObject_Notification = jsonArray_notification.getJSONObject(i);

                          String title = jsonObject_Notification.getString("title");
                          String description = jsonObject_Notification.getString("description");
                          String id = jsonObject_Notification.getString("_id");
                          String userID = jsonObject_Notification.getString("userID");
                          String userType = jsonObject_Notification.getString("userType");
                          String createdAt = jsonObject_Notification.getString("createdAt");

                          Notification_ModelClass notification_modelClass = new Notification_ModelClass(
                                  title,description,userID,id,userType,createdAt
                          );

                          notifi.add(notification_modelClass);
                      }


                      linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                      notificationAdapter = new NotificationAdapter(getContext(),notifi);
                      recyclerNotification.setLayoutManager(linearLayoutManager);
                      recyclerNotification.setHasFixedSize(true);
                      recyclerNotification.setAdapter(notificationAdapter);


                  }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                error.printStackTrace();
                Toast.makeText(getContext(), error+"", Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
