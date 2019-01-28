package com.example.test.data.network;

import com.example.test.data.model.City;
import com.example.test.data.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("map/cities")
    Call<List<City>> getCities();

    @GET("chat/create")
    Call<String> createChat();

    @PUT("chat/send/{id}")
    Call<Message> sendMessage(@Path("id") String id);

    @GET("chat/messages")
    Call<List<Message>> getMessages();
}
