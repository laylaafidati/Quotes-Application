package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class AuthorListActivity extends AppCompatActivity {
    Button btnRandomTags, btnWithTags;
    RecyclerView rvAuthor;
    AdapterAuthorList adapterAuthorList;
    public static ArrayList<Author> arrayAuthor;
    private static String URL_JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_list);

        btnRandomTags = findViewById(R.id.btn_randomTags3);
        btnWithTags = findViewById(R.id.btn_withTags3);
        rvAuthor = findViewById(R.id.rv_authorList);

        arrayAuthor = new ArrayList<>();

        btnRandomTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnWithTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorListActivity.this, WithTagsActivity.class);
                startActivity(intent);
            }
        });

        URL_JSON = "https://api.quotable.io/authors";
        authorJson();
    }

    private void authorJson() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_JSON,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject authorObject = jsonArray.getJSONObject(i);
                        Author author = new Author();
                        author.setName(authorObject.getString("name"));
                        author.setTotalQuotes(authorObject.getString("quoteCount"));
                        author.setBiography(authorObject.getString("bio"));
                        arrayAuthor.add(author);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rvAuthor.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapterAuthorList = new AdapterAuthorList(arrayAuthor, getApplication());
                rvAuthor.setAdapter(adapterAuthorList);
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