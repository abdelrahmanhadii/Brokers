package com.example.quantum.brokers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class moreinfo extends AppCompatActivity implements OnMapReadyCallback{


    realStateClass newObject;

    ImageView img;
    TextView name;
    TextView mobile;
    TextView address;
    TextView price;

    String MAP_FRAGMENT = "map_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
                .findFragmentByTag(MAP_FRAGMENT);

        if(mapFragment == null){
            mapFragment = SupportMapFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.mapLayout, mapFragment, MAP_FRAGMENT)
                    .commit();
        }
        mapFragment.getMapAsync(this);

        img = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.mobile);
        address = (TextView) findViewById(R.id.address);
        price = (TextView) findViewById(R.id.price);


        newObject = (realStateClass) getIntent().getSerializableExtra("key");

        Picasso.with(moreinfo.this).load(newObject.getImgHolder()).into(img);
        name.setText(newObject.getNameHolder());
        mobile.setText(newObject.getMobileHolder());
        address.setText(newObject.getAddressHolder());
        price.setText(newObject.getPriceHolder());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng locationMarker = new LatLng(Double.parseDouble(newObject.getLatitudeHolder()), Double.parseDouble(newObject.getLatitudeHolder()));
        googleMap.addMarker(new MarkerOptions().position(locationMarker));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationMarker));
    }
}
