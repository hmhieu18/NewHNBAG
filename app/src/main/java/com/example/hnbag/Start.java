package com.example.hnbag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;

public class Start extends AppCompatActivity {
    private Button logIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);
        initComponent();
    }

    private void initComponent() {
        logIn = findViewById(R.id.loginstart);
        signUp = findViewById(R.id.signupstart);
    }

    public void startSignupClicked(View view) {
        Intent myIntent = new Intent(Start.this, SignUp.class);
        Start.this.startActivity(myIntent);
    }

    public void startLogInClicked(View view) {
        Intent myIntent = new Intent(Start.this, LogIn.class);
        Start.this.startActivity(myIntent);
    }
}