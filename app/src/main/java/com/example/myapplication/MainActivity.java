package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserInfo(String.valueOf(1));
    }

    private void getUserInfo(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://195.19.44.146/service/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebInterface webInterface = retrofit.create(WebInterface.class);

        Call<ServerResponse> response = webInterface.getFullInfo(id);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                TextView textView = findViewById(R.id.textView);
                ServerResponse response1 = response.body();
                textView.setText(response1.getName());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }

}
