package com.example.warehouse.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.adapter.ShowAllHomePageProductAdapter;
import com.example.warehouse.adapter.ShowCategoryProductAdapter;
import com.example.warehouse.adapter.VegetablesAdapter;
import com.example.warehouse.modelclass.CateGoryImage_ModelClass;
import com.example.warehouse.modelclass.CategoryProduct_ModelClass;
import com.example.warehouse.modelclass.CategoryWeight_ModelClass;
import com.example.warehouse.modelclass.Category_ModelClass;
import com.example.warehouse.modelclass.Image_ModelClass;
import com.example.warehouse.modelclass.ShowHomeProduct;
import com.example.warehouse.modelclass.SnacksImage_ModelClass;
import com.example.warehouse.modelclass.SnacksWeight_ModelClass;
import com.example.warehouse.modelclass.Snacks_ModelClass;
import com.example.warehouse.modelclass.Vegetables_Modelclass;
import com.example.warehouse.modelclass.Weight_ModelClass;
import com.example.warehouse.url.AppURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewCategoryProduct extends AppCompatActivity {

    TextView text_name;
    RecyclerView categoryProductRecycler;
    ImageView img_Back;

    ArrayList<CategoryProduct_ModelClass> category;
    ArrayList<CateGoryImage_ModelClass> cateGoryImage;
    ArrayList<CategoryWeight_ModelClass> categoryWeight;
    GridLayoutManager gridLayoutManager;
    ShowCategoryProductAdapter showCategoryProductAdapter;

    String categoryid,categoryname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_product);

        categoryProductRecycler = findViewById(R.id.categoryProductRecycler);
        text_name = findViewById(R.id.text_name);
        img_Back = findViewById(R.id.img_Back);

        Intent intent = getIntent();

        categoryid = intent.getStringExtra("categoryid");
        categoryname = intent.getStringExtra("categoryname");

        text_name.setText(categoryname);

        categoryProduct(categoryid);
    }

    public void categoryProduct(String productid){

        String url = AppURL.viewCategoryProducts+productid;

        ProgressDialog progressDialog = new ProgressDialog(ViewCategoryProduct.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Show Product Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");

                    if(message.equals("false")){

                        category = new ArrayList<>();

                        String productDetails = jsonObject.getString("productDetails");

                        JSONArray jsonArray_vegetables = new JSONArray(productDetails);

                        for (int i = 0; i < jsonArray_vegetables.length(); i++) {

                            JSONObject jsonObject_product = jsonArray_vegetables.getJSONObject(i);

                            String discount = jsonObject_product.getString("discount");
                            String id = jsonObject_product.getString("_id");
                            String title = jsonObject_product.getString("title");
                            String price = jsonObject_product.getString("price");
                            String type = jsonObject_product.getString("type");
                            String description = jsonObject_product.getString("description");

                            cateGoryImage = new ArrayList<>();

                            String images = jsonObject_product.getString("images");

                            JSONArray jsonArray_images = new JSONArray(images);

                            for (int j = 0; j < jsonArray_images.length(); j++) {

                                String image = jsonArray_images.get(j).toString();


                                CateGoryImage_ModelClass cateGoryImage_modelClass = new CateGoryImage_ModelClass(
                                        image
                                );

                                cateGoryImage.add(cateGoryImage_modelClass);

                            }

                            categoryWeight = new ArrayList<>();

                            String weight = jsonObject_product.getString("weight");

                            JSONArray jsonArray_weight = new JSONArray(weight);


                            for (int k = 0; k < jsonArray_weight.length(); k++) {

                                String str_weight = jsonArray_weight.get(k).toString();


                                CategoryWeight_ModelClass categoryWeight_modelClass = new CategoryWeight_ModelClass(
                                        str_weight
                                );
                                categoryWeight.add(categoryWeight_modelClass);

                            }

                            CategoryProduct_ModelClass categoryProduct_modelClass = new CategoryProduct_ModelClass(

                                    discount, id, title, price, type, description, cateGoryImage, categoryWeight

                            );

                            category.add(categoryProduct_modelClass);

                        }

                        gridLayoutManager = new GridLayoutManager(ViewCategoryProduct.this,2, GridLayoutManager.VERTICAL, false);
                        showCategoryProductAdapter = new ShowCategoryProductAdapter(ViewCategoryProduct.this, category);
                        categoryProductRecycler.setLayoutManager(gridLayoutManager);
                        categoryProductRecycler.setHasFixedSize(true);
                        categoryProductRecycler.setAdapter(showCategoryProductAdapter);


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
                Toast.makeText(ViewCategoryProduct.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewCategoryProduct.this);
        requestQueue.add(stringRequest);


    }
}