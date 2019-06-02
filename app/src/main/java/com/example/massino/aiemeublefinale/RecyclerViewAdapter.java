package com.example.massino.aiemeublefinale;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Massino on 1/6/2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mPrix = new ArrayList<>();
    private ArrayList<String> mDispo = new ArrayList<>();
    private Context mContext;



    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<Integer> images, ArrayList<String> prix, ArrayList<String> dispo ) {
        mImageNames = imageNames;
        mImages = images;
        mPrix=prix;
        mDispo=dispo;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        //Ici on affiche les contenus
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);
        holder.imageName.setText(mImageNames.get(position));
        holder.prix.setText(mPrix.get(position));
        holder.dispo.setText(mDispo.get(position));

        //ici le clic listener
      holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext.getApplicationContext(),"tres bon choix"+position,Toast.LENGTH_SHORT).show();
               int choix =position;
                Intent scenformeActivity = new Intent(mContext,com.example.massino.aiemeublefinale.SceneformeActivity.class);
               scenformeActivity.putExtra("image_url",choix);
                mContext.startActivity(scenformeActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView prix;
        TextView dispo;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            prix =itemView.findViewById(R.id.prix);
            dispo=itemView.findViewById(R.id.dispo);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}















