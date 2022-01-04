package com.example.warehouse.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.warehouse.activity.UserLoginPage;
import com.example.warehouse.fragment.ProductActivity;
import com.example.warehouse.modelclass.GroceryImage_ModelClass;
import com.example.warehouse.modelclass.GroceryWeight_ModelClass;
import com.example.warehouse.modelclass.Grocery_ModelClass;
import com.example.warehouse.modelclass.Image_ModelClass;
import com.example.warehouse.url.AppURL;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {

    Context context;
    ArrayList<Grocery_ModelClass>  Grocery;
    ArrayList<GroceryImage_ModelClass> Groceryimage;
    ArrayList<GroceryWeight_ModelClass>  Groceryweight;

    String token,str_quantity,productId;
    int quantity;

    public GroceryAdapter(Context context, ArrayList<Grocery_ModelClass> grocery, ArrayList<GroceryImage_ModelClass> groceryimage) {

        this.context = context;
        this.Grocery = grocery;
        this.Groceryimage = groceryimage;
    }

    @NonNull
    @NotNull
    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceryview,parent,false);
        return new GroceryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GroceryAdapter.ViewHolder holder, int position) {

        token = SharedPrefManager.getInstance(context).getUser().getToken();

        Grocery_ModelClass grocery_details = Grocery.get(position);

        holder.text_ProductName.setText(grocery_details.getTitle());
        holder.text_price.setText("₹"+grocery_details.getPrice());
        // holder.text_Discprice.setText("₹"+product_details.getDiscount());


        String disPrice = grocery_details.getDiscount();

        SpannableString ss = new SpannableString("₹"+disPrice);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.text_Discprice.setText(ss);

        Groceryweight = new ArrayList<>();
        Groceryweight = grocery_details.getWeight_modelClasses();

        GrocerySpinerAdapter adapter = new GrocerySpinerAdapter(context, android.R.layout.simple_spinner_dropdown_item
                , Groceryweight);
        holder.text_weigh.setAdapter(adapter);

        ArrayList<GroceryImage_ModelClass> image_modelClass = grocery_details.getImage_modelClasses();
        String image = "https://kisaanandfactory.com/static_file/"+image_modelClass.get(0);
        Log.d("ranj_adapter_image",image);
        Log.d("ranj_adapter_image",image_modelClass.get(0)+"");
        Picasso.with(context).load(image).into(holder.product_image);

        str_quantity = holder.t2.getText().toString().trim();
        quantity = Integer.valueOf(str_quantity);

        holder.clicl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productid = grocery_details.getId();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ProductActivity productActivity = new ProductActivity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, productActivity,productid).addToBackStack(null).commit();

            }
        });

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

                productId = grocery_details.getId();
                addToCart(quantity,token,productId);

            }
        });

    }

    @Override
    public int getItemCount() {
        return  Grocery.size()>4?4:Grocery.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        Spinner text_weigh;
        TextView text_ProductName,text_price,text_Discprice,t1, t2, t3;
        LinearLayout linearLayout;
        Button addtocart;
        RelativeLayout clicl_product;


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
            clicl_product = itemView.findViewById(R.id.clicl_product);
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

        String url =AppURL.addtocartItem+productid;

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
