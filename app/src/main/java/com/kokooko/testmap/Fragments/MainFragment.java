package com.kokooko.testmap.Fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kokooko.testmap.R;

public class MainFragment extends Fragment {
    public MainFragment() {
        super(R.layout.main_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button map_bt = view.findViewById(R.id.map_bt);
        Button list_bt = view.findViewById(R.id.list_bt);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new MapFragment())
                .commit();

        map_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new MapFragment())
                        .commit();
            }
        });

        list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, new ListFragment())
                        .commit();
            }
        });

    }

}