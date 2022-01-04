package com.example.warehouse.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.warehouse.activity.UserLoginPage;
import com.example.warehouse.adapter.AddressDetailsAdapter;
import com.example.warehouse.adapter.CartItemAdapter;
import com.example.warehouse.adapter.ViewCartItemAdapter;
import com.example.warehouse.modelclass.AddressDetails_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsWeight_ModelClass;
import com.example.warehouse.modelclass.ShowCartItem_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPage extends Fragment {

    TextView text_subTotalPrice,text_shippingCharges,text_TotalPrice,text_ShowAddress;
    Button btn_AddnewAddress,btn_selectAddress,btn_ProceedCheckout;
    RecyclerView recyclerAddressDetails,orderSummaryRecycler;
    String House, street, locality, city, state, country, zip, Latitude, Longitude, Default, id,
            userid,token,str_ShowAddress,addreessid,productprice,shippingprice,texttotalmoney,cartid;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    ArrayList<AddressDetails_ModelClass> viewAddressDetails = new ArrayList<>();
    AddressDetailsAdapter addressDetailsAdapter;
    CartItemAdapter cartItemAdapter;
    ArrayList<ShowCartItem_ModelClass> show_ShowCartItem;
    ArrayList<ProductDetailsImage_ModelClass> productDetailsImage;
    ArrayList<ProductDetailsWeight_ModelClass> productDetailsWeight;
    double totalAmount = 0,d_quantity = 0;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.checkoutpage,container,false);

        text_TotalPrice = view.findViewById(R.id.text_TotalPrice);
        text_subTotalPrice = view.findViewById(R.id.text_subTotalPrice);
        text_shippingCharges = view.findViewById(R.id.text_shippingCharges);
        btn_AddnewAddress = view.findViewById(R.id.btn_AddnewAddress);
        btn_selectAddress = view.findViewById(R.id.btn_selectAddress);
        btn_ProceedCheckout = view.findViewById(R.id.btn_ProceedCheckout);
        text_ShowAddress = view.findViewById(R.id.text_ShowAddress);
        orderSummaryRecycler = view.findViewById(R.id.orderSummaryRecycler);

        //Retrieve the value
        productprice = getArguments().getString("productprice");
        shippingprice = getArguments().getString("shippingprice");
        texttotalmoney = getArguments().getString("texttotalmoney");
        cartid = getArguments().getString("cartid");

        text_shippingCharges.setText(shippingprice);
        text_subTotalPrice.setText(productprice);
        text_TotalPrice.setText(texttotalmoney);

        token = SharedPrefManager.getInstance(getContext()).getUser().getToken();

        btn_selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectaddress();
            }
        });

        showCartItem(token);


        return view;
    }

    public void selectaddress(){

        Dialog dialogSelect = new Dialog(getContext());
        dialogSelect.setContentView(R.layout.selectaddress);
        dialogSelect.setCancelable(false);

        Button btn_SelectAddress = dialogSelect.findViewById(R.id.btn_SelectAddress);

        recyclerAddressDetails = dialogSelect.findViewById(R.id.recyclerAddressDetails);

        btn_SelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(str_ShowAddress.equals("")){

                    Toast.makeText(getContext(), "Select Your Address", Toast.LENGTH_SHORT).show();

                }else{

                    str_ShowAddress = addressDetailsAdapter.addressvalue();
                    addreessid = AddressDetailsAdapter.addressId;
                    text_ShowAddress.setText(str_ShowAddress);

                    dialogSelect.dismiss();
                }

            }
        });

        viewAddressDetails(token);


        dialogSelect.show();
        Window window = dialogSelect.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void viewAddressDetails(String token) {

        viewAddressDetails.clear();

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
                        recyclerAddressDetails.setLayoutManager(linearLayoutManager);
                        recyclerAddressDetails.setHasFixedSize(true);
                        recyclerAddressDetails.setAdapter(addressDetailsAdapter);

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
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


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
                        cartid = jsonObject_cartItems.getString("_id");
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


                           /* double sales_Price = Double.valueOf(price);

                            double quanTity = Double.valueOf(quantity);

                            double totalprice = sales_Price * quanTity;

                            d_quantity = d_quantity + quanTity;


                            totalAmount = totalAmount + totalprice;*/

                            ShowCartItem_ModelClass showCartItem_modelClass = new ShowCartItem_ModelClass(
                                    discount,id,title,price,type,description,quantity,productDetailsImage,productDetailsWeight
                            );
                            show_ShowCartItem.add(showCartItem_modelClass);

                        }

                    }

                   /* String total_price = String.valueOf(totalAmount);
                    String str_quantity = String.valueOf(d_quantity);

                    text_quantity.setText(str_quantity);

                    product_price.setText(total_price);

                    String strtax_price = tax_price.getText().toString().trim();
                    String str_shipping = shipping_price.getText().toString().trim();

                    double d_texprice = Double.valueOf(strtax_price);
                    double d_shippingprice = Double.valueOf(str_shipping);

                    double total_amount = totalAmount + d_texprice + d_shippingprice;
                    String str_totalamount = String.valueOf(total_amount);

                    text_totalmoney.setText(str_totalamount);*/

                    linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                    cartItemAdapter = new CartItemAdapter(getContext(),show_ShowCartItem);
                    orderSummaryRecycler.setLayoutManager(linearLayoutManager1);
                    orderSummaryRecycler.setHasFixedSize(true);
                    orderSummaryRecycler.setAdapter(cartItemAdapter);

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
