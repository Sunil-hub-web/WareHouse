package com.example.warehouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.activity.ViewProductDetails;
import com.example.warehouse.fragment.ProductActivity;
import com.example.warehouse.modelclass.Data_ModelClass;
import com.example.warehouse.modelclass.TopProduct_ModelClass;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewholder> {

    Context context;
    AppCompatActivity activity;
    ArrayList<TopProduct_ModelClass> top_product;
    ArrayList<Data_ModelClass> top_productData;

    public ProductDetailsAdapter(Context context, ArrayList<TopProduct_ModelClass> topProduct) {

        this.context = context;
        this.top_product = topProduct;

    }

    @NonNull
    @NotNull
    @Override

    public ProductDetailsAdapter.MyViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showtopproduct, parent, false);
        return new ProductDetailsAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductDetailsAdapter.MyViewholder holder, int position) {

        TopProduct_ModelClass product = top_product.get(position);

        holder.text_product.setText(product.getName());

        top_productData = new ArrayList<>();
        top_productData = product.getData();

        Log.d("top_productData", top_productData.toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        ViewProductAdapter viewProductAdapter = new ViewProductAdapter(context, top_productData);
        holder.recyclerTopProduct.setLayoutManager(linearLayoutManager);
        holder.recyclerTopProduct.setHasFixedSize(true);
        holder.recyclerTopProduct.setAdapter(viewProductAdapter);

        holder.text_TopProductsViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = holder.text_product.getText().toString().trim();

                top_productData = new ArrayList<>();
                top_productData = product.getData();

                SharedPreferences product_sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = product_sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(top_productData);
                editor.putString("task list", json);
                editor.apply();

                Intent intent = new Intent(context, ViewProductDetails.class);
                intent.putExtra("productName", name);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return top_product.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView text_product, text_TopProductsViewAll;
        RecyclerView recyclerTopProduct;

        public MyViewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_product = itemView.findViewById(R.id.text_product);
            recyclerTopProduct = itemView.findViewById(R.id.recyclerProduct);
            text_TopProductsViewAll = itemView.findViewById(R.id.text_TopProductsViewAll);

        }

    }
}
