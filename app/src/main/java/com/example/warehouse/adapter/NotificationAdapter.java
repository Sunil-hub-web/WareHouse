package com.example.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.Notification_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    ArrayList<Notification_ModelClass> notific;
    DateFormat dateFormat;


    public NotificationAdapter(Context context, ArrayList<Notification_ModelClass> notifi) {

        this.context = context;
        this.notific = notifi;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shownotification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.ViewHolder holder, int position) {

        Notification_ModelClass noti = notific.get(position);

        holder.text_Showtitle.setText(noti.getTitle());
        holder.text_ShowDescription.setText(noti.getDescription());

        String datetime = noti.getDateTime();

       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            dateFormat = new SimpleDateFormat(datetime);

            try {
                Date date = dateFormat.parse(datetime);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
                String dateStr = formatter.format(date);

                holder.text_ShowDateTime.setText(dateStr);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return notific.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_Showtitle,text_ShowDescription,text_ShowDateTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            text_Showtitle = itemView.findViewById(R.id.text_Showtitle);
            text_ShowDescription = itemView.findViewById(R.id.text_ShowDescription);
            text_ShowDateTime = itemView.findViewById(R.id.text_ShowDateTime);


        }
    }
}
