package com.example.test.ui.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.test.R;
import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    private ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Test data
        cities.add(new City("Japan", "Tokyo", 35.6850, 139.7514));
        cities.add(new City("United States","New York", 40.6943, -73.9249));
        cities.add(new City("Mexico","Mexico City",  19.4424, -99.1310));
        cities.add(new City("India","Mumbai",  19.0170, 72.8570));
        cities.add(new City("Brazil","São Paulo",  -23.5587, -46.6250));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Call<List<City>> call = ApiClient.getClient().create(ApiService.class).getCities();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.body() != null){
                    cities.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for (City city : cities) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(city.getLat(), city.getLng()))
                    .title(city.getName()));
        }
    }
}
