package com.example.hnbag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class FavoritePlaces extends FragmentActivity implements OnMapReadyCallback {
    private Button search;
    private GoogleMap mMap;
    private EditText search_bar;
    private String query;
    private Location lastKnownLocation;
    private ListView _listViewResult;
    private ArrayList<Root.Results> favoritePlaces;
    public ResultArrayAdapter _resultArrayAdapter;
    private boolean isDone = false;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();
    private String FAVORITE_FILE = "favorite.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        loadJson();
        initComponent();
        getLastKnownLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadJson() {
        Type listType = new TypeToken<ArrayList<Root.Results>>() {
        }.getType();
        Gson gson = new Gson();
        try {
            String json = JsonHandling.readString(JsonHandling.readJsonFromStorage(this, FAVORITE_FILE));
            Log.d("json", json);
            favoritePlaces = new Gson().fromJson(json, listType);
            Log.d("size", Integer.valueOf(favoritePlaces.size()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponent() {
        search = (Button) findViewById(R.id.search2);
        search_bar = (EditText) findViewById(R.id.search_bar2);
        _listViewResult = findViewById(R.id.listViewResults);
        _listViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                moveCameraToMarker(position);
                changeIconMarker(position);
            }
        });
        _listViewResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                optionPlace(FavoritePlaces.this, i);
                return true;
            }
        });
        _resultArrayAdapter = new ResultArrayAdapter(FavoritePlaces.this, R.layout.place_item, favoritePlaces);
        _listViewResult.setAdapter(_resultArrayAdapter);
    }

    private void changeIconMarker(int position) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        displayMarkers();
    }

    private void displayMarkers() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.doan1);
        bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bmp);
        for (Root.Results i : favoritePlaces) {
            Marker temp = mMap.addMarker(new MarkerOptions()
                    .position(i.getGeometry().getLocation().getLatLng())
                    .title(i.getName())
                    .icon(bitmapDescriptor)
            );
            markerArrayList.add(temp);
        }
        moveCameraToMarker(0);
    }

    private void moveCameraToMarker(int i) {
        if (!favoritePlaces.isEmpty()) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(favoritePlaces.get(i).getGeometry().getLocation().getLatLng())     // Sets the center of the map to Mountain View
                    .zoom(15)                           // Sets the zoom
                    .bearing(90)                        // Sets the orientation of the camera to east
                    .tilt(30)                           // Sets the tilt of the camera to 30 degrees
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lastKnownLocation = location;
                            Log.d("location", Double.valueOf(lastKnownLocation.getLatitude()).toString());
                        }
                    }
                });
    }

    private String parseURL() throws MalformedURLException {
        String res = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query +
                "&location=" +
                "10.7726045,106.6989436" +
//                lastKnownLocation.getLatitude() + lastKnownLocation.getLongitude() +
                "&radius=2000" +
                "&key=AIzaSyDk2Z5H8EiJNIsHWrb7-fXXDnhbPqjIfbI";
        return res;
    }

    public void search2Clicked(View view) {
        query = String.valueOf(search_bar.getText());
    }

    private void optionPlace(final Context context, final int i) {
        final CharSequence[] options = {"Add to favorite", "Direction", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Add to favorite")) {
//                    addPlaceToFavorite(context, i);

                } else if (options[item].equals("Direction")) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" +
                                    "10.7726045,106.6989436" +
//                lastKnownLocation.getLatitude() + lastKnownLocation.getLongitude() +
                                    "&daddr=" +
                                    favoritePlaces.get(i).getGeometry().getLocation().getLat() + ',' + favoritePlaces.get(i).getGeometry().getLocation().getLng()));
                    startActivity(intent);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void saveFavoriteToFile(Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(favoritePlaces);
        JsonHandling.writeToFile(json, context, FAVORITE_FILE);
    }

}