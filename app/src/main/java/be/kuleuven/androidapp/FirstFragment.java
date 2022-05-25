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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstFragment extends Fragment implements MyAdapter.OnNoteListener {

    private static ArrayList<String> s3;
    private static int categoryID;
    RecyclerView recyclerView;
    ArrayList<String> s1 = new ArrayList<>();
    ArrayList<String> s2 = new ArrayList<>();
    ArrayList<Bitmap> images = new ArrayList<>();

    public FirstFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String requestURL = "https://studev.groept.be/api/a21pt115/getCategories";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                response -> {
                    boolean finished = false;
                    int index = 0;
                    while (!finished) {
                        try {
                            JSONObject curObject = response.getJSONObject(index);
                            String curCat = curObject.getString("ProductFamily");
                            s2.add(curCat);
                            index++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            decode();
                            finished = true;
                            setAdapter();
                            s3 = s2;
                        }
                    }
                },
                error -> {
                }
        );
        String requestURL1 = "https://studev.groept.be/api/a21pt115/getCategoryImage";
        JsonArrayRequest submitRequest1 = new JsonArrayRequest(Request.Method.GET, requestURL1, null,
                response -> {
                    boolean finished = false;
                    int index = 0;
                    while (!finished) {
                        try {
                            JSONObject curObject = response.getJSONObject(index);
                            String curIm = curObject.getString("CategoryImage");
                            s1.add(curIm);
                            index++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finished = true;
                            decode();
                        }
                    }
                },
                error -> {
                }
        );
        requestQueue.add(submitRequest1);
        //wait for query to be executed
        try {
            Thread.sleep(300);
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

    public void decode() {
        for (int i = 0; i < s1.size(); i++) {
            byte[] decodedString = Base64.decode(s1.get(i), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            images.add(decodedByte);
        }
    }

    public void setAdapter() {
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
    }

    public static int getCategoryID() {
        return categoryID;
    }

    public static ArrayList<String> getArrayList() {
        return s3;
    }
}