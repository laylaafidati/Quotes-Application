package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class FavoriteQuotesActivity extends AppCompatActivity {
    ArrayList<Favorite> arrayFavorites;
    ArrayList<Integer> array_id;
    RecyclerView rv_Favorites;
    AdapterFavorites adapterFavorites;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        dbHelper = new DatabaseHelper(this);

        rv_Favorites = findViewById(R.id.rv_favorites);

        arrayFavorites = new ArrayList<>();
        array_id = new ArrayList<>();


        Cursor cursor = dbHelper.getAllData();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tags = cursor.getString(1);
            String author = cursor.getString(2);
            String content = cursor.getString(3);

            Favorite favorite = new Favorite(tags, author, content);
            arrayFavorites.add(favorite);
            array_id.add(id);
        }

        adapterFavorites = new AdapterFavorites(arrayFavorites, array_id, this);
        rv_Favorites.setLayoutManager(new LinearLayoutManager(this));
        rv_Favorites.setAdapter(adapterFavorites);
    }
}