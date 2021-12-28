package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.activity.ViewProductDetails;
import com.example.warehouse.adapter.GroceryAdapter;
import com.example.warehouse.adapter.HouseholdItemsAdapter;
import com.example.warehouse.adapter.ProductDetailsAdapter;
import com.example.warehouse.adapter.ProductDetailsAdapter1;
import com.example.warehouse.R;
import com.example.warehouse.adapter.SnacksAdapter;
import com.example.warehouse.adapter.VegetablesAdapter;
import com.example.warehouse.adapter.ViewPagerAdapter;
import com.example.warehouse.modelclass.DataImage_ModelClass;
import com.example.warehouse.modelclass.DataWeight_ModelClass;
import com.example.warehouse.modelclass.Data_ModelClass;
import com.example.warehouse.modelclass.GroceryImage_ModelClass;
import com.example.warehouse.modelclass.GroceryWeight_ModelClass;
import com.example.warehouse.modelclass.Grocery_ModelClass;
import com.example.warehouse.modelclass.HouseholdItemsImage_ModelClass;
import com.example.warehouse.modelclass.HouseholdItemsWeight_ModelClass;
import com.example.warehouse.modelclass.HouseholdItems_ModelClass;
import com.example.warehouse.modelclass.Image_ModelClass;
import com.example.warehouse.modelclass.SnacksImage_ModelClass;
import com.example.warehouse.modelclass.SnacksWeight_ModelClass;
import com.example.warehouse.modelclass.Snacks_ModelClass;
import com.example.warehouse.modelclass.TopProduct_ModelClass;
import com.example.warehouse.modelclass.Vegetables_Modelclass;
import com.example.warehouse.modelclass.Weight_ModelClass;
import com.example.warehouse.url.AppURL;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    private static final int NUM_PAGES = 3;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private ImageView[] dots;
    EditText edit_Serach;
    RecyclerView vegetablesRecycler, groceryRecycler, snacksRecycler, householdItemsRecycler, recyclerTopProduct;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1, linearLayoutManager2,
            linearLayoutManager3, linearLayoutManager4;
    ProductDetailsAdapter productDetailsAdapter;
    //ProductDetailsAdapter1 productDetailsAdapter1;
    VegetablesAdapter vegetablesAdapter;
    GroceryAdapter groceryAdapter;
    SnacksAdapter snacksAdapter;
    HouseholdItemsAdapter householdItemsAdapter;
    TextView text_name,text_VegetablesViewAll,text_GroceryViewAll,text_SnacksViewAll,text_HouseholdItemsViewAll,
             text_TopProductsViewall;
    ImageView image_Notification, image_Cart;
    int currentPage = 0;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 4000;
    private int dotscount;

    ArrayList<Vegetables_Modelclass> vegetable;
    ArrayList<Image_ModelClass> vegetableimage;
    ArrayList<Weight_ModelClass> vegetableweight;

    ArrayList<Grocery_ModelClass> Grocery;
    ArrayList<GroceryImage_ModelClass> Groceryimage;
    ArrayList<GroceryWeight_ModelClass> Groceryweight;

    ArrayList<Snacks_ModelClass> Snacks;
    ArrayList<SnacksImage_ModelClass> Snacksimage;
    ArrayList<SnacksWeight_ModelClass> Snacksweight;

    ArrayList<HouseholdItems_ModelClass> HouseholdItems;
    ArrayList<HouseholdItemsImage_ModelClass> householdItemsimage;
    ArrayList<HouseholdItemsWeight_ModelClass> householdItemsweight;

    ArrayList<TopProduct_ModelClass> topProduct;
    ArrayList<Data_ModelClass> topProducrData;
    ArrayList<DataImage_ModelClass> dataImage;
    ArrayList<DataWeight_ModelClass> dataWeight;

    String discount, id, title, price, type, description;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);


        edit_Serach = view.findViewById(R.id.serach);

        vegetablesRecycler = view.findViewById(R.id.vegetablesRecycler);
        groceryRecycler = view.findViewById(R.id.groceryRecycler);
        snacksRecycler = view.findViewById(R.id.snacksRecycler);
        householdItemsRecycler = view.findViewById(R.id.householdItemsRecycler);
        recyclerTopProduct = view.findViewById(R.id.recyclerTopProduct);
        text_VegetablesViewAll = view.findViewById(R.id.text_VegetablesViewAll);
        text_GroceryViewAll = view.findViewById(R.id.text_GroceryViewAll);
        text_SnacksViewAll = view.findViewById(R.id.text_SnacksViewAll);
        text_HouseholdItemsViewAll = view.findViewById(R.id.text_HouseholdItemsViewAll);


        edit_Serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                SerachPage search = new SerachPage();
                ft1.replace(R.id.frame, search);
                ft1.commit();

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


        showAllProduct();
        //showTopProduct();
        createViewPager();

        text_VegetablesViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences product_sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = product_sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(vegetable);
                editor.putString("task list", json);
                editor.apply();

                Intent intent = new Intent(getContext(), ViewProductDetails.class);
                intent.putExtra("productName","vegetable");
                startActivity(intent);

            }
        });

        text_GroceryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences product_sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = product_sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Grocery);
                editor.putString("task list", json);
                editor.apply();

                Intent intent = new Intent(getContext(), ViewProductDetails.class);
                intent.putExtra("productName","Grocery");
                startActivity(intent);

            }
        });

        text_SnacksViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences product_sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = product_sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Snacks);
                editor.putString("task list", json);
                editor.apply();

                Intent intent = new Intent(getContext(), ViewProductDetails.class);
                intent.putExtra("productName","Snacks");
                startActivity(intent);
            }
        });

        text_HouseholdItemsViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences product_sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = product_sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(HouseholdItems);
                editor.putString("task list", json);
                editor.apply();

                Intent intent = new Intent(getContext(), ViewProductDetails.class);
                intent.putExtra("productName","HouseholdItems");
                startActivity(intent);
            }
        });

        return view;
    }

    public void createViewPager() {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();

        ScrollViewpager();

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

    public void ScrollViewpager() {

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }


    public void showAllProduct() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getHomepageDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if (message.equals("false")) {

                        vegetable = new ArrayList<>();

                        String vegetables = jsonObject.getString("vegetables");
                        String grocery = jsonObject.getString("grocery");
                        String snacks = jsonObject.getString("snacks");
                        String householdItems = jsonObject.getString("householdItems");

                        JSONArray jsonArray_vegetables = new JSONArray(vegetables);

                        for (int i = 0; i < jsonArray_vegetables.length(); i++) {

                            JSONObject jsonObject_product = jsonArray_vegetables.getJSONObject(i);

                            discount = jsonObject_product.getString("discount");
                            id = jsonObject_product.getString("_id");
                            title = jsonObject_product.getString("title");
                            price = jsonObject_product.getString("price");
                            type = jsonObject_product.getString("type");
                            description = jsonObject_product.getString("description");

                            vegetableimage = new ArrayList<>();

                            String images = jsonObject_product.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(0).toString();


                                Image_ModelClass image_modelClass = new Image_ModelClass(
                                        image
                                );

                                vegetableimage.add(image_modelClass);

                            }

                            vegetableweight = new ArrayList<>();

                            String weight = jsonObject_product.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(0).toString();


                                Weight_ModelClass weight_modelClass = new Weight_ModelClass(
                                        str_weight
                                );
                                vegetableweight.add(weight_modelClass);

                            }

                            Vegetables_Modelclass vegetables_modelclass = new Vegetables_Modelclass(

                                    discount, id, title, price, type, description, vegetableimage, vegetableweight

                            );

                            vegetable.add(vegetables_modelclass);

                        }

                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        vegetablesAdapter = new VegetablesAdapter(getContext(), vegetable);
                        vegetablesRecycler.setLayoutManager(linearLayoutManager);
                        vegetablesRecycler.setHasFixedSize(true);
                        vegetablesRecycler.setAdapter(vegetablesAdapter);

                        // retrive data from Grocery

                        Grocery = new ArrayList<>();


                        JSONArray jsonArray_grocery = new JSONArray(grocery);

                        for (int i = 0; i < jsonArray_grocery.length(); i++) {

                            JSONObject jsonObject_grocery = jsonArray_grocery.getJSONObject(i);

                            String discount = jsonObject_grocery.getString("discount");
                            String id = jsonObject_grocery.getString("_id");
                            String title = jsonObject_grocery.getString("title");
                            String price = jsonObject_grocery.getString("price");
                            String type = jsonObject_grocery.getString("type");
                            String description = jsonObject_grocery.getString("description");

                            Groceryimage = new ArrayList<>();

                            String images = jsonObject_grocery.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(j).toString();


                                GroceryImage_ModelClass groceryImage_modelClass = new GroceryImage_ModelClass(
                                        image
                                );

                                Groceryimage.add(groceryImage_modelClass);

                            }

                            Groceryweight = new ArrayList<>();

                            String weight = jsonObject_grocery.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(k).toString();


                                GroceryWeight_ModelClass groceryWeight_modelClass = new GroceryWeight_ModelClass(
                                        str_weight
                                );
                                Groceryweight.add(groceryWeight_modelClass);

                            }

                            Grocery_ModelClass grocery_modelClass = new Grocery_ModelClass(

                                    discount, id, title, price, type, description, Groceryimage, Groceryweight

                            );

                            Grocery.add(grocery_modelClass);

                        }

                        linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        groceryAdapter = new GroceryAdapter(getContext(), Grocery,Groceryimage);
                        groceryRecycler.setLayoutManager(linearLayoutManager1);
                        groceryRecycler.setHasFixedSize(true);
                        groceryRecycler.setAdapter(groceryAdapter);

                        // Retrive data from Snakes

                        Snacks = new ArrayList<>();

                        JSONArray jsonArray_snacks = new JSONArray(snacks);

                        for (int i = 0; i < jsonArray_snacks.length(); i++) {

                            JSONObject jsonObject_snakes = jsonArray_snacks.getJSONObject(i);

                            String discount = jsonObject_snakes.getString("discount");
                            String id = jsonObject_snakes.getString("_id");
                            String title = jsonObject_snakes.getString("title");
                            String price = jsonObject_snakes.getString("price");
                            String type = jsonObject_snakes.getString("type");
                            String description = jsonObject_snakes.getString("description");

                            Snacksimage = new ArrayList<>();

                            String images = jsonObject_snakes.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(0).toString();


                                SnacksImage_ModelClass snacksImage_modelClass = new SnacksImage_ModelClass(
                                        image
                                );

                                Snacksimage.add(snacksImage_modelClass);

                            }

                            Snacksweight = new ArrayList<>();

                            String weight = jsonObject_snakes.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(0).toString();


                                SnacksWeight_ModelClass snacksWeight_modelClass = new SnacksWeight_ModelClass(
                                        str_weight
                                );
                                Snacksweight.add(snacksWeight_modelClass);


                            }

                            Snacks_ModelClass snacks_modelClass = new Snacks_ModelClass(

                                    discount, id, title, price, type, description, Snacksimage, Snacksweight

                            );

                            Snacks.add(snacks_modelClass);

                        }

                        linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        snacksAdapter = new SnacksAdapter(getContext(), Snacks);
                        snacksRecycler.setLayoutManager(linearLayoutManager2);
                        snacksRecycler.setHasFixedSize(true);
                        snacksRecycler.setAdapter(snacksAdapter);

                        // Retrive data from Snakes

                        HouseholdItems = new ArrayList<>();

                        JSONArray jsonArray_householdItems = new JSONArray(householdItems);

                        for (int i = 0; i < jsonArray_householdItems.length(); i++) {

                            JSONObject jsonObject_householdItems = jsonArray_householdItems.getJSONObject(i);

                            String discount = jsonObject_householdItems.getString("discount");
                            String id = jsonObject_householdItems.getString("_id");
                            String title = jsonObject_householdItems.getString("title");
                            String price = jsonObject_householdItems.getString("price");
                            String type = jsonObject_householdItems.getString("type");
                            String description = jsonObject_householdItems.getString("description");

                            householdItemsimage = new ArrayList<>();

                            String images = jsonObject_householdItems.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(0).toString();


                                HouseholdItemsImage_ModelClass householdItemsImageModelClass = new HouseholdItemsImage_ModelClass(
                                        image
                                );

                                householdItemsimage.add(householdItemsImageModelClass);
                                Log.d("productimage", vegetableimage.toString());

                            }

                            householdItemsweight = new ArrayList<>();

                            String weight = jsonObject_householdItems.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(0).toString();


                                HouseholdItemsWeight_ModelClass householdItemsWeightModelClass = new HouseholdItemsWeight_ModelClass(
                                        str_weight
                                );
                                householdItemsweight.add(householdItemsWeightModelClass);

                                Log.d("productweight", vegetableweight.toString());

                            }

                            HouseholdItems_ModelClass householdItems_modelClass = new HouseholdItems_ModelClass(

                                    discount, id, title, price, type, description, householdItemsimage, householdItemsweight

                            );

                            HouseholdItems.add(householdItems_modelClass);

                            Log.d("productdetails", vegetable.toString());

                        }

                        linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        householdItemsAdapter = new HouseholdItemsAdapter(getContext(), HouseholdItems);
                        householdItemsRecycler.setLayoutManager(linearLayoutManager3);
                        householdItemsRecycler.setHasFixedSize(true);
                        householdItemsRecycler.setAdapter(householdItemsAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void showTopProduct() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.getTopProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("err");

                    topProduct = new ArrayList<>();


                    String data_msg = jsonObject.getString("data");

                    if (message.equals("false")) {

                        JSONArray jsonArray_TopProduct = new JSONArray(data_msg);

                        for (int i = 0; i < jsonArray_TopProduct.length(); i++) {

                            JSONObject jsonObject_data = jsonArray_TopProduct.getJSONObject(i);

                            String name = jsonObject_data.getString("name");
                            String url = jsonObject_data.getString("url");

                            topProducrData = new ArrayList<>();

                            String data = jsonObject_data.getString("data");
                            JSONArray jsonArray_data = new JSONArray(data);

                            if(jsonArray_data == null){

                                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                            }else{

                                for (int k = 0; k < jsonArray_data.length(); k++) {

                                    JSONObject jsonObject_Data = jsonArray_data.getJSONObject(k);

                                    String discount = jsonObject_Data.getString("discount");
                                    String id = jsonObject_Data.getString("_id");
                                    String title = jsonObject_Data.getString("title");
                                    String price = jsonObject_Data.getString("price");
                                    String type = jsonObject_Data.getString("type");
                                    String description = jsonObject_Data.getString("description");

                                    dataImage = new ArrayList<>();

                                    String images = jsonObject_Data.getString("images");

                                    JSONArray jsonArray_images = new JSONArray(images);

                                    for (int j = 0; j < jsonArray_images.length(); j++) {

                                        String image = jsonArray_images.get(0).toString();


                                        DataImage_ModelClass dataImage_modelClass = new DataImage_ModelClass(
                                                image
                                        );

                                        dataImage.add(dataImage_modelClass);

                                    }

                                    dataWeight = new ArrayList<>();

                                    String weight = jsonObject_Data.getString("weight");

                                    JSONArray jsonArray_weight = new JSONArray(weight);


                                    for (int l = 0; l < jsonArray_weight.length(); l++) {

                                        String str_weight = jsonArray_weight.get(l).toString();


                                        DataWeight_ModelClass dataWeight_modelClass = new DataWeight_ModelClass(
                                                str_weight
                                        );
                                        dataWeight.add(dataWeight_modelClass);

                                    }

                                    Data_ModelClass data_modelClass = new Data_ModelClass(

                                            discount, id, title, price, type, description, dataImage, dataWeight
                                    );

                                    topProducrData.add(data_modelClass);

                                    Log.d("topProducrData",topProducrData.toString());

                                }

                            }

                            TopProduct_ModelClass topProduct_modelClass = new TopProduct_ModelClass(
                                    name, url, topProducrData
                            );

                            topProduct.add(topProduct_modelClass);

                        }

                        linearLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        productDetailsAdapter = new ProductDetailsAdapter(getContext(), topProduct);
                        recyclerTopProduct.setLayoutManager(linearLayoutManager4);
                        recyclerTopProduct.setHasFixedSize(true);
                        recyclerTopProduct.setAdapter(productDetailsAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}
