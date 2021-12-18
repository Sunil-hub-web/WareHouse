package com.example.warehouse.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.AddressDetails_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddressDetailsAdapter extends RecyclerView.Adapter<AddressDetailsAdapter.Viewholder> {

    Context context;
    ArrayList<AddressDetails_ModelClass> address ;


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

    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView text_House,tetx_street,text_locality,text_city,text_state,text_country,text_zip,text_LatLong;
        Button btn_Delete;

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

        }
    }
}
