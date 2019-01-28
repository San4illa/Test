package com.example.test.ui.marks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.test.R;
import com.example.test.data.model.City;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private CityAdapter adapter;
    private ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        // test data
        cities.add(new City("RU", "Saint Petersburg", 59.9402, 30.3138));
        cities.add(new City("NF", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("PL", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("RO", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("RU", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("AZ", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("KM", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("GS", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("IR", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("GM", "Saint Petersburg", 222.222, 22.2121));
        cities.add(new City("GF", "Saint Petersburg", 222.222, 22.2121));

        recyclerView = findViewById(R.id.rv_marks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CityAdapter(cities);
        recyclerView.setAdapter(adapter);

        initSwipeToDelete();

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

    // +
    private void initSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);
                cities.remove(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
