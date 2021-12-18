package com.example.warehouse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehouse.R;

import org.jetbrains.annotations.NotNull;

public class ProductActivity extends Fragment {

    TextView t1, t2, t3;
    LinearLayout inc;
    Button btn_AddCart;
    ImageView image_Notification, image_Cart;
    TextView text_name;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.product_activity,container,false);

         btn_AddCart = view.findViewById(R.id.addCart);
        inc = view.findViewById(R.id.inc);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(false);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc(true);
            }
        });

        btn_AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //text_name.setText("Serach");

                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft1.replace(R.id.frame,cart);
                ft1.commit();
            }
        });

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);

        text_name.setText("Product");

        image_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText("Notification");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Notifaction notification = new Notifaction();
                ft.replace(R.id.frame, notification);
                ft.commit();

                image_Notification.setVisibility(View.INVISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
            }
        });

        image_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text_name.setText("Cart");
                image_Cart.setVisibility(View.INVISIBLE);
                image_Notification.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft.replace(R.id.frame, cart);
                ft.commit();

            }
        });


        return view;
    }

    private void inc(Boolean x){
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

        Toast.makeText(getActivity(), t2.getText(), Toast.LENGTH_SHORT).show();

    }
}
