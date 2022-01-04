package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.UserSignUpPage;
import com.example.warehouse.adapter.FavouriteProductAdapter;
import com.example.warehouse.adapter.VegetablesAdapter;
import com.example.warehouse.modelclass.Image_ModelClass;
import com.example.warehouse.modelclass.Vegetables_Modelclass;
import com.example.warehouse.modelclass.Weight_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavouriteProduct extends Fragment {

    ImageView image_Notification, image_Cart;
    TextView text_name;
    RecyclerView recyclerfavourite;
    LinearLayoutManager linearLayoutManager;

    ArrayList<Vegetables_Modelclass> favouriteProduct;
    ArrayList<Image_ModelClass> favouriteimage;
    ArrayList<Weight_ModelClass> favouriteweight;

    FavouriteProductAdapter favouriteProductAdapter;

    String token;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favouriteproduct,container,false);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);
        recyclerfavourite = view.findViewById(R.id.recyclerfavourite);

        text_name.setText("Favourite");

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();

        viewFevouriteProduct(token);


        image_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText("Notification");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Notifaction notification = new Notifaction();
                ft.replace(R.id.frame, notification);
                ft.commit();

                image_Notification.setVisibility(View.INVISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
            }
        });
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

    public void viewFevouriteProduct(String token){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("View Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.viewfavourite, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if(message.equals("false")){

                        favouriteProduct = new ArrayList<>();

                        String favourite = jsonObject.getString("favourite");


                        JSONArray jsonArray_favourite = new JSONArray(favourite);

                        for (int i = 0; i < jsonArray_favourite.length(); i++) {

                            JSONObject jsonObject_favourite = jsonArray_favourite.getJSONObject(i);

                            String product = jsonObject_favourite.getString("productId");
                            String favourite_id = jsonObject_favourite.getString("_id");
                            String userId = jsonObject_favourite.getString("userId");

                                JSONObject jsonObject_product = new JSONObject(product);

                                String discount = jsonObject_product.getString("discount");
                                String id = jsonObject_product.getString("_id");
                                String title = jsonObject_product.getString("title");
                                String price = jsonObject_product.getString("price");
                                String type = jsonObject_product.getString("type");
                                String description = jsonObject_product.getString("description");
                                String totalRating = jsonObject_product.getString("totalRating");

                                favouriteimage = new ArrayList<>();

                                String images = jsonObject_product.getString("images");

                                JSONArray jsonArray_images = new JSONArray(images);

                                for (int j = 0; j < jsonArray_images.length(); j++) {

                                    String image = jsonArray_images.get(0).toString();


                                    Image_ModelClass image_modelClass = new Image_ModelClass(
                                            image
                                    );

                                    favouriteimage.add(image_modelClass);

                                }

                                favouriteweight = new ArrayList<>();

                                String weight = jsonObject_product.getString("weight");

                                JSONArray jsonArray_weight = new JSONArray(weight);


                                for (int k = 0; k < jsonArray_weight.length(); k++) {

                                    String str_weight = jsonArray_weight.get(0).toString();


                                    Weight_ModelClass weight_modelClass = new Weight_ModelClass(
                                            str_weight
                                    );
                                    favouriteweight.add(weight_modelClass);

                                }

                                Vegetables_Modelclass vegetables_modelclass = new Vegetables_Modelclass(

                                        discount, id, title, price, type, description, favouriteimage, favouriteweight,totalRating,favourite_id

                                );

                                favouriteProduct.add(vegetables_modelclass);
                            }
                    }

                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    favouriteProductAdapter = new FavouriteProductAdapter(getContext(), favouriteProduct);
                    recyclerfavourite.setLayoutManager(linearLayoutManager);
                    recyclerfavourite.setHasFixedSize(true);
                    recyclerfavourite.setAdapter(favouriteProductAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);
//                            if (error.networkResponse.statusCode == 400) {
                            String data = jsonError.getString("msg");
                            Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

//                            } else if (error.networkResponse.statusCode == 404) {
//                                JSONArray data = jsonError.getJSONArray("msg");
//                                JSONObject jsonitemChild = data.getJSONObject(0);
//                                String ms = jsonitemChild.toString();
//                                Toast.makeText(RegisterActivity.this, ms, Toast.LENGTH_SHORT).show();
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("successresponceVolley", "" + e);
                        }
                    }
                }

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
