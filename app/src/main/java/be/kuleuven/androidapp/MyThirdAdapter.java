package be.kuleuven.androidapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyThirdAdapter extends RecyclerView.Adapter<MyThirdAdapter.MyViewHolder> {

    ArrayList<String> data1;
    ArrayList<Integer> data2;
    ArrayList<Bitmap> images;
    Context context;
    private OnNoteListener mOnNoteListener;

    public MyThirdAdapter(Context ct, ArrayList<String> s1, ArrayList<Integer> s2, ArrayList<Bitmap> img, OnNoteListener onNoteListener){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_third_row, parent, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        holder.myText2.setText(data2.get(position) + "x");
        holder.myImage.setImageBitmap(images.get(position));
        holder.myBtn.callOnClick();
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myText1, myText2;
        ImageView myImage;
        Button myBtn;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.txtItem);
            myText2 = itemView.findViewById(R.id.txtQty);
            myImage = itemView.findViewById(R.id.imgCartArticle);
            myBtn = itemView.findViewById(R.id.btnRemove);
            this.onNoteListener = onNoteListener;
            myBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
