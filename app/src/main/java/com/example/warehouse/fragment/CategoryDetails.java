package com.example.warehouse.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.UserLoginPage;
import com.example.warehouse.adapter.CategoryAdapter;
import com.example.warehouse.modelclass.Category_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryDetails extends Fragment {

    RecyclerView categoryRecycler;
    TextView text_name;
    ImageView image_Notification, image_Cart;
    GridLayoutManager gridLayoutManager;
    CategoryAdapter categoryAdapter;
    ArrayList<Category_ModelClass> category = new ArrayList<>();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.showcategory, container, false);

        text_name = view.findViewById(R.id.name);
        image_Notification = view.findViewById(R.id.imagenotification);
        image_Cart = view.findViewById(R.id.imagecart);

        text_name.setText("Category Details");

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

        categoryRecycler = view.findViewById(R.id.categoryRecycler);


        viewCategory();


        return view;
    }

    public void viewCategory() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("View Category Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppURL.viewCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("err");
                    String msg = jsonObject.getString("msg");
                    String data = jsonObject.getString("data");

                    if (message.equals("false")) {

                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray_Data = new JSONArray(data);
                        for (int i = 0; i < jsonArray_Data.length(); i++) {

                            JSONObject jsonObject_Data = jsonArray_Data.getJSONObject(i);

                            String id = jsonObject_Data.getString("_id");
                            String name = jsonObject_Data.getString("name");
                            String productType = jsonObject_Data.getString("productType");

                            Category_ModelClass category_modelClass = new Category_ModelClass(
                                    id, name, productType
                            );

                            category.add(category_modelClass);
                        }

                        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                        categoryAdapter = new CategoryAdapter(category, getContext());
                        categoryRecycler.setLayoutManager(gridLayoutManager);
                        categoryRecycler.setHasFixedSize(true);
                        categoryRecycler.setAdapter(categoryAdapter);

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
                Log.d("Ranj_addresError", error.toString());
                Toast.makeText(getContext(), "Session Expired ,Login Again", Toast.LENGTH_SHORT).show();

                SharedPrefManager sharedPrefManager = new SharedPrefManager(getActivity());
                sharedPrefManager.logout();
                Intent intentLogin = new Intent(getActivity(), UserLoginPage.class);
                startActivity(intentLogin);

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
}
