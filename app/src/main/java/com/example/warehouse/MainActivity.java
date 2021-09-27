package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mydrawer;
    NavigationView navigationView;
    TextView text_homePage, text_OrderPage, text_name, text_PaymentHistory,
            text_Chart, text_ProductListing, text_Supplier;
    ImageView image_Notification, image_Cart;

    private Boolean exit = false;

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        mydrawer = (DrawerLayout) findViewById(R.id.mydrwaer);
        navigationView = findViewById(R.id.navigationview);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        text_homePage = header.findViewById(R.id.nav_home);

        text_OrderPage = header.findViewById(R.id.nav_order);
        text_PaymentHistory = header.findViewById(R.id.nav_paymentHistory);
        text_Chart = header.findViewById(R.id.nav_chart);
        text_ProductListing = header.findViewById(R.id.nav_productlisting);
        text_Supplier = header.findViewById(R.id.nav_supplier);
        text_name = findViewById(R.id.name);
        image_Notification = findViewById(R.id.imagenotification);
        image_Cart = findViewById(R.id.imagecart);

        text_name.setText("Home Page");
        mydrawer.closeDrawer(GravityCompat.START);
        image_Notification.setVisibility(View.VISIBLE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Home home = new Home();
        ft.replace(R.id.frame, home,"testID");
        ft.commit();

        text_homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText("Home Page");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Home home = new Home();
                ft.replace(R.id.frame, home,"testID");
                ft.commit();

            }
        });

        text_OrderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText("Order");
                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MyOrderActivity order = new MyOrderActivity();
                ft.replace(R.id.frame, order);
                ft.commit();
            }
        });

        text_PaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mydrawer.closeDrawer(GravityCompat.START);
                image_Notification.setVisibility(View.VISIBLE);
                image_Cart.setVisibility(View.VISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                PaymentHistoryDetails payment = new PaymentHistoryDetails();
                ft.replace(R.id.frame, payment);
                ft.commit();

            }
        });

        text_Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ChatBot chatBot = new ChatBot();
                ft.replace(R.id.frame, chatBot);
                ft.commit();


            }
        });

        text_Supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_name.setText("Supplier");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SupplierPage supplier = new SupplierPage();
                ft.replace(R.id.frame, supplier);
                ft.commit();

            }
        });

        image_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("Cart");
                image_Cart.setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CartActivity cart = new CartActivity();
                ft.replace(R.id.frame, cart);
                ft.commit();

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        mydrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void Clickmenu(View view){

        // open drawer
        openDrawer(mydrawer);
    }

    private static void openDrawer(DrawerLayout drawerLayout){

        // opendrawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Home test = (Home) getSupportFragmentManager().findFragmentByTag("testID");

        if (test != null && test.isVisible()) {

            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }, 4 * 1000);
            }
        }
        else {

            text_name.setText("Home Page");
            MainActivity.fragmentManager.beginTransaction()
                    .replace(R.id.frame,new Home(),"testID").addToBackStack(null).commit();
            image_Notification.setVisibility(View.VISIBLE);
            image_Cart.setVisibility(View.VISIBLE);

        }
    }
}
