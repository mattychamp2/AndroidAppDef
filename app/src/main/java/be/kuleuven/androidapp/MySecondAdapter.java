package be.kuleuven.androidapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.MyViewHolder> {

    ArrayList<String> data1;
    ArrayList<Integer> images;
    Context context;
    private OnNoteListener mOnNoteListener;

    public MySecondAdapter(Context ct, ArrayList<String> s1, ArrayList<Integer> img, OnNoteListener onNoteListener){
        context = ct;
        data1 = s1;
        images = img;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_second_row, parent, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText.setText(data1.get(position));
        holder.myImage.setImageResource((images.get(position)));
        holder.myButton.callOnClick();
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myText;
        ImageView myImage;
        Button myButton;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            myText = itemView.findViewById(R.id.txtArticle);
            myImage = itemView.findViewById(R.id.imgArticle);
            myButton = itemView.findViewById(R.id.btnAdd);
            this.onNoteListener = onNoteListener;
            myButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public void getPos(){

    }
}
