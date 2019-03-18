package com.foxconn.matthew.materialdesigndemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Matthew on 2017/10/17.
 */

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private Context mContext;
    private List<Animal> animals;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = itemView.findViewById(R.id.animal_image);
            textView = itemView.findViewById(R.id.animal_name);
        }
    }

    public AnimalAdapter(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.animal_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Animal animal = animals.get(position);
                Intent intent = new Intent(mContext, AnimalActivity.class);
                intent.putExtra(AnimalActivity.ANIMAL_NAME, animal.getName());
                intent.putExtra(AnimalActivity.ANIMAL_IMAGE_ID, animal.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.textView.setText(animal.getName());
        Glide.with(mContext).load(animal.getImageId()).into(holder.imageView);
        //holder.imageView.setImageResource(animal.getImageId());
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }
}
