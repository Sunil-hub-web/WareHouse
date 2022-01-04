package com.example.warehouse.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.UserSignUpPage;
import com.example.warehouse.fragment.CartActivity;
import com.example.warehouse.modelclass.GroceryImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsWeight_ModelClass;
import com.example.warehouse.modelclass.ShowCartItem_ModelClass;
import com.example.warehouse.url.AppURL;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewCartItemAdapter extends RecyclerView.Adapter<ViewCartItemAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowCartItem_ModelClass> cartitem;
    ArrayList<ProductDetailsImage_ModelClass> productDetailsImage;
    ArrayList<ProductDetailsWeight_ModelClass> productDetailsWeight;
    String token,str_quantity,productId;
    int quantity;
    double price,d_quantity;

    public ViewCartItemAdapter(Context context, ArrayList<ShowCartItem_ModelClass> show_showCartItem) {

        this.context = context;
        this.cartitem = show_showCartItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewCartItemAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartproduct,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewCartItemAdapter.ViewHolder holder, int position) {

        token = SharedPrefManager.getInstance(context).getUser().getToken();

        ShowCartItem_ModelClass showitem = cartitem.get(position);

        holder.product_Name.setText(showitem.getTitle());
        holder.quantity.setText(showitem.getQuantity());

        String price1 = showitem.getPrice();
        String quantity1 = showitem.getQuantity();

        holder.t2.setText(showitem.getQuantity());

        price = Integer.parseInt(price1);
        quantity = Integer.parseInt(quantity1);


        double d_Price = price * quantity;

        String str_price = String.valueOf(d_Price);


        holder.product_price.setText(str_price);

        ArrayList<ProductDetailsImage_ModelClass> productDetailsImage = showitem.getImage_modelClasses();
        String image = "https://kisaanandfactory.com/static_file/"+productDetailsImage.get(0);
        Log.d("ranj_adapter_image",image);
        Log.d("ranj_adapter_image",productDetailsImage.get(0)+"");
        Picasso.with(context).load(image).into(holder.productImage);


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);
//-
                str_quantity = holder.t2.getText().toString().trim();

                quantity = Integer.valueOf(str_quantity);
                d_quantity = Double.valueOf(quantity);

                productId = showitem.getId();

                String pric = showitem.getPrice();
                double d_price = Integer.parseInt(pric);
                d_quantity = Double.valueOf(str_quantity);

                holder.quantity.setText(str_quantity);

                double d_Price = d_price * d_quantity;

                String str_price = String.valueOf(d_Price);

                holder.product_price.setText(str_price);

                String sum = CartActivity.product_price.getText().toString().trim();
                String qunty = CartActivity.text_quantity.getText().toString().trim();

                double d_sum = Double.valueOf(sum);
                double d_amount = d_sum - d_price;

                double d_qunty = Double.valueOf(qunty);
                double tot_quantity = d_qunty - 1;

                String str_quanty = String.valueOf(tot_quantity);

                String str_amount = String.valueOf(d_amount);
                CartActivity.product_price.setText(str_amount);
                CartActivity.text_quantity.setText(str_quanty);

                String shipping = CartActivity.shipping_price.getText().toString().trim();
                String tax = CartActivity.tax_price.getText().toString().trim();

                double d_shipping = Double.valueOf(shipping);
                double d_tax = Double.valueOf(tax);

                double d_TotalAmount = d_amount + d_shipping + d_tax;

                String str_TotalAmount = String.valueOf(d_TotalAmount);
                CartActivity.text_totalmoney.setText(str_TotalAmount);

                addQuantity(token,quantity,"success",productId);

            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);
                //+

                str_quantity = holder.t2.getText().toString().trim();
                quantity = Integer.valueOf(str_quantity);

                productId = showitem.getId();

                String pric = showitem.getPrice();
                double d_price = Integer.parseInt(pric);
                d_quantity = Double.valueOf(str_quantity);

                holder.quantity.setText(str_quantity);

                double d_Price = d_price * d_quantity;

                String str_price = String.valueOf(d_Price);


                holder.product_price.setText(str_price);

                String sum = CartActivity.product_price.getText().toString().trim();
                String qunty = CartActivity.text_quantity.getText().toString().trim();

                double d_sum = Double.valueOf(sum);
                double d_amount = d_sum + d_price;

                double d_qunty = Double.valueOf(qunty);
                double tot_quantity = d_qunty + 1;

                String str_quanty = String.valueOf(tot_quantity);

                String str_amount = String.valueOf(d_amount);
                CartActivity.product_price.setText(str_amount);
                CartActivity.text_quantity.setText(str_quanty);

                String shipping = CartActivity.shipping_price.getText().toString().trim();
                String tax = CartActivity.tax_price.getText().toString().trim();

                double d_shipping = Double.valueOf(shipping);
                double d_tax = Double.valueOf(tax);

                double d_TotalAmount = d_amount + d_shipping + d_tax;

                String str_TotalAmount = String.valueOf(d_TotalAmount);
                CartActivity.text_totalmoney.setText(str_TotalAmount);

                addQuantity(token,quantity,"success",productId);


            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productid = showitem.getId();

                deleteProduct(token,productid);
                cartitem.remove(position);
                notifyDataSetChanged();

                str_quantity = holder.t2.getText().toString().trim();

                String pric = showitem.getPrice();
                d_quantity = Double.valueOf(str_quantity);


                String sum = CartActivity.product_price.getText().toString().trim();
                String qunty = CartActivity.text_quantity.getText().toString().trim();


                double d_qunty = Double.valueOf(qunty);
                double tot_quanty = d_qunty - d_quantity;
                String str_quanty = String.valueOf(tot_quanty);
                CartActivity.text_quantity.setText(str_quanty);

                String productprice = holder.product_price.getText().toString().trim();
                double d_productprice = Double.valueOf(productprice);
                double d_sum = Double.valueOf(sum);
                double d_total = d_sum - d_productprice ;

                String str_amount = String.valueOf(d_total);
                CartActivity.product_price.setText(str_amount);


                String shipping = CartActivity.shipping_price.getText().toString().trim();
                String tax = CartActivity.tax_price.getText().toString().trim();

                double d_shipping = Double.valueOf(shipping);
                double d_tax = Double.valueOf(tax);

                double d_TotalAmount = d_total + d_shipping + d_tax;

                String str_TotalAmount = String.valueOf(d_TotalAmount);
                CartActivity.text_totalmoney.setText(str_TotalAmount);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_price,quantity,product_Name,t1,t2,t3;
        LinearLayout linearLayout;
        ImageView btn_delete,productImage;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            product_Name = itemView.findViewById(R.id.product_Name);
            product_price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.quantity);
            productImage = itemView.findViewById(R.id.productImage);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }

        private void linearLayout(Boolean x){
            int y = Integer.parseInt(t2.getText().toString());
            if(x){
                y++;
                t2.setText(String.valueOf(y));
            }else {
                y--;
                if(y <= 0){
                    t2.setText("1");
                }else {
                    t2.setText(String.valueOf(y));
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void addQuantity(String token,int quantity,String index,String Productid) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Update Quantity Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        String url = AppURL.addquantity+productId;

        JSONObject jsonObject_quantity = new JSONObject();

        try{

            jsonObject_quantity.put("quantity",quantity);
            jsonObject_quantity.put("index",index);

        } catch (Exception e){}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,url , jsonObject_quantity, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String message = response.getString("err");
                    if(message.equals("false")){

                        Toast.makeText(context, "Quantity Add Sucessfully", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);
//                            if (error.networkResponse.statusCode == 400) {
                            String data = jsonError.getString("msg");
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    public void deleteProduct(String token,String Productid) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Delete Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        String url = AppURL.removecartbyid+Productid;

        Log.d("url",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    String message = response.getString("err");
                    if(message.equals("false")){

                        Toast.makeText(context, "Delete Product SuccessFully", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);
//                            if (error.networkResponse.statusCode == 400) {
                            String data = jsonError.getString("msg");
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }


}
