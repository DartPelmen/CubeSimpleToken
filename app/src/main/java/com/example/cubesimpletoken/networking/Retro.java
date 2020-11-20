package com.example.cubesimpletoken.networking;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retro {
    @FormUrlEncoded
    @POST(".")
    Call<JsonObject> updateUser(@Field("id") String first, @Field("readerID") String last);
}