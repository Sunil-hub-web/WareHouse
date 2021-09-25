package com.example.warehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

public class MyOrderActivity extends Fragment {

    TextView text_back,name;
    Button btn_ContinueShoping;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myorder_activity,container,false);


        text_back = view.findViewById(R.id.back);
        btn_ContinueShoping = view.findViewById(R.id.continueShoping);

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                Home home = new Home();
                ft.replace(R.id.frame,home);
                ft.commit();*/

                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.frame,new Home(),null).addToBackStack(null).commit();
            }
        });

        btn_ContinueShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Home home = new Home();
                ft.replace(R.id.frame,home);
                ft.commit();
            }
        });


        return view;
    }
}
