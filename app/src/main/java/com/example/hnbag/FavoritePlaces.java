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
import android.os.SystemClock;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FavoritePlaces extends FragmentActivity implements OnMapReadyCallback {
    private Button search;
    private GoogleMap mMap;
    private EditText search_bar;
    private String query;
    private Location lastKnownLocation;
    private ListView _listViewResult;
    public ResultArrayAdapter _resultArrayAdapter;
    private boolean isDone = false;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();
    private String FAVORITE_FILE = MainActivity.profile.get_id();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        new updateFromDatabase().execute();
        SystemClock.sleep(1000);
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
            MainActivity.profile.favoritePlaces = new Gson().fromJson(json, listType);
            Log.d("size", Integer.valueOf(MainActivity.profile.favoritePlaces.size()).toString());
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
        if (MainActivity.profile.favoritePlaces.size() != 0) {
            _resultArrayAdapter = new ResultArrayAdapter(FavoritePlaces.this, R.layout.place_item, MainActivity.profile.favoritePlaces);
            _listViewResult.setAdapter(_resultArrayAdapter);
        }
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
        for (Root.Results i : MainActivity.profile.favoritePlaces) {
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
        if (!MainActivity.profile.favoritePlaces.isEmpty()) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(MainActivity.profile.favoritePlaces.get(i).getGeometry().getLocation().getLatLng())     // Sets the center of the map to Mountain View
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
                                    MainActivity.profile.favoritePlaces.get(i).getGeometry().getLocation().getLat() + ',' + MainActivity.profile.favoritePlaces.get(i).getGeometry().getLocation().getLng()));
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
        String json = gson.toJson(MainActivity.profile.favoritePlaces);
        JsonHandling.writeToFile(json, context, FAVORITE_FILE);
    }

    class updateFromDatabase extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/users/" +
                        MainActivity.profile.get_id();
                URL obj = null;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");
                con.setRequestProperty("X-Appery-Session-Token", MainActivity.profile.getSessionToken());

                int responseCode = con.getResponseCode();
                Log.d("Response Code : ", Integer.valueOf(responseCode).toString());
                BufferedReader iny = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = iny.readLine()) != null) {
                    response.append(output);
                }
                iny.close();
                //printing result from response
                Log.d("response", response.toString());
                MainActivity.profile=(new Gson()).fromJson(response.toString(), Profile.class);
                Type listType = new TypeToken<ArrayList<Root.Results>>() {
                }.getType();
                MainActivity.profile.favoritePlaces = new Gson().fromJson(JsonHandling.base64Decode(MainActivity.profile.json_favorite_places), listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}