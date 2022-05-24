package be.kuleuven.androidapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecondFragment extends Fragment implements MyAdapter.OnNoteListener {

    private RequestQueue requestQueue;
    static ArrayList<String> s1 = new ArrayList<>();
    static ArrayList<Integer> s2 = new ArrayList<>();
    ArrayList<String> rawImages = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
    ArrayList<Bitmap> images = new ArrayList<>();
    private int count;
    RecyclerView recyclerView;

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        s1 = new ArrayList<>();
        s2 = new ArrayList<>();
        rawImages = new ArrayList<>();
        prices = new ArrayList<>();
        images = new ArrayList<>();
        count = 0;
        requestQueue = Volley.newRequestQueue(getActivity());
        String requestURL = "https://studev.groept.be/api/a21pt115/getCartForFragment";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean finished = false;
                        int index = 0;
                        while (!finished) {
                            try {
                                JSONObject curObject = response.getJSONObject(index);
                                String curItem = curObject.getString("item");
                                s1.add(curItem);
                                int curQty = curObject.getInt("quantity");
                                s2.add(curQty);
                                String curRIm = curObject.getString("CategoryImage");
                                rawImages.add(curRIm);
                                Double curPrice = curObject.getDouble("price");
                                prices.add(curPrice);
                                index++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                decode();
                                finished = true;
                                setAdapter();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(submitRequest);

        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewFr2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    public void decode() {
        for (int i = 0; i < rawImages.size(); i++) {
            byte[] decodedString = Base64.decode(rawImages.get(i), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            images.add(decodedByte);
        }
    }

    public void setAdapter() {
        MyThirdAdapter myThirdAdapter = new MyThirdAdapter(getContext(), s1, s2, images, prices, this::onNoteClick);
        recyclerView.setAdapter(myThirdAdapter);
    }

    public static SecondFragment newInstance() {
        Bundle args = new Bundle();
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNoteClick(int position) {
        if (count >= s1.size()) {
            if (s2.get(position) == 1) {
                removeFromCart("https://studev.groept.be/api/a21pt115/deleteItemFromCart/" + s1.get(position));
            } else {
                removeFromCart("https://studev.groept.be/api/a21pt115/decreaseItemInCart/" + s1.get(position));
            }
        } else {
            count++;
        }
    }

    private void removeFromCart(String url) {
        requestQueue = Volley.newRequestQueue(getActivity());
        String requestURL = url;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        update();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(submitRequest);
        Toast.makeText(getContext(), "removed", Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<String> getCart(){
        return s1;
    }

    public static ArrayList<Integer> getQty(){
        return s2;
    }

    public void update(){
        //refresh here
    }
}
