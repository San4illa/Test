package com.example.test.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;

    private ArrayList<City> cities = new ArrayList<>();

    LatLng farLeft = new LatLng(0, 0);
    LatLng farRight = new LatLng(0, 0);
    LatLng nearLeft = new LatLng(0, 0);
    LatLng nearRight = new LatLng(0, 0);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            VisibleRegion visibleRegion = map.getProjection().getVisibleRegion();

//            LatLng farLeft = visibleRegion.farLeft;
//            LatLng farRight = visibleRegion.farRight;
//            LatLng nearLeft = visibleRegion.nearLeft;
//            LatLng nearRight = visibleRegion.nearRight;

            farLeft = visibleRegion.farLeft;
            farRight = visibleRegion.farRight;
            nearLeft = visibleRegion.nearLeft;
            nearRight = visibleRegion.nearRight;

            mapView.getMapAsync(MapFragment.this::onMapReady);

            double minLat = nearLeft.latitude;
            double maxLat = farRight.latitude;
            double minLng = nearLeft.longitude;
            double maxLng = farRight.longitude;

            ApiClient.getClient().create(ApiService.class).getCitiesRx(minLat, maxLat, minLng, maxLng)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new Observer<List<City>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "onSubscribe");
                        }

                        @Override
                        public void onNext(List<City> c) {
                            Log.d(TAG, "onNext: " + c.size());

                            cities.clear();

                            int max;
                            if (c.size() < 250) {
                                max = c.size();
                            } else {
                                max = 250;
                            }

                            for (int i = 0; i < max; i++) {
                                cities.add(c.get(i));
                            }

                            mapView.getMapAsync(MapFragment.this::onMapReady);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError " + e);
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete");
                        }
                    });

//            Call<List<City>> call = ApiClient.getClient().create(ApiService.class).getCities(minLat, maxLat, minLng, maxLng);
//            call.enqueue(new Callback<List<City>>() {
//                @Override
//                public void onResponse(Call<List<City>> call, Response<List<City>> response) {
//                    Log.d(TAG, "onResponse: " + response.body().size());
//                    if (response.body() != null) {
//                        cities.clear();
//                        cities.addAll(response.body());
//
//                        mapView.getMapAsync(MapFragment.this::onMapReady);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<City>> call, Throwable t) {
//                    Log.d(TAG, "onFailure: ");
//                }
//            });
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.clear();

        map.addMarker(new MarkerOptions()
                .position(farLeft))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(new MarkerOptions()
                .position(farRight))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(new MarkerOptions()
                .position(nearLeft))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(new MarkerOptions()
                .position(nearRight))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        for (City city : cities) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(city.getLat(), city.getLng()))
                    .title(city.getCountry() + ": " + city.getName()));
        }
    }
}
