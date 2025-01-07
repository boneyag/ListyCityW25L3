package com.example.listycityw25l3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityArrayAdapter extends ArrayAdapter<City> {

    private ArrayList<City> cities;
    private Context context;
    public CityArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<City> cities) {
        super(context, resource, cities);
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.array_list_content, parent, false);
        }

        City city = cities.get(position);

        TextView cityName = view.findViewById(R.id.city_text_view);
        TextView provinceName = view.findViewById(R.id.province_text_view);

        cityName.setText(city.getCity());
        provinceName.setText(city.getProvince());

        return view;
    }
}
