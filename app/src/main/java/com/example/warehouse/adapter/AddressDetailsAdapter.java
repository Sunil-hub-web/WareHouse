package com.example.warehouse.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.warehouse.R;
import com.example.warehouse.SharedPrefManager;
import com.example.warehouse.activity.AddressDetails;
import com.example.warehouse.modelclass.AddressDetails_ModelClass;
import com.example.warehouse.url.AppURL;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressDetailsAdapter extends RecyclerView.Adapter<AddressDetailsAdapter.Viewholder> {

    Context context;
    ArrayList<AddressDetails_ModelClass> address ;
    public static String addressid,token,all_address,addressId;
    int index;


    public AddressDetailsAdapter(Context context, ArrayList<AddressDetails_ModelClass> viewAddressDetails) {

        this.context = context;
        this.address = viewAddressDetails;

    }

    @NonNull
    @NotNull
    @Override
    public AddressDetailsAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewaddressdetails,parent,false);
        return new Viewholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AddressDetailsAdapter.Viewholder holder, int position) {

        AddressDetails_ModelClass address_details = address.get(position);

        holder.text_House.setText(Html.fromHtml("<font color='#000000'><b>HouseNo :</b><br></font>"+address_details.getHouse()));
        holder.tetx_street.setText(Html.fromHtml("<font color='#000000'><b>street :</b><br></font>"+address_details.getStreet()));
        holder.text_locality.setText(Html.fromHtml("<font color='#000000'><b>locality :</b><br></font>"+address_details.getLocality()));
        holder.text_city.setText(Html.fromHtml("<font color='#000000'><b>city :</b><br></font>"+address_details.getCity()));
        holder.text_state.setText(Html.fromHtml("<font color='#000000'><b>state :</b><br></font>"+address_details.getState()));
        holder.text_country.setText(Html.fromHtml("<font color='#000000'><b>country :</b><br></font>"+address_details.getCountry()));
        holder.text_zip.setText(Html.fromHtml("<font color='#000000'><b>zip :</b><br></font>"+address_details.getZip()));
        holder.text_LatLong.setText(Html.fromHtml("<font color='#000000'><b>LatLong :</b><br></font>"+address_details.getLatitude()+","+address_details.getLongitude()));

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                token = SharedPrefManager.getInstance(context).getUser().getToken();

                addressid = address_details.getId();

                deleteAddress(token);

                address.remove(position);
                notifyDataSetChanged();
                notifyItemChanged(position);

            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all_address = address_details.getHouse()+","+address_details.getStreet()+","+address_details.getLocality()+","+
                        address_details.getCity()+","+address_details.getState()+","+address_details.getCountry()+""+address_details.getZip();
                addressId = address_details.getId();
            }
        });

        holder.lin_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressId = address_details.getId();

                index = position;
                notifyDataSetChanged();
                all_address = address_details.getHouse()+","+address_details.getStreet()+","+address_details.getLocality()+","+
                        address_details.getCity()+","+address_details.getState()+","+address_details.getCountry()+""+address_details.getZip();

            }
        });

        if(index == position){

            holder.lin_address.setBackgroundResource(R.drawable.selectaddressback);
            holder.lin_address.setElevation(15);

            addressId = address_details.getId();
            //Toast.makeText(context, addressId, Toast.LENGTH_SHORT).show();

        }
        else {
            holder.lin_address.setBackgroundResource(R.drawable.edittextback);
            holder.lin_address.setElevation(5);
        }
    }

    public String addressvalue(){

        return all_address;
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView text_House,tetx_street,text_locality,text_city,text_state,text_country,text_zip,text_LatLong;
        Button btn_Delete;
        CardView card;
        LinearLayout lin_address;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_House = itemView.findViewById(R.id.text_House);
            tetx_street = itemView.findViewById(R.id.tetx_street);
            text_locality = itemView.findViewById(R.id.text_locality);
            text_city = itemView.findViewById(R.id.text_city);
            text_state = itemView.findViewById(R.id.text_state);
            text_country = itemView.findViewById(R.id.text_country);
            text_zip = itemView.findViewById(R.id.text_zip);
            text_LatLong = itemView.findViewById(R.id.text_LatLong);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
            card = itemView.findViewById(R.id.card);
            lin_address = itemView.findViewById(R.id.lin_address);

        }
    }

    public void deleteAddress(String token){
        String url = AppURL.deleteAddressDetails+addressid;
        Log.d("Ranj_Delete_Url",url);

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Delete Address Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);



        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Ranj_dltAdres_response",response.toString());

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("err");
                    String msg = jsonObject.getString("msg");
                    if(message.equals("true")){

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Ranj_dltAdres_error_try",e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace();
                Log.d("Ranj_deleteAddres_error",error.toString());
                Toast.makeText (context, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("auth-token", token);
                return header;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
