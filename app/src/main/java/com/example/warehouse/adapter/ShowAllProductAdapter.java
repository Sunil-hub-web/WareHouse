package com.example.warehouse.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.warehouse.modelclass.Image_ModelClass;
import com.example.warehouse.modelclass.Weight_ModelClass;
import com.example.warehouse.modelclass.ShowProductDetails_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAllProductAdapter extends RecyclerView.Adapter<ShowAllProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowProductDetails_ModelClass> product;
    ArrayList<Weight_ModelClass> productweight;
    ArrayList<Image_ModelClass> productimage;

    String token,str_quantity,productId;
    int quantity;

    public ShowAllProductAdapter(Context context, ArrayList<ShowProductDetails_ModelClass> productdetails) {

        this.context = context;
        this.product = productdetails;
    }

    @NonNull
    @NotNull
    @Override
    public ShowAllProductAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowAllProductAdapter.ViewHolder holder, int position) {

        token = SharedPrefManager.getInstance(context).getUser().getToken();

        ShowProductDetails_ModelClass product_details = product.get(position);

        holder.text_ProductName.setText(product_details.getTitle());
        holder.text_price.setText("???"+product_details.getPrice());
       // holder.text_Discprice.setText("???"+product_details.getDiscount());


        String disPrice = product_details.getDiscount();

        SpannableString ss = new SpannableString("???"+disPrice);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.text_Discprice.setText(ss);

        productweight = new ArrayList<>();
        productweight = product_details.getProductweight();

        CitySpinerAdapter adapter = new CitySpinerAdapter(context, android.R.layout.simple_spinner_dropdown_item
                , productweight);
        holder.text_weigh.setAdapter(adapter);

        productimage = new ArrayList<>();
        productimage = product_details.getProductimage();

        str_quantity = holder.t2.getText().toString().trim();
        quantity = Integer.valueOf(str_quantity);

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);

                str_quantity = holder.t2.getText().toString().trim();
                quantity = Integer.valueOf(str_quantity);
            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);

                str_quantity = holder.t2.getText().toString().trim();
                quantity = Integer.valueOf(str_quantity);
            }
        });

        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.addtocart.setVisibility(View.INVISIBLE);
                holder.linearLayout.setVisibility(View.VISIBLE);

                str_quantity = holder.t2.getText().toString().trim();
                quantity = Integer.valueOf(str_quantity);

                productId = product_details.getId();
                addToCart(quantity,token,productId);

            }
        });


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView text_ProductName,text_price,text_Discprice,t1, t2, t3;
        LinearLayout linearLayout;
        Button addtocart;
        Spinner text_weigh;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Discprice = itemView.findViewById(R.id.text_Discprice);
            text_price = itemView.findViewById(R.id.text_price);
            text_weigh = itemView.findViewById(R.id.text_weigh);
            text_ProductName = itemView.findViewById(R.id.text_ProductName);
            product_image = itemView.findViewById(R.id.product_image);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            addtocart = itemView.findViewById(R.id.addtocart);

        }

        private void linearLayout(Boolean x){
            int y = Integer.parseInt(t2.getText().toString());
            if(x){
                y++;
                t2.setText(String.valueOf(y));
            }else {
                y--;
                if(y <= 0){
                    t2.setText("0");
                }else {
                    t2.setText(String.valueOf(y));
                }
            }
        }
    }

    public void addToCart(int quantity,String token,String productid){

        String url = AppURL.addtocartItem+productid;

        Log.d("url",url);

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Add To Cart Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        Map<String,Integer> params = new HashMap<>();
        params.put("quantity",quantity);

        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url , jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    String message = response.getString("err");
                    String msg = response.getString("msg");
                    if(message.equals("false")){

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Session Expired ,Login Again" , Toast.LENGTH_SHORT).show();

               /* try {

                    SharedPrefManager sharedPrefManager = new SharedPrefManager((Context) clone());
                    sharedPrefManager.logout();
                    Intent intentLogin = new Intent(context, UserLoginPage.class);
                    context.startActivity(intentLogin);

                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }*/


            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> header = new HashMap<>();
                header.put("auth-token",token);
                return header;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }
}
