package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebInterface {
    @GET("/users/{id}")
    Call<ServerResponse> getFullInfo(
            @Path("id") String id
    );

}
