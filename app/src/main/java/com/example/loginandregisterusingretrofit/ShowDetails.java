package com.example.loginandregisterusingretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandregisterusingretrofit.ModelClass.FetchModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowDetails extends AppCompatActivity {
RecyclerView recyclerView;
Retrofit retrofit;
String URL="http://192.168.147.74/";
Adapter adapter;
ArrayList<FetchModel> fetchModelArrayList;
SearchView SearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_details);

        recyclerView = findViewById(R.id.recyclerView);
        SearchBar = findViewById(R.id.SearchBar);
        fetchModelArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<ArrayList<FetchModel>> call = api.GetAllDetails();
        call.enqueue(new Callback<ArrayList<FetchModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchModel>> call, Response<ArrayList<FetchModel>> response) {
                if (response.isSuccessful()){
                    fetchModelArrayList = response.body();
                    adapter = new Adapter(fetchModelArrayList,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ShowDetails.this, "Data No Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FetchModel>> call, Throwable t) {
                Toast.makeText(ShowDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        SearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                  FilterLists(newText);
                return true;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void FilterLists(String newText) {
        ArrayList<FetchModel> filter = new ArrayList<>();
        for (FetchModel model :fetchModelArrayList){
          if (model.name.toLowerCase().contains(newText.toLowerCase())){
              filter.add(model);
          }
        }
        if (filter.isEmpty()){
              recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "Data Not Exists...", Toast.LENGTH_SHORT).show();
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.SetFilterList(filter);
        }
    }
}
