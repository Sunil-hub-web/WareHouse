package com.example.warehouse.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.ProductImage_ModelClass;
import com.example.warehouse.modelclass.ProductWeight_ModelClass;
import com.example.warehouse.modelclass.ShowProductDetails_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowAllProductAdapter extends RecyclerView.Adapter<ShowAllProductAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowProductDetails_ModelClass> product;
    ArrayList<ProductWeight_ModelClass> productweight;
    ArrayList<ProductImage_ModelClass> productimage;


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

        ShowProductDetails_ModelClass product_details = product.get(position);

        holder.text_ProductName.setText(product_details.getTitle());
        holder.text_price.setText("₹"+product_details.getPrice());
       // holder.text_Discprice.setText("₹"+product_details.getDiscount());


        String disPrice = product_details.getDiscount();

        SpannableString ss = new SpannableString("₹"+disPrice);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.text_Discprice.setText(ss);

        productweight = new ArrayList<>();
        productweight = product_details.getProductweight();

        CitySpinerAdapter adapter = new CitySpinerAdapter(context, android.R.layout.simple_spinner_dropdown_item
                , productweight);
        holder.text_weigh.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView text_ProductName,text_price,text_Discprice;
        Spinner text_weigh;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Discprice = itemView.findViewById(R.id.text_Discprice);
            text_price = itemView.findViewById(R.id.text_price);
            text_weigh = itemView.findViewById(R.id.text_weigh);
            text_ProductName = itemView.findViewById(R.id.text_ProductName);
            product_image = itemView.findViewById(R.id.product_image);


        }
    }
}
