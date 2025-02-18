package com.example.listycityw25l3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CityDialogFragment.OnFragmentInteractionListener {
    private ListView cityList;
    private FloatingActionButton addCityButton;
    private FloatingActionButton deleteCityButton;
    private ArrayList<City> cityDataList;
    private ArrayAdapter<City> cityArrayAdapter;
    private int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list_view);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);
        deleteCityButton.setVisibility(View.INVISIBLE);

        String cities[] = {"Edmonton", "Calgary", "Vancouver", "Toronto", "Montreal", "Saskatoon", "Victoria", "Ottawa", "Quebec City", "London", "Kingston"};
        String provinces[] = {"AB", "AB", "BC", "ON", "QC", "SK", "BC", "ON", "QC", "ON", "ON"};

        cityDataList = new ArrayList<>();

        for (int i=0; i<cities.length; i++){
            cityDataList.add(new City(cities[i], provinces[i]));
        }

        cityArrayAdapter = new CityArrayAdapter(this, R.layout.array_list_content, cityDataList);
        cityList.setAdapter(cityArrayAdapter);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CityDialogFragment().show(getSupportFragmentManager(), "ADD_CITY");
            }
        });

        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                City currentCity = cityDataList.get(position);
                CityDialogFragment.newInstance(currentCity, position).show(getSupportFragmentManager(), "EDIT_CITY");
                return false;
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == selectedPosition){
                    deleteCityButton.setVisibility(View.INVISIBLE);
                    selectedPosition = -1;
                    cityList.clearChoices();
                    cityList.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                } else {
                    if (position != -1){
                        deleteCityButton.setVisibility(View.VISIBLE);
                    }
                    if (selectedPosition != -1){
                        cityList.getChildAt(selectedPosition).setBackgroundColor(Color.TRANSPARENT);
                    }
                    cityList.getChildAt(position).setBackgroundColor(Color.GRAY);
                    selectedPosition = position;
                }
            }
        });

        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEL_BTN", "del button");
                deleteCity(selectedPosition);
                deleteCityButton.setVisibility(View.INVISIBLE);
                cityList.getChildAt(selectedPosition).setBackgroundColor(Color.TRANSPARENT);
                selectedPosition = -1;
            }
        });
    }

    @Override
    public void onOkPressListener(City newCity) {
        cityDataList.add(newCity);
        cityArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditPressListener(City newCity, int position) {
        City oldCity = cityDataList.get(position);
        oldCity.setCity(newCity.getCity());
        oldCity.setProvince(newCity.getProvince());
        cityArrayAdapter.notifyDataSetChanged();
    }

    public void deleteCity(int position){
        cityDataList.remove(position);
        cityArrayAdapter.notifyDataSetChanged();
    }
}