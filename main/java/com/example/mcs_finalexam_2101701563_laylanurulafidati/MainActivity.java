package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnWithTags, btnAuthorList, btnRefresh, btnFavorite;
    TextView tvTags, tvAuthor, tvContent;
    private static String URL_JSON;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        btnWithTags = findViewById(R.id.btn_withTags1);
        btnAuthorList = findViewById(R.id.btn_authorList1);
        btnRefresh = findViewById(R.id.btn_refresh1);
        btnFavorite = findViewById(R.id.btn_favorite1);
        tvTags = findViewById(R.id.tv_tags1);
        tvAuthor = findViewById(R.id.tv_author1);
        tvContent = findViewById(R.id.tv_content1);

        btnWithTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WithTagsActivity.class);
                startActivity(intent);
            }
        });

        btnAuthorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthorListActivity.class);
                startActivity(intent);
            }
        });

        URL_JSON = "https://api.quotable.io/random";
        randomTagsJSON();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomTagsJSON();
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(tvTags.getText().toString(), tvAuthor.getText().toString(), tvContent.getText().toString());
                boolean fav = dbHelper.dbInsert(favorite);
                if (fav == true) {
                    Toast.makeText(MainActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void randomTagsJSON() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_JSON,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tags");
                    ArrayList<String> arrayTags = new ArrayList<>();
                    tvTags.setText("");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayTags.add(jsonArray.getString(i));
                        tvTags.setText(tvTags.getText() + " • " + arrayTags.get(i));
                    }
                        tvAuthor.setText(response.getString("author"));
                        tvContent.setText(response.getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menubar = getMenuInflater();
        menubar.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.favorites:
                intent = new Intent(this, FavoriteQuotesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}