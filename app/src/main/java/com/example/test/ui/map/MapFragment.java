package com.example.test.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.data.model.City;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;

    private ArrayList<City> cities = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Test data
        cities.add(new City("Japan", "Tokyo", 35.6850, 139.7514));
        cities.add(new City("United States", "New York", 40.6943, -73.9249));
        cities.add(new City("Mexico", "Mexico City", 19.4424, -99.1310));
        cities.add(new City("India", "Mumbai", 19.0170, 72.8570));
        cities.add(new City("Brazil", "São Paulo", -23.5587, -46.6250));

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (City city : cities) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(city.getLat(), city.getLng()))
                    .title(city.getName()));
        }
    }
}
