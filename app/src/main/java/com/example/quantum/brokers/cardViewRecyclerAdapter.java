package com.example.quantum.brokers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;

import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abdel-Rahman on 11/08/2017.
 */

public class cardViewRecyclerAdapter extends RecyclerView.Adapter<cardViewRecyclerAdapter.myViewHolder>{
    ArrayList<realStateClass> listData;
    realStateClass card;
    Context context;

    public cardViewRecyclerAdapter(ArrayList<realStateClass> listData, Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        card = listData.get(position);
        holder.title.setText(card.getAddressHolder());
        Picasso.with(context).load(card.getImgHolder()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moreinfo.class);
                intent.putExtra("key", card);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView img;
        CardView cardView;

        public myViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.address);
            img = (ImageView) itemView.findViewById(R.id.cardImage);
            cardView = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}
