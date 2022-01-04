package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.UserSignUpPage;
import com.example.warehouse.activity.ViewCategoryProduct;
import com.example.warehouse.adapter.GrocerySpinerAdapter;
import com.example.warehouse.adapter.ProductDetailsSpinerAdapter;
import com.example.warehouse.adapter.ShowCategoryProductAdapter;
import com.example.warehouse.adapter.ViewImageadapter;
import com.example.warehouse.modelclass.CateGoryImage_ModelClass;
import com.example.warehouse.modelclass.CategoryProduct_ModelClass;
import com.example.warehouse.modelclass.CategoryWeight_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsWeight_ModelClass;
import com.example.warehouse.modelclass.ProductDetails_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends Fragment {

    TextView t1, t2, t3, text_productName, text_totalReating, text_ProductDescription,text_ProductPrice;
    LinearLayout inc;
    Button btn_AddCart;
    ImageView image_Notification, image_Cart,favouriteProduct;
    TextView text_name;
    ViewPager2 recyclerShowImage;
    ViewImageadapter viewImageadapter;
    TextView[] dots;
    LinearLayout dots_container;
    Spinner text_weigh;
    String price,product_id,token;
    double productPrice = 0;


    ArrayList<ProductDetails_ModelClass> show_ProductDetails;
    ArrayList<ProductDetailsImage_ModelClass> productDetailsImage;
    ArrayList<ProductDetailsWeight_ModelClass> productDetailsWeight;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_activity, container, false);

        btn_AddCart = view.findViewById(R.id.addCart);
        inc = view.findViewById(R.id.inc);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);
        recyclerShowImage = view.findViewById(R.id.recyclerShowImage);
        dots_container = view.findViewById(R.id.dots_container);
        text_weigh = view.findViewById(R.id.text_weigh);
        text_ProductDescription = view.findViewById(R.id.text_ProductDescription);
        text_totalReating = view.findViewById(R.id.text_totalReating);
        text_productName = view.findViewById(R.id.text_productName);
        text_ProductPrice = view.findViewById(R.id.text_ProductPrice);
        favouriteProduct = view.findViewById(R.id.favouriteProduct);

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();

        String productid = getTag();
        showProduct_Details(productid);

        Toast.makeText(getContext(), productid, Toast.LENGTH_SHORT).show();

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(false);

                String quantity = t2.getText().toString().trim();
                String tprice = text_ProductPrice.getText().toString().trim();

                double d_tprice = Double.valueOf(tprice);
                double d_price = Double.valueOf(price);

                if(quantity.equals("1")){

                    text_ProductPrice.setText(price);

                }else{

                    productPrice = d_price - d_tprice;

                    String total_price = String.valueOf(productPrice);

                    text_ProductPrice.setText(total_price);
                }


            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(true);

                String quantity = t2.getText().toString().trim();

                    double d_price = Double.valueOf(price);
                    double d_quantity = Double.valueOf(quantity);
                    productPrice = productPrice + d_price;

                    String total_price = String.valueOf(productPrice);

                    text_ProductPrice.setText(total_price);

            }
        });

        btn_AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //text_name.setText("Serach");

                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft1.replace(R.id.frame, cart);
                ft1.commit();
            }
        });

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);

        text_name.setText("Product");

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

        recyclerShowImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                selectedIndicatorPosition(position);
                super.onPageSelected(position);
            }
        });

        favouriteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favouriteProduct.setImageResource(R.drawable.heart);

                addfavouriteProduct(product_id,token);

            }
        });

        return view;
    }

    private void inc(Boolean x) {
        int y = Integer.parseInt(t2.getText().toString());
        if (x) {
            y++;
            t2.setText(String.valueOf(y));
        } else {
            y--;
            if (y <= 0) {
                t2.setText("1");
            } else {
                t2.setText(String.valueOf(y));
            }
        }

        Toast.makeText(getActivity(), t2.getText(), Toast.LENGTH_SHORT).show();

    }

    public void showProduct_Details(String productid) {

        String url = AppURL.getProductById + productid;

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if (message.equals("false")) {

                        show_ProductDetails = new ArrayList<>();

                        String productDetails = jsonObject.getString("productDetails");

                        JSONObject jsonObject_product = new JSONObject(productDetails);

                        String discount = jsonObject_product.getString("discount");
                        product_id = jsonObject_product.getString("_id");
                        String title = jsonObject_product.getString("title");
                        price = jsonObject_product.getString("price");
                        String type = jsonObject_product.getString("type");
                        String description = jsonObject_product.getString("description");
                        String categoryId = jsonObject_product.getString("categoryId");
                        String totalRating = jsonObject_product.getString("totalRating");

                        text_productName.setText(title);
                        text_totalReating.setText(totalRating);
                        text_ProductDescription.setText(description);
                        text_ProductPrice.setText(price);


                        int total_Reating = Integer.valueOf(totalRating);

                        productDetailsImage = new ArrayList<>();

                        String images = jsonObject_product.getString("images");

                        JSONArray jsonArray_images = new JSONArray(images);

                        for (int j = 0; j < jsonArray_images.length(); j++) {

                            String image = jsonArray_images.get(j).toString();


                            ProductDetailsImage_ModelClass productDetailsImage_modelClass = new ProductDetailsImage_ModelClass(
                                    image
                            );

                            productDetailsImage.add(productDetailsImage_modelClass);

                        }

                        productDetailsWeight = new ArrayList<>();

                        String weight = jsonObject_product.getString("weight");

                        JSONArray jsonArray_weight = new JSONArray(weight);


                        for (int k = 0; k < jsonArray_weight.length(); k++) {

                            String str_weight = jsonArray_weight.get(k).toString();


                            ProductDetailsWeight_ModelClass productDetailsWeight_modelClass = new ProductDetailsWeight_ModelClass(
                                    str_weight
                            );
                            productDetailsWeight.add(productDetailsWeight_modelClass);

                        }

                        ProductDetails_ModelClass productDetails_modelClass = new ProductDetails_ModelClass(
                                discount, product_id, title, price, type, description, total_Reating, categoryId, productDetailsImage, productDetailsWeight
                        );

                        show_ProductDetails.add(productDetails_modelClass);

                    }

                    viewImageadapter = new ViewImageadapter(getContext(), productDetailsImage);
                    recyclerShowImage.setAdapter(viewImageadapter);

                    Log.d("productDetailsImage",productDetailsImage.toString());
                    int arraysize = productDetailsImage.size();

                    dots = new TextView[arraysize];

                    dotsIndicator();

                    ProductDetailsSpinerAdapter adapter = new ProductDetailsSpinerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item
                            , productDetailsWeight);
                    text_weigh.setAdapter(adapter);


                        /*gridLayoutManager = new GridLayoutManager(ViewCategoryProduct.this,2, GridLayoutManager.VERTICAL, false);
                        showCategoryProductAdapter = new ShowCategoryProductAdapter(ViewCategoryProduct.this, category);
                        categoryProductRecycler.setLayoutManager(gridLayoutManager);
                        categoryProductRecycler.setHasFixedSize(true);
                        categoryProductRecycler.setAdapter(showCategoryProductAdapter);*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void selectedIndicatorPosition(int position) {


        for (int i = 0; i < dots.length; i++) {


            if (i == position) {

                dots[i].setTextColor(Color.BLUE);

            } else {

                dots[i].setTextColor(Color.BLACK);
            }
        }

    }

    private void dotsIndicator() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#9679;"));
            dots[i].setTextSize(20);
            dots[i].setPadding(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots_container.addView(dots[i]);
        }
    }

    public void addfavouriteProduct(String productId,String token){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Add Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


      JSONObject jsonObject = new JSONObject();

      try{
          jsonObject.put("productId",productId);

      }catch(Exception e){

          e.printStackTrace();
      }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppURL.addfavourite, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {
                    String message = response.getString("err");
                    String msg = response.getString("msg");

                    if(message.equals("false")){

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }

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

      jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      RequestQueue requestQueue = Volley.newRequestQueue(getContext());
      requestQueue.add(jsonObjectRequest);

    }
}


