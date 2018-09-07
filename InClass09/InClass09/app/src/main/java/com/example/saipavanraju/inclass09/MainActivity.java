package com.example.saipavanraju.inclass09;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {


    private GoogleMap mMap;
    Gson gson=new Gson();


    ArrayList<Trips.latlong> marks;

    List<LatLng> points=new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();

        InputStream inputStream = this.getResources().openRawResource(R.raw.trip);
        String jsonString = readJsonFile(inputStream);


        Trips trips = gson.fromJson(jsonString, Trips.class);

        marks = trips.getData();




        for (int i = 0 ; i < marks.size(); i++){
            points.add(new LatLng(Double.valueOf(marks.get(i).latitude)  , Double.valueOf(marks.get(i).longitude)));
        };

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    GoogleMap map;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        Log.d("demo", "onMapReady: "+ points.get(0).toString());
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(points).width(10)
                .color(Color.BLUE));

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 10));





        map.setOnPolylineClickListener(this);
        map.setOnPolygonClickListener(this);

        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (int i = 0 ; i < points.size(); i++){
                    builder.include(points.get(i));
                };

                LatLngBounds tmpBounds = builder.build();



                map.setLatLngBoundsForCameraTarget(builder.build());
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                map.setPadding(50,0,50,0);
                map.addMarker(new MarkerOptions().position(new LatLng(points.get(0).latitude,points.get(0).longitude)).title("Start Location"));
                map.addMarker(new MarkerOptions().position(new LatLng(points.get(points.size()-1).latitude,points.get(points.size()-1).longitude)).title("End Location"));

            }
        });
    }
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }



}
