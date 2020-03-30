package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.models.Country;
import com.example.myapplication.models.DataAuth;
import com.example.myapplication.models.DataCountry;
import com.example.myapplication.models.ServerResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private TextView tv,tv2;
    private Button btn;
    private Api api;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        pref = getPreferences(0);
        String token = pref.getString(Constants.AUTH_SAVED_TOKEN,"");
        if(token.isEmpty()) {
            doAuth();
            tv.setText("No saved token!");
        } else {
            Snackbar.make(findViewById(R.id.root),"Already auth!",Snackbar.LENGTH_LONG).show();
            tv.setText(token);
        }
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });
    }

    private void doBase() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }


    private void doAuth(){
        // full log retrofit
        // https://stackoverflow.com/questions/32514410/logging-with-retrofit-2
        doBase();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "test@test.com");
        map.put("password", "1234356");
        Call<ServerResponse<DataAuth>> response = api.getUserInfo(map);
        response.enqueue(new Callback<ServerResponse<DataAuth>>() {
            @Override
            public void onResponse(Call<ServerResponse<DataAuth>> call, Response<ServerResponse<DataAuth>> response) {
                if(response.code() == 200) {
                    ServerResponse<DataAuth> result = response.body();
                    Log.e("RRRRR",result.toString()+"");
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.AUTH_SAVED_TOKEN,result.getToken()); // эта переменная будет нужна для дальнейших GET запросов
                    editor.apply();
                    TextView tv = findViewById(R.id.textView);
                    tv.setText(result.getToken());
                    Snackbar.make(findViewById(R.id.root),"Token saved!",Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("RRRRR", response.raw() + "");
                }
            }
            @Override
            public void onFailure(Call<ServerResponse<DataAuth>> call, Throwable t) {
                Log.e("RRRRR",t.toString());
            }
        });
    }

    private void getList(){
        doBase();
        Call<ServerResponse<DataCountry>> response = api.getCountryList(pref.getString(Constants.AUTH_SAVED_TOKEN,""));
        response.enqueue(new Callback<ServerResponse<DataCountry>>() {
            @Override
            public void onResponse(Call<ServerResponse<DataCountry>> call, Response<ServerResponse<DataCountry>> response) {
                if(response.code() == 200) {
                    ServerResponse<DataCountry> result = response.body();
                    Log.e("RRRRR",result.getData().getCountry() + "" );
                    List<Country> countries = result.getData().getCountry();
                    Iterator<Country> iterator = countries.iterator();
                    while(iterator.hasNext()) {
                        tv2.append(iterator.next().getName()+"\n");
                    }
                } else {
                    Log.e("RRRRR", response.raw() + "");
                }
            }
            @Override
            public void onFailure(Call<ServerResponse<DataCountry>> call, Throwable t) {
                Log.e("RRRRR",t.toString());
            }
        });
    }
}
