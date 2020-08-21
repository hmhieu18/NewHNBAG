//package com.example.hnbag;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//
//public class showSearch extends AppCompatActivity {
//    private Button search;
////    private GoogleMap mMap;
//    private EditText search_bar;
//    private String query;
//    private Location lastKnownLocation;
//    private ListView _listViewResult;
//    private ArrayList<Root.Results> _resultsList;
//    public ResultArrayAdapter _resultArrayAdapter;
//    private boolean isDone = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_result);
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if (extras == null) {
//                query = null;
//            } else query = extras.getString("query");
//        } else {
//            query = (String) savedInstanceState.getSerializable("query");
//        }
//        assert query != null;
//        Log.d("query", query);
//        initComponent();
//        getLastKnownLocation();
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
////                .findFragmentById(R.id.map);
////        mapFragment.getMapAsync(this);
//    }
//
//    private void initComponent() {
//        search = (Button) findViewById(R.id.search2);
//        search_bar = (EditText) findViewById(R.id.search_bar2);
//        search_bar.setText(query);
//        _resultsList = new ArrayList<>();
//        _listViewResult = findViewById(R.id.listViewResults);
//        _resultArrayAdapter = new ResultArrayAdapter(this, R.layout.place_item, _resultsList);
//        _listViewResult.setAdapter(_resultArrayAdapter);
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            return;
////        }
////        mMap.setMyLocationEnabled(true);
////    }
//
//    private void getLastKnownLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        }
//        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            lastKnownLocation = location;
//                            Log.d("location", Double.valueOf(lastKnownLocation.getLatitude()).toString());
//                        }
//                    }
//                });
//    }
//
//    private String parseURL() throws MalformedURLException {
//        String res = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query +
//                "&location=" +
//                "10.7726045,106.6989436" +
////                lastKnownLocation.getLatitude() + lastKnownLocation.getLongitude() +
//                "&radius=2000" +
//                "&key=AIzaSyDk2Z5H8EiJNIsHWrb7-fXXDnhbPqjIfbI";
//        return res;
//    }
//
//    public void search2Clicked(View view) {
//        new retrieveData().execute();
//    }
//
//    class retrieveData extends AsyncTask<Void, Void, Void> {
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                String request = parseURL();
//                SearchHandling task = new SearchHandling(request);
//                _resultsList = task.search();
//                return null;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            _resultArrayAdapter.notifyDataSetChanged();
//        }
//    }
//}