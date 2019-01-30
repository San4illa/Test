package com.example.test.ui.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;

import java.io.IOException;
import java.util.List;

public class CitiesLoader extends AsyncTaskLoader<List<City>> {
    private List<City> cities;

    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;

    CitiesLoader(@NonNull Context context, double minLat, double maxLat, double minLng, double maxLng) {
        super(context);

        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLng = minLng;
        this.maxLng = maxLng;
    }

    @Override
    protected void onStartLoading() {
        if (cities != null) {
            deliverResult(cities);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(@Nullable List<City> data) {
        cities = data;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public List<City> loadInBackground() {
        try {
            return ApiClient.getClient().create(ApiService.class).getCities(minLat, maxLat, minLng, maxLng).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
