package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopScreen extends AppCompatActivity {

    private Button addToCartButton;

    private RequestQueue requestQueue;

    RecyclerView recyclerView;

    ArrayList<String> s1 = new ArrayList<>();

    ArrayList<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);
        addToCartButton = findViewById(R.id.btnAdd);

        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);

        requestQueue = Volley.newRequestQueue(this);
        String requestURL = makeURL();
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        System.out.println(response);
                        boolean finished = false;
                        int index = 0;
                        while(!finished){
                            try {
                                System.out.println(response);
                                JSONObject curObject = response.getJSONObject(index);
                                String curCat = curObject.getString("name");
                                s1.add(curCat);
                                index++;
                                System.out.println(curCat);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finished = true;
                                System.out.println(s1 + "na de db loop");
                                setAdapter();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //txtResponse.setText( error.getLocalizedMessage() );
                    }
                }
        );
        requestQueue.add(submitRequest);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.scndRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String makeURL() {
        String cat = FirstFragment.getArrayList().get(FirstFragment.getCategoryID());
        return ("https://studev.groept.be/api/a21pt115/getItems/" + cat);
    }

    public void setAdapter() {
        MySecondAdapter mySecondAdapter = new MySecondAdapter(this, s1, images);
        recyclerView.setAdapter(mySecondAdapter);
    }

    private void postDataUsingVolley() {
        String url = "https://studev.groept.be/api/a21pt115/insertTest";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ShopScreen.this, "Data added to API", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(ShopScreen.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }


    public void addArticleToCartBtn(View caller){
        postDataUsingVolley();
    }

}
