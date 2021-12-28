package com.example.warehouse.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warehouse.MainActivity;
import com.example.warehouse.R;
import com.example.warehouse.adapter.ShowAllHomePageProductAdapter;
import com.example.warehouse.adapter.VegetablesAdapter;
import com.example.warehouse.modelclass.Grocery_ModelClass;
import com.example.warehouse.modelclass.HouseholdItems_ModelClass;
import com.example.warehouse.modelclass.ShowHomeProduct;
import com.example.warehouse.modelclass.Snacks_ModelClass;
import com.example.warehouse.modelclass.TopProduct_ModelClass;
import com.example.warehouse.modelclass.Vegetables_Modelclass;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewProductDetails extends AppCompatActivity {

    TextView text_name;
    RecyclerView productRecycler;
    ImageView img_Back;

    ArrayList<ShowHomeProduct> showHomeProduct = new ArrayList<>();
    String productName;
    GridLayoutManager gridLayoutManager;
    ShowAllHomePageProductAdapter showAllHomePageProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_details);

        text_name = findViewById(R.id.text_name);
        productRecycler = findViewById(R.id.productRecycler);
        img_Back = findViewById(R.id.img_Back);

        Intent intent = getIntent();
        productName = intent.getStringExtra("productName");
        text_name.setText(productName);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);

            Type type = new TypeToken<ArrayList<ShowHomeProduct>>() {}.getType();
            showHomeProduct = gson.fromJson(json, type);

            Log.d("showHomeProduct",showHomeProduct.toString());

            gridLayoutManager = new GridLayoutManager(ViewProductDetails.this, 2,GridLayoutManager.VERTICAL, false);
            showAllHomePageProductAdapter = new ShowAllHomePageProductAdapter(ViewProductDetails.this, showHomeProduct);
            productRecycler.setLayoutManager(gridLayoutManager);
            productRecycler.setHasFixedSize(true);
            productRecycler.setAdapter(showAllHomePageProductAdapter);


        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ViewProductDetails.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}