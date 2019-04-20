package com.example.google.videoupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class AppHome extends AppCompatActivity {
    private static final String URL = "http://192.168.1.33/ayaan/getItems.php";
    String usersname,address,phone,field;
    List<Item> itemList;
    LinearLayout uplink,proflink;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        recyclerView = findViewById(R.id.itemLists);
        uplink = findViewById(R.id.uplink);
        proflink = findViewById(R.id.ProfileLayout);
        Intent intents =getIntent();
        usersname = intents.getStringExtra("name");
        field = intents.getStringExtra("field");
        phone = intents.getStringExtra("phone");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        proflink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppHome.this,Profile.class);
                intent.putExtra("name", usersname);
                intent.putExtra("phone", phone);
                intent.putExtra("field", field);
                startActivity(intent);
            }
        });
        uplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppHome.this,MainActivity.class);
                intent.putExtra("name", usersname);
                intent.putExtra("phone", phone);
                intent.putExtra("field", field);
                startActivity(intent);
            }
        });



        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson =gsonBuilder.create();
                Log.d("RESPONSE<><><><>",response);
                Item[] items = gson.fromJson(response,Item[].class);
                recyclerView.setAdapter(new GithubAdapter(AppHome.this,items));
                gson.fromJson(response,Item[].class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppHome.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
