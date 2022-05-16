package be.kuleuven.androidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FirstFragment extends Fragment {

    RecyclerView recyclerView;

    String[] s1;
    int[] images = {
            R.drawable.breadpic,
            R.drawable.breadpic,
            R.drawable.breadpic,
            R.drawable.breadpic,
            R.drawable.breadpic,
            R.drawable.breadpic
    };

    public FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.items);
        //s2 = getResources().getStringArray(R.array.description);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyAdapter myAdapter = new MyAdapter(getActivity(), s1, images);

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

}