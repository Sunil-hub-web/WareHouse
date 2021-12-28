package com.example.warehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehouse.R;
import com.example.warehouse.modelclass.CategoryWeight_ModelClass;
import com.example.warehouse.modelclass.Weight_ModelClass;

import java.util.ArrayList;

public class CategorySpinerAdapter extends ArrayAdapter<CategoryWeight_ModelClass> {

    private ArrayList<CategoryWeight_ModelClass> myarrayList;

    public CategorySpinerAdapter(Context context, int textViewResourceId, ArrayList<CategoryWeight_ModelClass> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList = modelArrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Nullable
    @Override
    public CategoryWeight_ModelClass getItem(int position) {
        return myarrayList.get(position);
    }

    @Override
    public int getCount() {
        int count = myarrayList.size();
        //return count > 0 ? count - 1 : count;
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        CategoryWeight_ModelClass model = getItem(position);

        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_textview, parent, false);

        TextView label = spinnerRow.findViewById(R.id.spinner_text);
        label.setText(String.format("%s", model != null ? model.getWeight() : ""));


        return spinnerRow;
    }
}
