package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.warehouse.adapter.ViewCartItemAdapter;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsWeight_ModelClass;
import com.example.warehouse.modelclass.ProductDetails_ModelClass;
import com.example.warehouse.modelclass.ShowCartItem_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends Fragment {

    ImageView image_Notification, image_Cart;
    public static TextView text_name,text_quantity,product_price,tax_price,shipping_price,text_totalmoney;
    RecyclerView recyclerCartproduct;
    LinearLayoutManager linearLayoutManager;
    ViewCartItemAdapter viewCartItemAdapter;

    ArrayList<ShowCartItem_ModelClass> show_ShowCartItem;
    ArrayList<ProductDetailsImage_ModelClass> productDetailsImage;
    ArrayList<ProductDetailsWeight_ModelClass> productDetailsWeight;

    String token;
    double totalAmount = 0,d_quantity = 0;
    Button btn_checkOut;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart_activity,container,false);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);
        text_quantity = view.findViewById(R.id.text_quantity);
        shipping_price = view.findViewById(R.id.shipping_price);
        text_totalmoney = view.findViewById(R.id.text_totalmoney);
        recyclerCartproduct = view.findViewById(R.id.recyclerCartproduct);
        product_price = view.findViewById(R.id.product_price);
        tax_price = view.findViewById(R.id.tax_price);
        btn_checkOut = view.findViewById(R.id.btn_checkOut);

        image_Notification.setVisibility(View.VISIBLE);
        image_Cart.setVisibility(View.INVISIBLE);
        text_name.setText("Cart");

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();

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

        showCartItem(token);

        return view;
    }

    public void showCartItem(String token){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getCartItem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("err");

                    show_ShowCartItem = new ArrayList<>();

                    if(message.equals("false")){

                        String cartItems = jsonObject.getString("cartItems");

                        JSONObject jsonObject_cartItems = new JSONObject(cartItems);

                        String active = jsonObject_cartItems.getString("active");
                        String cartid = jsonObject_cartItems.getString("_id");
                        String userId = jsonObject_cartItems.getString("userId");

                        String cart = jsonObject_cartItems.getString("cart");

                        JSONArray jsonArray_cart = new JSONArray(cart);

                        for(int i=0;i<jsonArray_cart.length();i++){

                            JSONObject jsonObject_cart = jsonArray_cart.getJSONObject(i);

                            String quantity = jsonObject_cart.getString("quantity");
                            String quantity_id = jsonObject_cart.getString("_id");

                            String item = jsonObject_cart.getString("item");

                            JSONObject jsonObject_item = new JSONObject(item);

                            String discount = jsonObject_item.getString("discount");
                            String id = jsonObject_item.getString("_id");
                            String title = jsonObject_item.getString("title");
                            String price = jsonObject_item.getString("price");
                            String type = jsonObject_item.getString("type");
                            String description = jsonObject_item.getString("description");
                            String categoryId = jsonObject_item.getString("categoryId");
                            String totalRating = jsonObject_item.getString("totalRating");

                            int total_Reating = Integer.valueOf(totalRating);

                            productDetailsImage = new ArrayList<>();

                            String images = jsonObject_item.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(0).toString();


                                ProductDetailsImage_ModelClass productDetailsImage_modelClass = new ProductDetailsImage_ModelClass(
                                        image
                                );

                                productDetailsImage.add(productDetailsImage_modelClass);

                            }

                            productDetailsWeight = new ArrayList<>();

                            String weight = jsonObject_item.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(k).toString();


                                ProductDetailsWeight_ModelClass productDetailsWeight_modelClass = new ProductDetailsWeight_ModelClass(
                                        str_weight
                                );
                                productDetailsWeight.add(productDetailsWeight_modelClass);

                            }


                            double sales_Price = Double.valueOf(price);

                            double quanTity = Double.valueOf(quantity);

                            double totalprice = sales_Price * quanTity;

                            d_quantity = d_quantity + quanTity;


                            totalAmount = totalAmount + totalprice;

                            ShowCartItem_ModelClass showCartItem_modelClass = new ShowCartItem_ModelClass(
                                   discount,id,title,price,type,description,quantity,productDetailsImage,productDetailsWeight
                            );
                            show_ShowCartItem.add(showCartItem_modelClass);

                        }

                    }

                    String total_price = String.valueOf(totalAmount);
                    String str_quantity = String.valueOf(d_quantity);

                    text_quantity.setText(str_quantity);

                    product_price.setText(total_price);

                    String strtax_price = tax_price.getText().toString().trim();
                    String str_shipping = shipping_price.getText().toString().trim();

                    double d_texprice = Double.valueOf(strtax_price);
                    double d_shippingprice = Double.valueOf(str_shipping);

                    double total_amount = totalAmount + d_texprice + d_shippingprice;
                    String str_totalamount = String.valueOf(total_amount);

                    text_totalmoney.setText(str_totalamount);

                    linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                    viewCartItemAdapter = new ViewCartItemAdapter(getContext(),show_ShowCartItem);
                    recyclerCartproduct.setLayoutManager(linearLayoutManager);
                    recyclerCartproduct.setHasFixedSize(true);
                    recyclerCartproduct.setAdapter(viewCartItemAdapter);

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
