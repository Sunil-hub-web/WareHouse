package com.example.warehouse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.warehouse.R;
import com.example.warehouse.adapter.ProductDetailsAdapter1;
import com.example.warehouse.adapter.TopProductDetailsAdapter;
import com.example.warehouse.adapter.ViewPagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TopProduct extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    EditText edit_Serach;
    RecyclerView recyclerView,recyclerView1;
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    ProductDetailsAdapter1 productDetailsAdapter1;
    TopProductDetailsAdapter topproductDetailsAdapter;
    ImageView image_Notification, image_Cart;
    TextView text_name;


    int Images[]={R.drawable.rectangle49,R.drawable.rectangle51,R.drawable.rectangle49};
    int Images1[]={R.drawable.rectangle56,R.drawable.rectangle_57,R.drawable.rectangle56};

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

           View view = inflater.inflate(R.layout.topproduct_activity,container,false);

        viewPager = view.findViewById(R.id.viewPager);

        edit_Serach = view.findViewById(R.id.serach);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView1 = view.findViewById(R.id.recycler1);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);

        text_name.setText("Home");

        edit_Serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text_name.setText("Serach");
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                SerachPage search = new SerachPage();
                ft1.replace(R.id.frame,search);
                ft1.commit();

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

        linearLayoutManager = new LinearLayoutManager (getContext(),
                LinearLayoutManager.HORIZONTAL,false);
        linearLayoutManager1 = new LinearLayoutManager (getContext(),
                LinearLayoutManager.HORIZONTAL,false);


        topproductDetailsAdapter = new TopProductDetailsAdapter (getContext().getApplicationContext(),Images);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);
        recyclerView.setAdapter (topproductDetailsAdapter);

        productDetailsAdapter1 = new ProductDetailsAdapter1 (getContext().getApplicationContext(),Images1);
        recyclerView1.setLayoutManager (linearLayoutManager1);
        recyclerView1.setHasFixedSize (true);
        recyclerView1.setAdapter (productDetailsAdapter1);
        //sliderDotspanel = view.findViewById(R.id.sliderDots);

        createViewPager();

        return view;
    }

    public void createViewPager() {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        //dots = new ImageView[dotscount];

       /* for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 8, 8, 8);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.active_dot));*/

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            /*    for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.active_dot));*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
