package com.example.warehouse;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Getsetter.MostSoldProduct;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.MyViewholder> {

    Context context;
    int[] image;
    //ArrayList<MostSoldProduct> arraysell = new ArrayList<MostSoldProduct>();
    AppCompatActivity activity;

    public ProductDetailsAdapter(Context context, int[] arraysell) {

        this.context = context;
        this.image = arraysell;
    }

    @NonNull
    @NotNull
    @Override


    public ProductDetailsAdapter.MyViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.home,parent,false);
        return new ProductDetailsAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductDetailsAdapter.MyViewholder holder, int position) {

        holder.imageView1.setImageResource(image[position]);

        String tt_1 = "₹59.00";

        SpannableString ss = new SpannableString(tt_1);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.price.setText(ss);

        holder.btn_addToCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity = (AppCompatActivity) view.getContext();
                CartActivity cart = new CartActivity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,cart).addToBackStack(null).commit();

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity = (AppCompatActivity) v.getContext();
                ProductActivity product = new ProductActivity();
                activity.getSupportFragmentManager().
                        beginTransaction().replace(R.id.frame,product).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        TextView price;
        Button btn_addToCart1;
        RelativeLayout relativeLayout;

        public MyViewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.price);
            btn_addToCart1 = itemView.findViewById(R.id.addtocart);
            relativeLayout = itemView.findViewById(R.id.clicl_product);


        }
    }
}
