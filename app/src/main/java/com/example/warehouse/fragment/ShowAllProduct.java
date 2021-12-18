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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.warehouse.activity.AddressDetails;
import com.example.warehouse.adapter.ShowAllProductAdapter;
import com.example.warehouse.modelclass.ProductImage_ModelClass;
import com.example.warehouse.modelclass.ProductWeight_ModelClass;
import com.example.warehouse.modelclass.ShowProductDetails_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ShowAllProduct extends Fragment {

    RecyclerView recyclerShowProduct;
    ShowAllProductAdapter showAllProductAdapter;
    ArrayList<ShowProductDetails_ModelClass> productdetails;
    ArrayList<ProductImage_ModelClass> productimage;
    ArrayList<ProductWeight_ModelClass> productweight;
    GridLayoutManager gridLayoutManager;

    ImageView image_Notification, image_Cart;
    TextView text_name;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.showallproduct,container,false);

        recyclerShowProduct = view.findViewById(R.id.recyclerShowProduct);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);

        image_Notification.setVisibility(View.VISIBLE);
        image_Cart.setVisibility(View.VISIBLE);
        text_name.setText("Product Details");

        showAllProduct();

        return view;
    }

    public void showAllProduct(){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.showAllProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if(message.equals("false")){

                        productdetails = new ArrayList<>();

                        String product = jsonObject.getString("products");

                        JSONArray jsonArray_product = new JSONArray(product);

                        for (int i = 0;i<jsonArray_product.length();i++){

                            JSONObject jsonObject_product = jsonArray_product.getJSONObject(i);

                            String discount = jsonObject_product.getString("discount");
                            String title = jsonObject_product.getString("title");
                            String price = jsonObject_product.getString("price");
                            String type = jsonObject_product.getString("type");

                            productimage = new ArrayList<>();

                            String images = jsonObject_product.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j=0;j<jsonArray_images.length();j++){

                                String image = jsonArray_images.get(0).toString();


                               ProductImage_ModelClass productImage_modelClass = new ProductImage_ModelClass(
                                       image
                               );

                               productimage.add(productImage_modelClass);
                                Log.d("productimage",productimage.toString());

                            }

                            productweight = new ArrayList<>();

                            String weight = jsonObject_product.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k=0;k<jsonArray_weight.length();k++){

                                String str_weight = jsonArray_weight.get(0).toString();


                                ProductWeight_ModelClass productWeight_modelClass = new ProductWeight_ModelClass(
                                        str_weight
                                );
                                productweight.add(productWeight_modelClass);
                                Log.d("productweight",productweight.toString());

                            }

                            ShowProductDetails_ModelClass showProductDetails_modelClass = new ShowProductDetails_ModelClass(

                                    discount,title,price,productimage,productweight

                            );

                            productdetails.add(showProductDetails_modelClass);

                            Log.d("productdetails",productdetails.toString());

                        }
                        gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                        showAllProductAdapter = new ShowAllProductAdapter(getContext(),productdetails);
                        recyclerShowProduct.setLayoutManager(gridLayoutManager);
                        recyclerShowProduct.setHasFixedSize(true);
                        recyclerShowProduct.setAdapter(showAllProductAdapter);
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
                Toast.makeText (getContext(), ""+error, Toast.LENGTH_SHORT).show ( );

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
