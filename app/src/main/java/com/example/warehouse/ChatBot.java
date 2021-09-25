package com.example.warehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

public class ChatBot extends Fragment {

    RelativeLayout rel_ChatWithAdmin;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.chatbot_activity,container,false);

        rel_ChatWithAdmin = view.findViewById(R.id.chatAdhmin);

        rel_ChatWithAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //text_name.setText("Serach");
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ChatActivity caht = new ChatActivity();
                ft1.replace(R.id.frame,caht);
                ft1.commit();

            }
        });


        return view;
    }
}
