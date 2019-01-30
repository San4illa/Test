package com.example.test.ui.map;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.data.model.City;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterViewHolder> {

    private ArrayList<City> cities;

    CityAdapter(ArrayList<City> cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new CityAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapterViewHolder holder, int position) {
        City city = cities.get(position);

//        Locale loc = new Locale("", city.getCountry());
//        holder.countryTextView.setText(loc.getDisplayCountry());

        holder.countryTextView.setText(city.getCountry());
        holder.nameTextView.setText(city.getName());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView countryTextView;
        TextView nameTextView;

        CityAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            countryTextView = itemView.findViewById(R.id.tv_country);
            nameTextView = itemView.findViewById(R.id.tv_name);
        }
    }
}
