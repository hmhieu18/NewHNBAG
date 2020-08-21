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
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class searchResult extends FragmentActivity implements OnMapReadyCallback {
    private Button search;
    private GoogleMap mMap;
    private EditText search_bar;
    private String query;
    private Location lastKnownLocation;
    private ListView _listViewResult;
    private ArrayList<Root.Results> _resultsList;
    private ArrayList<Root.Results> favoritePlaces;
    public ResultArrayAdapter _resultArrayAdapter;
    private boolean isDone = false;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();
    private String FAVORITE_FILE = "favorite.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                query = null;
            } else query = extras.getString("query");
        } else {
            query = (String) savedInstanceState.getSerializable("query");
        }
        assert query != null;
        Log.d("query", query);
        initComponent();
        getLastKnownLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (!query.equals("")) new retrieveData().execute();
    }

    private void initComponent() {
        search = (Button) findViewById(R.id.search2);
        search_bar = (EditText) findViewById(R.id.search_bar2);
        search_bar.setText(query);
        _resultsList = new ArrayList<>();
        favoritePlaces = new ArrayList<>();
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
                optionPlace(searchResult.this, i);
                return true;
            }
        });
    }

    private void changeIconMarker(int position) {

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void displayMarkers() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.doan1);
        bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bmp);
        for (Root.Results i : _resultsList) {
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(_resultsList.get(i).getGeometry().getLocation().getLatLng())     // Sets the center of the map to Mountain View
                .zoom(15)                           // Sets the zoom
                .bearing(90)                        // Sets the orientation of the camera to east
                .tilt(30)                           // Sets the tilt of the camera to 30 degrees
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    public void btn_direct_onclick(View view) {
        (new retrieveData()).execute();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()))     // Sets the center of the map to Mountain View
                .zoom(15)                           // Sets the zoom
                .bearing(90)                        // Sets the orientation of the camera to east
                .tilt(30)                           // Sets the tilt of the camera to 30 degrees
                .build()));
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
        new retrieveData().execute();
    }

    class retrieveData extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String request = parseURL();
                SearchHandling task = new SearchHandling(request);
                _resultsList.addAll(task.search());
                Log.d("res", Integer.valueOf(_resultsList.size()).toString());
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            _resultArrayAdapter = new ResultArrayAdapter(searchResult.this, R.layout.place_item, _resultsList);
            _listViewResult.setAdapter(_resultArrayAdapter);
            Log.d("res", Integer.valueOf(_resultsList.size()).toString());
            Log.d("res", Integer.valueOf(_resultArrayAdapter.getCount()).toString());
            displayMarkers();
        }
    }

    private void optionPlace(final Context context, final int i) {
        final CharSequence[] options = {"Add to favorite", "Direction", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Add to favorite")) {
                    addPlaceToFavorite(context, i);

                } else if (options[item].equals("Direction")) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" +
                                    "10.7726045,106.6989436" +
//                lastKnownLocation.getLatitude() + lastKnownLocation.getLongitude() +
                                    "&daddr=" +
                                    _resultsList.get(i).getGeometry().getLocation().getLat() + ',' + _resultsList.get(i).getGeometry().getLocation().getLng()));
                    startActivity(intent);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void addPlaceToFavorite(Context context, int position) {
        favoritePlaces.add(_resultsList.get(position));
        saveFavoriteToFile(context);
    }

    private void saveFavoriteToFile(Context context) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(favoritePlaces);
        JsonHandling.writeToFile(json, context, FAVORITE_FILE);
    }

}