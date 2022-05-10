package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.kuleuven.androidapp.databinding.ActivityMainBinding;

public class Shop extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }
}