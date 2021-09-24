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

public class TopProductDetailsAdapter extends RecyclerView.Adapter<TopProductDetailsAdapter.Viewholder> {
    Context context;
    int[] image;
    //ArrayList<MostSoldProduct> arraysell = new ArrayList<MostSoldProduct>();
    AppCompatActivity activity;

    public TopProductDetailsAdapter(Context applicationContext, int[] images) {

        this.context = applicationContext;
        this.image = images;
    }

    @NonNull
    @NotNull
    @Override
    public TopProductDetailsAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topproduct,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopProductDetailsAdapter.Viewholder holder, int position) {

        holder.imageView1.setImageResource(image[position]);


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

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        Button btn_addToCart1;
        RelativeLayout relativeLayout;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.product_image);
            btn_addToCart1 = itemView.findViewById(R.id.addtocart);
            relativeLayout = itemView.findViewById(R.id.clicl_product);
        }


    }
}
