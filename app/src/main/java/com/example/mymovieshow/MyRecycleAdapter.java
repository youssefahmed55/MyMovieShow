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

public class MyRecycleAdapter extends  RecyclerView.Adapter<MyRecycleAdapter.Holder> {
    private ArrayList<Result2> arrayList;
    private Context context;
    private OnmyClickListenerrr onmyClickListenerrr;
    public MyRecycleAdapter(Context context) {
        this.context = context;
    }

    public void setArrayList(ArrayList<Result2> arrayList) {
        this.arrayList = arrayList;
    }

    public void setOnClickListeners(OnmyClickListenerrr onClickListeners) {
        this.onmyClickListenerrr = onClickListeners;
    }

    public interface OnmyClickListenerrr {
        void onclick2(Result2 resultt2) ;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false)) ;
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
            imageView = itemView.findViewById(R.id.imageofmovie_list1);
            textView1 = itemView.findViewById(R.id.nameofmovie);
            textView2 = itemView.findViewById(R.id.Detailsofmovie);
            textView3 = itemView.findViewById(R.id.rateofmovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // you can trust the adapter position
                        // do whatever you intend to do with this position
                        if (onmyClickListenerrr != null)
                            onmyClickListenerrr.onclick2(arrayList.get(getAbsoluteAdapterPosition()));
                    }

                }
            });
        }
    }
}
