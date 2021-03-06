package com.example.warehouse.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.fragment.ProductActivity;

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

                holder.btn_addToCart1.setVisibility(View.INVISIBLE);
                holder.linearLayout.setVisibility(View.VISIBLE);


               /* AppCompatActivity activity = (AppCompatActivity) view.getContext();
                CartActivity cart = new CartActivity();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame,cart).addToBackStack(null).commit();*/

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

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(false);
            }
        });

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout(true);
            }
        });


    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        Button btn_addToCart1;
        RelativeLayout relativeLayout;
        ImageView imageView1;

        LinearLayout linearLayout;
        TextView price,t1, t2, t3;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.product_image3);

            price = itemView.findViewById(R.id.price);
            btn_addToCart1 = itemView.findViewById(R.id.addtocart);
            relativeLayout = itemView.findViewById(R.id.clicl_product);

            linearLayout = itemView.findViewById(R.id.inc);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
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
}
