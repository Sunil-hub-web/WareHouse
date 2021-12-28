package com.example.warehouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.activity.ViewCategoryProduct;
import com.example.warehouse.modelclass.Category_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<Category_ModelClass> category;
    String categoryid;

    public CategoryAdapter(ArrayList<Category_ModelClass> category, Context context) {

        this.context = context;
        this.category = category;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorypage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.ViewHolder holder, int position) {

        Category_ModelClass category_details = category.get(position);
        holder.text_Category.setText(category_details.getCategoryName());

        holder.category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryid = category_details.getCategoryId();

                Intent intent = new Intent(context, ViewCategoryProduct.class);
                intent.putExtra("categoryid",category_details.getCategoryId());
                intent.putExtra("categoryname",category_details.getCategoryName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Category;
        CardView category_card;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Category = itemView.findViewById(R.id.text_Category);
            category_card = itemView.findViewById(R.id.category_card);
        }
    }
}
