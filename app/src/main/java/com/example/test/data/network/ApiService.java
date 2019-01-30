package com.example.test.data.network;

import com.example.test.data.model.City;
import com.example.test.data.model.Message;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @GET("map/cities/hash")
    Observable<String> getHash();

    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @GET("map/cities")
    Call<List<City>> getCities(@Query("min_lat") double minLat,
                               @Query("max_lat") double maxLat,
                               @Query("min_lng") double minLng,
                               @Query("max_lng") double maxLng);

    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @GET("map/cities")
    Observable<List<City>> getCitiesRx(@Query("min_lat") double minLat,
                                     @Query("max_lat") double maxLat,
                                     @Query("min_lng") double minLng,
                                     @Query("max_lng") double maxLng);


    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @GET("chat/create")
    Call<String> createChat();

    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @PUT("chat/send/{id}")
    Call<Message> sendMessage(@Path("id") String id);

    @Headers("Authorization:1834718703151e8fb77ccb6d4463cb1d")
    @GET("chat/messages")
    Call<List<Message>> getMessages();
}


