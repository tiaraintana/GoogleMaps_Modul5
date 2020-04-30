package com.example.googlemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    private GoogleMap mMap;
    private ImageButton button1;
    private ImageButton button2;
    private Integer loc;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= findViewById(R.id.button1);
        button2= findViewById(R.id.button2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this); //mendapatkan tiitk lokasi device
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc =0;
                fetchLastLocation(); //menuju fungsi fetch
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = 1;
                fetchLastLocation();
            }
        });

    }

    private void fetchLastLocation() {
        //check permission untuk mendapatkan lokasi saatini
        //mengijinkan aplikasi untuk mengakses lokasi yang tepat dari pengguna
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){ //jika lokasi tidakkosong maka maps akan mengakses dan menampilkan lokasi saat ini
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                            +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true); //zoom in zoom out



        if (loc== 0){
            //menampilkan lokasi saat ini
            LatLng latLng= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("HOME").snippet("Jl. Tangkis, Rt : 01, Rw: 01, Desa Mertani, Kecamatan Karanggeneng");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)); //icon marker untuk lokasi saat ini
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15)); //supaya saat apk dibuka langsung menuju ke tempat yg ditandai
            googleMap.addMarker(markerOptions);

        }
        else {
            LatLng wisata1 = new LatLng(-6.865370, 112.359927); //menentukan koordinat secara manual
            googleMap.addMarker(new MarkerOptions().position(wisata1).title("Wisata Bahari Lamongan").snippet("Jl. Raya Paciran, Paciran, Kec. Paciran, Kabupaten Lamongan, Jawa Timur 62264\n" +
                    "\n"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(wisata1));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wisata1,15));

            LatLng wisata2 = new LatLng(-7.209081, 112.268290);
            googleMap.addMarker(new MarkerOptions().position(wisata2).title("Waduk Gondang").snippet("Waduk Gondang, Gondang Lor, Sugio, Kabupaten Lamongan, Jawa Timur" +
                    "\n"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(wisata2));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wisata2,15));

            LatLng wisata3 = new LatLng(-6.873571, 112.295609);
            googleMap.addMarker(new MarkerOptions().position(wisata3).title("Monumen Kapal Van Der Wijck").snippet("Brondong, Kec. Brondong, Kabupaten Lamongan, Jawa Timur 62263"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(wisata3));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wisata3,15));

            LatLng wisata4 = new LatLng(-7.120333, 112.415279);
            googleMap.addMarker(new MarkerOptions().position(wisata4).title("Alun Alun Kota Lamongan").snippet("Tumenggungan, Kec. Lamongan, Kabupaten Lamongan, Jawa Timur 62214"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(wisata4));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wisata4,15));

            LatLng wisata5 = new LatLng(-6.885896, 112.196614);
            googleMap.addMarker(new MarkerOptions().position(wisata5).title("Pantai Kutang").snippet("Kentong, Labuhan, Kec. Brondong, Kabupaten Lamongan, Jawa Timur 62263"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(wisata5));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wisata5,15));


        }


    }
    //permisiion granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}