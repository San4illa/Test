package com.example.test.data.network;

import com.example.test.data.model.Chat;
import com.example.test.data.model.City;
import com.example.test.data.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.test.BuildConfig.AUTHORIZATION_HEADER;

public interface ApiService {

    @Headers(AUTHORIZATION_HEADER)
    @GET("map/cities/hash")
    Call<String> getHash();

    @Headers(AUTHORIZATION_HEADER)
    @GET("map/cities")
    Call<List<City>> getCities(@Query("min_lat") double minLat,
                               @Query("max_lat") double maxLat,
                               @Query("min_lng") double minLng,
                               @Query("max_lng") double maxLng);

    @Headers(AUTHORIZATION_HEADER)
    @PUT("chat/create")
    Call<Chat> createChat();

    @Headers(AUTHORIZATION_HEADER)
    @PUT("chat/send/{id}")
    Call<Message> sendMessage(@Path("id") String id);

    @Headers(AUTHORIZATION_HEADER)
    @GET("chat/messages")
    Call<List<Message>> getMessages();
}


