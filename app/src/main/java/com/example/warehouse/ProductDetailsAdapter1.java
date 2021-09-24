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
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ProductDetailsAdapter1 extends RecyclerView.Adapter<ProductDetailsAdapter1.Viewholder> {
    Context context;
    int[] image;
    public ProductDetailsAdapter1(Context applicationContext, int[] images) {

        this.context = applicationContext;
        this.image = images;
    }

    @NonNull
    @NotNull
    @Override
    public ProductDetailsAdapter1.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.home1,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductDetailsAdapter1.Viewholder holder, int position) {

        holder.imageView1.setImageResource(image[position]);

        String tt_1 = "₹59.00";

        SpannableString ss = new SpannableString(tt_1);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.price.setText(ss);

        holder.btn_addToCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                CartActivity cart = new CartActivity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,cart).addToBackStack(null).commit();

            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ProductActivity product = new ProductActivity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,product).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView price;
        Button btn_addToCart1;
        RelativeLayout relativeLayout;
        ImageView imageView1;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.product_image3);

            price = itemView.findViewById(R.id.price);
            btn_addToCart1 = itemView.findViewById(R.id.addtocart);
            relativeLayout = itemView.findViewById(R.id.clicl_product);
        }


    }
}
