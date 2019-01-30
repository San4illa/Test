package com.example.test.ui.map;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;

import java.util.List;

import io.reactivex.Single;

public class CitiesLoaderRx extends AsyncTaskLoader<Single<List<City>>> {
    private Single<List<City>> single;

    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;

    public CitiesLoaderRx(@NonNull Context context, double minLat, double maxLat, double minLng, double maxLng) {
        super(context);

        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLng = minLng;
        this.maxLng = maxLng;
    }

    @Override
    protected void onStartLoading() {
        if (single != null) {
            deliverResult(single);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(@Nullable Single<List<City>> data) {
        single = data;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public Single<List<City>> loadInBackground() {
        return ApiClient.getClient().create(ApiService.class).getCitiesRx(minLat, maxLat, minLng, maxLng);
    }
}
