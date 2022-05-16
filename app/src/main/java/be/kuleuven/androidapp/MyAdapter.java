package be.kuleuven.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] data1, data2;
    int[] images;
    Context context;

    public MyAdapter(Context ct, String[] s1, int[] img){
        context = ct;
        data1 = s1;
        images = img;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.my_row, parent, false);
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText_1.setText(data1[position]);
        //holder.myText_2.setText(data2[position]);
        holder.myImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText_1, myText_2;
        ImageView myImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText_1 = itemView.findViewById(R.id.item_txt);
            myImage = itemView.findViewById(R.id.my_imageView);
        }
    }
}