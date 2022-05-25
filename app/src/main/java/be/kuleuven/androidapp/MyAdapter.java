package be.kuleuven.androidapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<String> data1;
    ArrayList<Bitmap> images;
    Context context;
    private final OnNoteListener mOnNoteListener;

    public MyAdapter(Context ct, ArrayList<String> s1, ArrayList<Bitmap> img, OnNoteListener onNoteListener) {
        context = ct;
        data1 = s1;
        images = img;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, null);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText_1.setText(data1.get(position));
        holder.myImage.setImageBitmap(images.get(position));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myText_1;
        ImageView myImage;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            myText_1 = itemView.findViewById(R.id.item_txt);
            myImage = itemView.findViewById(R.id.my_imageView);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick((getAbsoluteAdapterPosition()));
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}