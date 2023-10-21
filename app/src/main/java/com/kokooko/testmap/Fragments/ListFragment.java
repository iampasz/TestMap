package com.kokooko.testmap.Fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kokooko.testmap.Adapters.MyAdapter;
import com.kokooko.testmap.API.ApiService;
import com.kokooko.testmap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFragment extends Fragment {

    RecyclerView rv;
    public ListFragment() {
        super(R.layout.list_fragment);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getList();
    }

    public void getList() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ap.sportforall.gov.ua/api/v1/") // Базовый URL API
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Object> call = apiService.getDictionaries();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    String myJson = new Gson().toJson(response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(myJson);
                        Iterator<String> keys = jsonObject.keys();
                        ArrayList<String> myList = new ArrayList<>();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            myList.add(key);
                        }
                        MyAdapter myAdapter = new MyAdapter(myList);
                        rv.setAdapter(myAdapter);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}