package be.kuleuven.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
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


public class FirstFragment extends Fragment implements MyAdapter.OnNoteListener {

    private static ArrayList<String> s3;
    private RequestQueue requestQueue;
    private static int categoryID;
    RecyclerView recyclerView;

    ArrayList<String> s1 = new ArrayList<>();
    ArrayList<String> s2 = new ArrayList<>();
    ArrayList<Bitmap> images = new ArrayList<>();

    public FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        requestQueue = Volley.newRequestQueue( getActivity() );
        String requestURL = "https://studev.groept.be/api/a21pt115/getCategories";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        boolean finished = false;
                        int index = 0;
                        while(!finished){
                            try {
                                JSONObject curObject = response.getJSONObject(index);
                                String curCat = curObject.getString("ProductFamily");
                                s2.add(curCat);
                                index++;
                                //System.out.println(curCat);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                decode();
                                finished = true;
                                //System.out.println(s2 + "na de db loop");
                                setAdapter();
                                s3 = s2;
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
        String requestURL1 = "https://studev.groept.be/api/a21pt115/getCategoryImage";
        JsonArrayRequest submitRequest1 = new JsonArrayRequest(Request.Method.GET, requestURL1, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        boolean finished = false;
                        int index = 0;
                        while(!finished){
                            try {
                                //System.out.println(response);
                                JSONObject curObject = response.getJSONObject(index);
                                String curIm = curObject.getString("CategoryImage");
                                s1.add(curIm);
                                index++;
                                //System.out.println(curIm);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finished = true;
                                //System.out.println(s1 + "na de db loop");
                                decode();
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
        requestQueue.add(submitRequest1);
        //wait for query to be executed
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestQueue.add(submitRequest);


        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    public void decode(){
        for(int i = 0; i < s1.size(); i++){
            System.out.println(s1.get(i));
            byte[] decodedString = Base64.decode(s1.get(i),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            images.add(decodedByte);
        }
    }

    public void setAdapter(){

        MyAdapter myAdapter = new MyAdapter(getContext(), s2, images, this);
        recyclerView.setAdapter(myAdapter);
    }

    public static FirstFragment newInstance() {
        Bundle args = new Bundle();
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNoteClick(int position) {
        categoryID = position;
        Intent intent = new Intent(getContext(), ShopScreen.class);
        startActivity(intent);
        Toast.makeText(getContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
    }

    public static int getCategoryID(){
        return categoryID;
    }

    public static ArrayList<String> getArrayList(){
        return s3;
    }
}