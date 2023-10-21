package com.kokooko.testmap.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.kokooko.testmap.R;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polyline;
import java.util.ArrayList;


public class MapFragment extends Fragment {

    MapView map;
    GeoPoint startPoint;
    GeoPoint endPoint;
    Marker startMarker;
    Marker endMarker;
    Polyline roadOverlay;
    RoadManager roadManager;

    public MapFragment() {
        super(R.layout.map_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Context ctx = getContext().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = view.findViewById(R.id.myMap);

        roadManager = new OSRMRoadManager(getContext(), "MY_USER_AGENT");

        Button setStart = view.findViewById(R.id.setStart);
        Button setEnd = view.findViewById(R.id.setEnd);
        EditText editStartX = view.findViewById(R.id.editStartX);
        EditText editStartY = view.findViewById(R.id.editStartY);
        EditText editEndX = view.findViewById(R.id.editEmdX);
        EditText editEndY = view.findViewById(R.id.editEmdY);

        setStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double x = Double.parseDouble(editStartX.getText().toString());
                double y = Double.parseDouble(editStartY.getText().toString());

                if (x < -180) {
                    x = -180;
                }

                if (x > 180) {
                    x = 180;
                }

                if (y < -180) {
                    y = -180;
                }

                if (y > 180) {
                    y = 180;
                }

                editStartX.setText(x + "");
                editStartY.setText(y + "");
                setStartPoint(x, y);

                //setStartPoint(34.8724, 32.4696);

                //  AddThispoint(, 35, 33);
            }
        });

        setEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double x = Double.parseDouble(editEndX.getText().toString());
                double y = Double.parseDouble(editEndY.getText().toString());

                if (x < -180) {
                    x = -180;
                }

                if (x > 180) {
                    x = 180;
                }

                if (y < -180) {
                    y = -180;
                }

                if (y > 180) {
                    y = 180;
                }

                editEndX.setText(x + "");
                editEndY.setText(y + "");
                setEndPoint(x, y);

            }
        });

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        MinimapOverlay mMinimapOverlay = new MinimapOverlay(getContext(), map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(8.5);
        GeoPoint startPoint = new GeoPoint(35.0524, 33.1846);
        mapController.setCenter(startPoint);

        showRout();

    }


    private void setStartPoint(double x, double y) {

        if (startMarker != null) {
            map.getOverlays().remove(startMarker);
        }

        if (startPoint != null) {
            map.getOverlays().remove(startPoint);

        }

        startPoint = new GeoPoint(x, y);

        IMapController mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(startPoint);

        startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_start_point));
        startMarker.setTitle("Start");


        showRout();
    }

    private void setEndPoint(double x, double y) {

        if (endMarker != null) {
            map.getOverlays().remove(endMarker);
        }

        if (endPoint != null) {
            map.getOverlays().remove(endPoint);

        }

        endPoint = new GeoPoint(x, y);

        IMapController mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(endPoint);

        endMarker = new Marker(map);
        endMarker.setPosition(endPoint);
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(endMarker);
        endMarker.setIcon(getResources().getDrawable(R.drawable.ic_finish));
        endMarker.setTitle("End");


        showRout();
    }

    private void showRout() {

        if (roadOverlay != null) {
            map.getOverlays().remove(roadOverlay);
        }

        if (startPoint == null) {
            startPoint = new GeoPoint(34.8724, 32.4696);
        }

        if (endPoint == null) {
            endPoint = new GeoPoint(35.352636, 33.857259);
        }

        if (startMarker == null) {
            startMarker = new Marker(map);
            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(startMarker);
            startMarker.setIcon(getResources().getDrawable(R.drawable.ic_start_point));
            startMarker.setTitle("Start");
        }

        if (endMarker == null) {
            endMarker = new Marker(map);
            endMarker.setPosition(endPoint);
            endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(endMarker);
            endMarker.setIcon(getResources().getDrawable(R.drawable.ic_finish));
            endMarker.setTitle("END");
        }


        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        waypoints.add(endPoint);

        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {


                Road road = roadManager.getRoad(waypoints);
                roadOverlay = RoadManager.buildRoadOverlay(road);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        map.getOverlays().add(roadOverlay);
                        map.invalidate();

                    }
                });
            }
        });

        backgroundThread.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
}