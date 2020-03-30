package com.example.myapplication;

import com.example.myapplication.models.DataAuth;
import com.example.myapplication.models.DataCountry;
import com.example.myapplication.models.ServerResponse;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @Headers({
            "X-API-KEY: " + Constants.X_API_KEY,
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("api/user/login")
    Call<ServerResponse<DataAuth>> getUserInfo(@FieldMap HashMap<String, String> map);

    @Headers({
            "X-API-KEY: " + Constants.X_API_KEY
    })
    @GET("api/country/all")
    Call<ServerResponse<DataCountry>> getCountryList(@Header("X-TOKEN") String token);

}
