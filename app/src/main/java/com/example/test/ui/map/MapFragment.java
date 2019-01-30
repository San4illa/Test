package com.example.test.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.data.model.City;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int MAP_VIEW = 0;
    private static final int LIST_VIEW = 1;
    private static final int LOADER_ID = 5;

    private int viewType = MAP_VIEW;

    private GoogleMap map;
    private MapView mapView;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private CityAdapter adapter;

    private ArrayList<City> cities = new ArrayList<>();

    double minLat = 0;
    double maxLat = 0;
    double minLng = 0;
    double maxLng = 0;

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
        fab.setOnClickListener(v -> {
            getMarks();
            startLoading(true);
        });

        startLoading(false);
    }

    private void getMarks() {
        progressBar.setVisibility(View.VISIBLE);

        VisibleRegion visibleRegion = map.getProjection().getVisibleRegion();

        LatLng farRight = visibleRegion.farRight;
        LatLng nearLeft = visibleRegion.nearLeft;

        mapView.getMapAsync(MapFragment.this::onMapReady);

        minLat = nearLeft.latitude;
        maxLat = farRight.latitude;
        minLng = nearLeft.longitude;
        maxLng = farRight.longitude;
    }

    private void startLoading(boolean restart) {
        LoaderManager.LoaderCallbacks<List<City>> callbacks = new CitiesListCallback();
        if (restart) {
            getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, callbacks);
        } else {
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, callbacks);
        }
    }

    private void showCities(List<City> c) {
        cities.clear();

        int max;
        if (c.size() < 250) {
            max = c.size();
        } else {
            Toast.makeText(getContext(), getString(R.string.warning_much_marks), Toast.LENGTH_SHORT).show();
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
                getActivity().setTitle(getString(R.string.action_list));
                viewType = LIST_VIEW;
            } else {
                mapView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

                item.setIcon(R.drawable.ic_action_list);
                item.setTitle(getString(R.string.action_list));
                getActivity().setTitle(getString(R.string.action_map));
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


    private class CitiesListCallback implements LoaderManager.LoaderCallbacks<List<City>> {
        @NonNull
        @Override
        public Loader<List<City>> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new CitiesLoader(getContext(), minLat, maxLat, minLng, maxLng);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<City>> loader, List<City> cities) {
            showCities(cities);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<City>> loader) {

        }
    }
}

