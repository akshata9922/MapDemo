package com.example.felix_its.mapdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_PERMISSION_LOCATION=1;
    //1.Declare Loaction client
    private FusedLocationProviderClient mFuseLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //2.Initilize location client
        mFuseLocationClient= LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
        }
        else
        {
            getMyLocation();
            Toast.makeText(this,"Permission is allowed",Toast.LENGTH_SHORT).show();
        }

        // Add a marker in Sydney and move the camera
        LatLng vimanagar = new LatLng(18.566526, 73.912239);
        LatLng kalyaninagar=new LatLng(18.5463,73.9033);
        LatLng baner=new LatLng(18.5590,73.7868);
        LatLng yervada=new LatLng(18.5529,73.8796);

        MarkerOptions markerOptionsVimanagar =new MarkerOptions().position(vimanagar).title("Marker in Vimanagar");
        MarkerOptions markerOptionsKalyaninagar =new MarkerOptions().position(kalyaninagar).title("Marker in kalyaninagar");
        MarkerOptions markerOptionsBaner =new MarkerOptions().position(baner).title("Marker in Banar");
        MarkerOptions markerOptionsYervda=new MarkerOptions().position(yervada).title("Marker in Yervada");
        mMap.addMarker(markerOptionsVimanagar);
        mMap.addMarker(markerOptionsKalyaninagar);
        mMap.addMarker(markerOptionsBaner);
        mMap.addMarker(markerOptionsYervda);

        //circle
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vimanagar, 15.0f));
        CircleOptions circleOptions=new CircleOptions().center(vimanagar).radius(1000).strokeColor(Color.BLACK).strokeWidth(3).fillColor(Color.BLUE);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addCircle(circleOptions);

        //polyline option
        PolylineOptions polylineOptions=new PolylineOptions();
        polylineOptions.add(vimanagar);
        polylineOptions.add(kalyaninagar);
        polylineOptions.add(baner);
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        mMap.addPolyline(polylineOptions);

        //polygon
        PolygonOptions polygonOptions=new PolygonOptions();
        polygonOptions.add(vimanagar);
        polygonOptions.add(kalyaninagar);
        polygonOptions.add(yervada);
        polygonOptions.add(baner);
        polygonOptions.add(vimanagar);
        polygonOptions.fillColor(Color.BLACK);
        mMap.addPolygon(polygonOptions);
        }

    @SuppressLint("MissingPermission")
    private void getMyLocation() {
      mFuseLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(Location location) {
              if(location!=null)
              {
                  LatLng myLocation=new LatLng(location.getLatitude(),location.getLongitude());
                  MarkerOptions markerOptions=new MarkerOptions().position(myLocation).title("My Location");
                  mMap.addMarker(markerOptions);
                  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.0f));
              }
          }
      });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMISSION_LOCATION)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                getMyLocation();
            }
            else {
                Toast.makeText(this,"This Permission is mandatory",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
            }
        }
    }
}
