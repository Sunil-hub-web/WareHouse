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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.warehouse.fragment.CartActivity;
import com.example.warehouse.fragment.CategoryDetails;
import com.example.warehouse.fragment.ChatBot;
import com.example.warehouse.fragment.Home;
import com.example.warehouse.fragment.MyOrderActivity;
import com.example.warehouse.fragment.PaymentHistoryDetails;
import com.example.warehouse.fragment.ShowAddressDetails;
import com.example.warehouse.fragment.ShowAllProduct;
import com.example.warehouse.fragment.SupplierPage;
import com.example.warehouse.fragment.ViewUserDetailsFragment;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mydrawer;
    NavigationView navigationView;

    TextView text_homePage, text_OrderPage, text_name, text_PaymentHistory,
            text_Chart, text_ProductListing, text_Supplier,nav_Myaccount,nav_Myaddress,nav_Logout,nav_Category;
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
        nav_Myaccount = header.findViewById(R.id.nav_Myaccount);
        nav_Myaddress = header.findViewById(R.id.nav_Myaddress);
        nav_Logout = header.findViewById(R.id.nav_Logout);
        nav_Category = header.findViewById(R.id.nav_Category);
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

        nav_Myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("My Account");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ViewUserDetailsFragment viewUserDetailsFragment = new ViewUserDetailsFragment();
                ft.replace(R.id.frame, viewUserDetailsFragment);
                ft.commit();
            }
        });

        text_ProductListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("Product Details");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ShowAllProduct showAllProduct = new ShowAllProduct();
                ft.replace(R.id.frame, showAllProduct);
                ft.commit();

            }
        });

        nav_Myaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("MyAddress");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ShowAddressDetails showAddressDetails = new ShowAddressDetails();
                ft.replace(R.id.frame, showAddressDetails);
                ft.commit();


            }
        });

        nav_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(MainActivity.this).logout();
            }
        });

        nav_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_name.setText("Category");
                mydrawer.closeDrawer(GravityCompat.START);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CategoryDetails categoryDetails = new CategoryDetails();
                ft.replace(R.id.frame, categoryDetails);
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
