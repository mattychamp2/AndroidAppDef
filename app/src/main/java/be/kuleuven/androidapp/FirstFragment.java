package be.kuleuven.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FirstFragment extends Fragment implements MyAdapter.OnNoteListener {

    RecyclerView recyclerView;

    ArrayList<String> s1 = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();

    public FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        s1.add("Brood");
        s1.add("Patisserie");
        s1.add("Cake");
        s1.add("Taart");
        s1.add("Pralines");
        s1.add("Belegde Broodjes");
        images.add(R.drawable.broodassortiment);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);
        images.add(R.drawable.breadpic);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyAdapter myAdapter = new MyAdapter(getActivity(), s1, images, this);

        recyclerView.setAdapter(myAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    public static FirstFragment newInstance() {

        Bundle args = new Bundle();

        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(), ShopScreen.class);
        startActivity(intent);
        Toast.makeText(getContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();
    }
}