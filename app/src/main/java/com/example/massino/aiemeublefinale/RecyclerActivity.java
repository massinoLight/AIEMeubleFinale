package com.example.massino.aiemeublefinale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";
    //variables utilisés
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private ArrayList<String> mPrix = new ArrayList<>();
    private ArrayList<String> mDispo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recycler_view);
        initImageBitmaps();
    }
    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //fauteuil gris
        mImageUrls.add(R.drawable.fauteilgris);
        mNames.add("Fauteuille gris");
        mPrix.add("40000DA");
        mDispo.add("disponible");
         //banc exterieur
        mImageUrls.add(R.drawable.banc);
        mNames.add("banc pour extérieur");
        mPrix.add("20000DA");
        mDispo.add("disponible");
        //bureau
        mImageUrls.add(R.drawable.burau);
        mNames.add("Bureau une place");
        mPrix.add("20000DA");
        mDispo.add("disponible");

        mImageUrls.add(R.drawable.fauteuille3);
        mNames.add("fauteuille ");
        mPrix.add("3000DA");
        mDispo.add("disponible");


        mImageUrls.add(R.drawable.fauteuille1);
        mNames.add("fauteuille bleu");
        mPrix.add("40000DA");
        mDispo.add("disponible");

        mImageUrls.add(R.drawable.table);
        mNames.add("Mini table");
        mPrix.add("40000DA");
        mDispo.add("disponible");

        mImageUrls.add(R.drawable.armoiremoderne);
        mNames.add("armoire nouvelle collection");
        mPrix.add("40000DA");
        mDispo.add("disponible");
//ici
        mImageUrls.add(R.drawable.thor);
        mNames.add("Le maarteau de thor mionir");
        mPrix.add("40000000000DA");
        mDispo.add("indisponible");

        mImageUrls.add(R.drawable.tiroiremoderne);
        mNames.add("table basse ");
        mPrix.add("40000DA");
        mDispo.add("disponible");





        mImageUrls.add(R.drawable.fauteuille2);
        mNames.add("fauteil maron ");
        mPrix.add("40000DA");
        mDispo.add("disponible");




        mImageUrls.add(R.drawable.fautrouge);
        mNames.add("fauteuille rouge");
        mPrix.add("40000DA");
        mDispo.add("disponible");


        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        android.support.v7.widget.RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, mPrix, mDispo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
