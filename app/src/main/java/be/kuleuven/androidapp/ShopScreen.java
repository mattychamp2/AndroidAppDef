package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShopScreen extends AppCompatActivity {

    private ListView l;
    private String[] tutorials =
             { "Brood", "Nog brood",
            "Meer brood", "koek"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);
        l = findViewById(R.id.lstView);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                tutorials);
        l.setAdapter(arr);
    }

}
