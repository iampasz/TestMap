package com.kokooko.testmap.API;



import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("dictionaries")
     Call<Object> getDictionaries();
}