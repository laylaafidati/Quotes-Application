package com.example.mcs_finalexam_2101701563_laylanurulafidati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WithTagsActivity extends AppCompatActivity {
    Button btnRandomTags, btnAuthorList, btnRefresh, btnFavorite;
    TextView tvTags, tvAuthor, tvContent;
    EditText etTags;
    private static String URL_JSON;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_tags);

        dbHelper = new DatabaseHelper(this);

        btnRandomTags = findViewById(R.id.btn_randomTags2);
        btnAuthorList = findViewById(R.id.btn_authorList2);
        btnRefresh = findViewById(R.id.btn_refresh2);
        btnFavorite = findViewById(R.id.btn_favorite2);
        tvTags = findViewById(R.id.tv_tags2);
        tvAuthor = findViewById(R.id.tv_author2);
        tvContent = findViewById(R.id.tv_content2);
        etTags = findViewById(R.id.et_tags);

        btnRandomTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithTagsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAuthorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithTagsActivity.this, AuthorListActivity.class);
                startActivity(intent);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTags.getText().toString().isEmpty()) {
                    Toast.makeText(WithTagsActivity.this, "Please input one or more tags", Toast.LENGTH_LONG).show();
                } else if (etTags.getText().toString().toLowerCase().contains(" ")) {
                    Toast.makeText(WithTagsActivity.this, "Please input tags without space", Toast.LENGTH_LONG).show();
                } else {
                    URL_JSON = "https://api.quotable.io/random?tags=" + etTags.getText().toString().toLowerCase();
                    TagsJSON();
                }
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(tvTags.getText().toString(), tvAuthor.getText().toString(), tvContent.getText().toString());
                boolean fav = dbHelper.dbInsert(favorite);
                if (fav == true) {
                    Toast.makeText(WithTagsActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void TagsJSON() {
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
                    tvAuthor.setText("Author: " + response.getString("author"));
                    tvContent.setText("Content: " + response.getString("content"));
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