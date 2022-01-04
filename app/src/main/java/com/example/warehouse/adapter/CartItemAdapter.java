package com.example.warehouse.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.ProductDetailsImage_ModelClass;
import com.example.warehouse.modelclass.ProductDetailsWeight_ModelClass;
import com.example.warehouse.modelclass.ShowCartItem_ModelClass;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowCartItem_ModelClass> cartitem;
    String token,str_quantity,productId;
    int quantity;
    double price,d_quantity;

    public CartItemAdapter(Context context, ArrayList<ShowCartItem_ModelClass> show_showCartItem) {

        this.context = context;
        this.cartitem = show_showCartItem;
    }

    @NonNull
    @NotNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersummary,parent,false);

        return new CartItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartItemAdapter.ViewHolder holder, int position) {

        ShowCartItem_ModelClass showitem = cartitem.get(position);

        ArrayList<ProductDetailsImage_ModelClass> productDetailsImage = showitem.getImage_modelClasses();
        String image = "https://kisaanandfactory.com/static_file/"+productDetailsImage.get(0);
        Log.d("ranj_adapter_image",image);
        Log.d("ranj_adapter_image",productDetailsImage.get(0)+"");
        Picasso.with(context).load(image).into(holder.productImage);

        holder.productName.setText(showitem.getTitle());

        String price1 = showitem.getPrice();
        String quantity1 = showitem.getQuantity();

        Double d_quantity = Double.valueOf(quantity1);
        Double d_salesPrice = Double.valueOf(price1);
        Double amount = d_quantity * d_salesPrice;
        String str_amount = String.valueOf(amount);

        holder.total.setText(str_amount);
    }

    @Override
    public int getItemCount() {
        return cartitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName,total;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            total = itemView.findViewById(R.id.total);
        }
    }
}
