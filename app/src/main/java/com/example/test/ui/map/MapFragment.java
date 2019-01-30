package com.example.test.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.test.R;
import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int MAP_VIEW = 0;
    private static final int LIST_VIEW = 1;

    private int viewType = MAP_VIEW;

    private GoogleMap map;
    private MapView mapView;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private CityAdapter adapter;

    private ArrayList<City> cities = new ArrayList<>();

    private LatLng farLeft = new LatLng(0, 0);
    private LatLng farRight = new LatLng(0, 0);
    private LatLng nearLeft = new LatLng(0, 0);
    private LatLng nearRight = new LatLng(0, 0);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        recyclerView = view.findViewById(R.id.rv_marks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CityAdapter(cities);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.pb);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> getMarks());
    }

    private void getMarks() {
        progressBar.setVisibility(View.VISIBLE);

        VisibleRegion visibleRegion = map.getProjection().getVisibleRegion();

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
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.INVISIBLE);
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_switch_view) {
            if (viewType == MAP_VIEW) {
                mapView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);

                item.setIcon(R.drawable.ic_action_map);
                item.setTitle(getString(R.string.action_map));
                viewType = LIST_VIEW;
            } else {
                mapView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

                item.setIcon(R.drawable.ic_action_list);
                item.setTitle(getString(R.string.action_list));
                viewType = MAP_VIEW;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.clear();

        for (City city : cities) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(city.getLat(), city.getLng()))
                    .title(city.getCountry() + ": " + city.getName()));
        }
    }
}

