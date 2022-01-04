package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.warehouse.activity.AddressDetails;
import com.example.warehouse.activity.UserLoginPage;
import com.example.warehouse.adapter.AddressDetailsAdapter;
import com.example.warehouse.modelclass.AddressDetails_ModelClass;
import com.example.warehouse.url.AppURL;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAddressDetails extends Fragment {

    RecyclerView addressRecycler;
    AddressDetailsAdapter addressDetailsAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<AddressDetails_ModelClass> viewAddressDetails = new ArrayList<>();
    String House, street, locality, city, state, country, zip, Latitude, Longitude, Default, id, userid, token;

    ImageView image_Notification, image_Cart;
    TextView text_name;
    Button btn_addaddress;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewaddress, container, false);

        addressRecycler = view.findViewById(R.id.addressRecycler);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);
        btn_addaddress = view.findViewById(R.id.btn_addaddress);

        image_Notification.setVisibility(View.VISIBLE);
        image_Cart.setVisibility(View.INVISIBLE);
        text_name.setText("My Address Details");

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();
        viewAddressDetails(token);

        btn_addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),AddressDetails.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void viewAddressDetails(String token) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("View Address Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getAllressDetailsById, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.d("Ranj_ShowAddres",response.toString());

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if (message.equals("true")) {

                        String address = jsonObject.getString("address");

                        JSONArray jsonArray_address = new JSONArray(address);

                        for (int i = 0; i < jsonArray_address.length(); i++) {

                            JSONObject jsonObject_address = jsonArray_address.getJSONObject(i);

                            id = jsonObject_address.getString("_id");
                            Default = jsonObject_address.getString("default");
                            userid = jsonObject_address.getString("userID");
                            House = jsonObject_address.getString("house");
                            street = jsonObject_address.getString("street");
                            locality = jsonObject_address.getString("locality");
                            city = jsonObject_address.getString("city");
                            state = jsonObject_address.getString("state");
                            country = jsonObject_address.getString("country");
                            zip = jsonObject_address.getString("zip");
                            Latitude = jsonObject_address.getString("longitude");
                            Longitude = jsonObject_address.getString("latitude");
                            String v = jsonObject_address.getString("__v");

                            AddressDetails_ModelClass addressDetails_modelClass = new AddressDetails_ModelClass(
                                    House, street, locality, city, state, country, zip, Latitude, Longitude, Default, id, userid
                            );

                            viewAddressDetails.add(addressDetails_modelClass);
                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        addressDetailsAdapter = new AddressDetailsAdapter(getContext(), viewAddressDetails);
                        addressRecycler.setLayoutManager(linearLayoutManager);
                        addressRecycler.setHasFixedSize(true);
                        addressRecycler.setAdapter(addressDetailsAdapter);

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
                Log.d("Ranj_addresError",error.toString());
                Toast.makeText(getContext(), "Session Expired ,Login Again" , Toast.LENGTH_SHORT).show();

                SharedPrefManager sharedPrefManager = new SharedPrefManager(getActivity());
                sharedPrefManager.logout();
                Intent intentLogin = new Intent(getActivity(), UserLoginPage.class);
                startActivity(intentLogin);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("auth-token", token);
                return header;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
}
