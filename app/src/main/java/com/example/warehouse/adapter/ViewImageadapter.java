package com.example.warehouse.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewImageadapter extends RecyclerView.Adapter<ViewImageadapter.ViewHolder> {

    Context context;

    ArrayList<ProductDetailsImage_ModelClass> show_Image;

    public ViewImageadapter(Context context, ArrayList<ProductDetailsImage_ModelClass> productDetailsImage) {

        this.context = context;
        this.show_Image = productDetailsImage;
    }

    @NonNull
    @NotNull
    @Override
    public ViewImageadapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewImageadapter.ViewHolder holder, int position) {

        ProductDetailsImage_ModelClass slideImage = show_Image.get(position);

        String image = "https://kisaanandfactory.com/static_file/"+slideImage.getImages();
        Picasso.with(context).load(image).into(holder.img_showImage);

    }

    @Override
    public int getItemCount() {
        return show_Image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_showImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            img_showImage = itemView.findViewById(R.id.img_showImage);
        }
    }
}
