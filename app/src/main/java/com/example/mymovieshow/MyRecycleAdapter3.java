package com.example.mymovieshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovieshow.pojo.Result2;

import java.util.ArrayList;

public class MyRecycleAdapter3 extends  RecyclerView.Adapter<MyRecycleAdapter3.Holder> {
    private ArrayList<Result2> arrayList;
    private Context context;
    private OnmyClickListenerrr2 onmyClickListenerrr2;
    public MyRecycleAdapter3(Context context) {
        this.context = context;
    }

    public void setOnmyClickListenerrr(OnmyClickListenerrr2 onmyClickListenerrr) {
        this.onmyClickListenerrr2 = onmyClickListenerrr;
    }

    public void setArrayList(ArrayList<Result2> arrayList) {
        this.arrayList = arrayList;
    }

    public interface OnmyClickListenerrr2 {
        void onclick22(Result2 resultt2) ;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getPosterPath()).into(holder.imageView);
        holder.textView1.setText(arrayList.get(position).getOriginalTitle());
        holder.textView2.setText(arrayList.get(position).getReleaseDate());
        holder.textView3.setText(String.valueOf(arrayList.get(position).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        if(arrayList == null) {
            return 0;
        }else {
            return arrayList.size();
        }
    }

     class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView1,textView2,textView3;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageofmovie_list3);
            textView1 = itemView.findViewById(R.id.nameofmovie_list3);
            textView2 = itemView.findViewById(R.id.Detailsofmovie_list3);
            textView3 = itemView.findViewById(R.id.rateofmovie_list3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // you can trust the adapter position
                        // do whatever you intend to do with this position
                        if (onmyClickListenerrr2 != null)
                            onmyClickListenerrr2.onclick22(arrayList.get(getAbsoluteAdapterPosition()));
                    }
                }
            });
        }
    }
}
