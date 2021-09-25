package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mydrawer;
    private ActionBarDrawerToggle mytoggle;
    NavigationView navigationView;
    TextView text_homePage,text_OrderPage,text_name,text_PaymentHistory,
              text_Chart,text_ProductListing,text_Supplier;
    ImageView image_Notification,image_Cart;
    LayoutInflater inflater;
    Button btn_addToCart1;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.home_home,null);
        actionBar.setCustomView(view1);

        image_Notification = view1.findViewById(R.id.imagenotification);
        image_Cart = view1.findViewById(R.id.imagecart);
        text_name = view1.findViewById(R.id.name);

        mydrawer = (DrawerLayout) findViewById(R.id.mydrwaer);
        navigationView = findViewById(R.id.navigationview);

        navigationView.setNavigationItemSelectedListener(this);
        mytoggle = new ActionBarDrawerToggle(this, mydrawer, R.string.open, R.string.close);

        actionBar.setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        View header = navigationView.getHeaderView(0);
        text_homePage = header.findViewById(R.id.nav_home);

        text_OrderPage = header.findViewById(R.id.nav_order);
        text_PaymentHistory = header.findViewById(R.id.nav_paymentHistory);
        text_Chart = header.findViewById(R.id.nav_chart);
        text_ProductListing = header.findViewById(R.id.nav_productlisting);
        text_Supplier = header.findViewById(R.id.nav_supplier);
        mydrawer.addDrawerListener(mytoggle);
        mytoggle.syncState();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mytoggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

        text_name.setText("Home Page");
        mydrawer.closeDrawer(GravityCompat.START);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Home home = new Home();
        ft.replace(R.id.frame,home);
        ft.commit();

        text_homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Home Page");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Home home = new Home();
                ft.replace(R.id.frame,home);
                ft.commit();

            }
        });

        text_OrderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Order");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MyOrderActivity order = new MyOrderActivity();
                ft.replace(R.id.frame,order);
                ft.commit();
            }
        });
        image_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Notification");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Notifaction notification = new Notifaction();
                ft.replace(R.id.frame,notification);
                ft.commit();

                image_Notification.setVisibility(View.INVISIBLE);

            }
        });

        text_PaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Payment History");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                PaymentHistoryDetails payment = new PaymentHistoryDetails();
                ft.replace(R.id.frame,payment);
                ft.commit();

            }
        });
        text_Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Chat");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ChatBot chatBot = new ChatBot();
                ft.replace(R.id.frame,chatBot);
                ft.commit();

            }
        });

        text_ProductListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        text_Supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText ("Supplier");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SupplierPage supplier = new SupplierPage();
                ft.replace(R.id.frame,supplier);
                ft.commit();

            }
        });

        image_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                text_name.setText ("Cart");
                image_Cart.setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft.replace(R.id.frame,cart);
                ft.commit();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mytoggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        mydrawer.closeDrawer(GravityCompat.START);

        return true;
    }




}

