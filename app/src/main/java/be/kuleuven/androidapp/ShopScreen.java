package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShopScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);

        ListView listview = (ListView) findViewById(R.id.lstView);
        listview.setOnItemClickListener(this::onItemClick);

        ArrayList<String> arrayLst = new ArrayList<String>();
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");
        arrayLst.add("bar");
        arrayLst.add("foo");
        arrayLst.add("bar");



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayLst );

        listview.setAdapter(arrayAdapter);

    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
