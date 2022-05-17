package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShopScreen extends AppCompatActivity {

    private Button addToCartButton;

    RecyclerView recyclerView;

    ArrayList<String> s1 = new ArrayList<String>();

    int[] images = {
            R.drawable.breadpic,
            R.drawable.breadpic,
            R.drawable.breadpic
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);
        s1.add("Brood");
        s1.add("Brood");
        s1.add("Brood");
        recyclerView = findViewById(R.id.scndRecyclerView);
        MySecondAdapter mySecondAdapter = new MySecondAdapter(this, s1, images);
        recyclerView.setAdapter(mySecondAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
