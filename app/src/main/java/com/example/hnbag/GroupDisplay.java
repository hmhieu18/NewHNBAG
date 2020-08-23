package com.example.hnbag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupDisplay extends AppCompatActivity {
    private ListView _listViewGroup;
    public ListGroupArrayAdapter _groupArrayAdapter;
    private Button newGroup;
    private String dialogInput;
    private int _position;
    private boolean foodTrue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_display);
        loadGroups();
        initComponent();
    }

    private void loadGroups() {
    }

    private void initComponent() {
        newGroup = findViewById(R.id.newgroup);
        _listViewGroup = findViewById(R.id.listviewGroups);
        _listViewGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _position = position;
                optionMenu(GroupDisplay.this);
            }
        });
        _groupArrayAdapter = new ListGroupArrayAdapter(GroupDisplay.this, R.layout.place_item, MainActivity.mProfile.listGroup);
        _listViewGroup.setAdapter(_groupArrayAdapter);
    }

    public void newGroupClicked(View view) {
        dialogInput = "";
        String groupName;
        showTextDialog("Insert group name", "Create group", false);
    }

    private void showTextDialog(String title, String positive, final boolean isAddUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogInput = input.getText().toString();
                if (dialogInput != "" && !isAddUser) {
                    MainActivity.mProfile.listGroup.add(new Group(new String[]{MainActivity.mProfile.username}, dialogInput));
                    _groupArrayAdapter.notifyDataSetChanged();
                }
                if (dialogInput != "" && isAddUser) {
                    MainActivity.mProfile.listGroup.get(_position).addUser(dialogInput);
                    _groupArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void optionMenu(Context context) {
        final CharSequence[] options = {"Common Favorite Foods", "Common Favorite Places", "Add new user to group"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Menu");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Common Favorite Foods")) {
                    foodTrue = true;
                    (new findCommon()).execute();
                } else if (options[item].equals("Common Favorite Places")) {
                    foodTrue = false;
                    (new findCommon()).execute();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                } else if (options[item].equals("Add new user to group")) {
                    showTextDialog("Add new user", "Add", true);
                }
            }
        });
        builder.show();
    }

    class findCommon extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String myURL = "https://api.appery.io/rest/1/db/users";
                URL obj = null;
                obj = new URL(myURL);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");

                con.setRequestProperty("X-Appery-Master-Key", "0e64e762-3a79-49e0-a8f2-d03775ee7dfd");
                con.setRequestProperty("X-Appery-Database-Id", "5f3fd5f62e22d76ab9836f0a");

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
                Log.d("response @@@", response.toString());
                ArrayList<mProfile> temp = (new Gson()).fromJson(response.toString(), new TypeToken<ArrayList<mProfile>>() {
                }.getType());
                Log.d("temp", Integer.valueOf(temp.size()).toString());
                MainActivity.mProfile.listGroup.get(_position).commonFavoritePlaces = (new Gson()).fromJson(JsonHandling.base64Decode(MainActivity.mProfile.json_favorite_places), new TypeToken<ArrayList<Results>>() {
                }.getType());
                MainActivity.mProfile.listGroup.get(_position).commonFavoriteFoods = (new Gson()).fromJson(JsonHandling.base64Decode(MainActivity.mProfile.json_favorite_foods), new TypeToken<ArrayList<Food>>() {
                }.getType());
                for (mProfile r : temp) {
                    if (Arrays.asList(MainActivity.mProfile.listGroup.get(_position).getUsernames()).contains(r.getUsername())) {
                        r.favoritePlaces = (new Gson()).fromJson(JsonHandling.base64Decode(r.json_favorite_places), new TypeToken<ArrayList<Results>>() {
                        }.getType());
                        r.favoriteFoods = (new Gson()).fromJson(JsonHandling.base64Decode(r.json_favorite_foods), new TypeToken<ArrayList<Food>>() {
                        }.getType());
                        Log.d("size", Integer.valueOf(MainActivity.mProfile.listGroup.get(_position).commonFavoritePlaces.size()).toString());
                        MainActivity.mProfile.listGroup.get(_position).commonFavoritePlaces = intersect(MainActivity.mProfile.listGroup.get(_position).commonFavoritePlaces, r.favoritePlaces);
                        MainActivity.mProfile.listGroup.get(_position).commonFavoriteFoods = intersectfood(MainActivity.mProfile.listGroup.get(_position).commonFavoriteFoods, r.favoriteFoods);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent myIntent;
            if (!foodTrue) {
                myIntent = new Intent(GroupDisplay.this, FavoritePlaces.class);

            }
            else{
                myIntent = new Intent(GroupDisplay.this, FavoriteFood.class);
            }
            myIntent.putExtra("position", _position);
            Log.d("_position", Integer.valueOf(_position).toString());
            GroupDisplay.this.startActivity(myIntent);
        }
    }

    private ArrayList<Results> intersect(ArrayList<Results> a, ArrayList<Results> b) {
        ArrayList<Results> result = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            if (idIsInArrayList(a.get(i).getPlace_id(), b)) {
                result.add(a.get(i));
            }
        }
        return result;
    }

    private boolean idIsInArrayList(String id, ArrayList<Results> b) {
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i).getPlace_id().equals(id))
                return true;
        }
        return false;
    }

    private ArrayList<Food> intersectfood(ArrayList<Food> a, ArrayList<Food> b) {
        ArrayList<Food> result = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            if (idIsInArrayListFood(a.get(i).getDescription(), b)) {
                result.add(a.get(i));
            }
        }
        return result;
    }

    private boolean idIsInArrayListFood(String id, ArrayList<Food> b) {
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i).getDescription().equals(id))
                return true;
        }
        return false;
    }
}