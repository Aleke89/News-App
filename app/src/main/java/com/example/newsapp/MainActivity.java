package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newsapp.Models.Headlines;
import com.example.newsapp.Models.NewsApiResponse;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ProgressDialog progressDialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching news articles of " + query);
                progressDialog.show();
                RequestManager requestManager = new RequestManager(MainActivity.this);
                requestManager.getNewsHeadline(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        b1 = findViewById(R.id.btn_first);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_second);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_third);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_fourth);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_fifth);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_sixth);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_seventh);
        b7.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching news from Server..");
        progressDialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadline(listener,"general",null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Headlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this,"No data found!!",Toast.LENGTH_SHORT);

            }else{
                showNews(list);
                progressDialog.dismiss();
            }

        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this,"An Error Occured!!",Toast.LENGTH_LONG);
        }
    };

    private void showNews(List<Headlines> list){
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        customAdapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void OnNewsClicked(Headlines headlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
                .putExtra("data",headlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        progressDialog.setTitle("Fetching news articles of "+ category);
        progressDialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadline(listener,category,null);
    }
}