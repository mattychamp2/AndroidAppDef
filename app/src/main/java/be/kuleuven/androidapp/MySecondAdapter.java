package be.kuleuven.androidapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MySecondAdapter extends RecyclerView.Adapter<MySecondAdapter.MyViewHolder> {

    String[] data1;
    int[] images;
    Context context;
    public MySecondAdapter(Context ct, String[] s1, int[] img){
        context = ct;
        data1 = s1;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_second_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText.setText(data1[position]);
        holder.myImage.setImageResource((images[position]));
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText = itemView.findViewById(R.id.txtArticle);
            myImage = itemView.findViewById(R.id.imgArticle);
        }
    }
}
