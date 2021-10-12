package com.example.headsupprep

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    // Get all
    @Headers("Content-Type: application/json")
    @GET("/celebrities/")
    fun getAll(): Call<ArrayList<celebrityDetails>>

    // Get one
    @Headers("Content-Type: application/json")
    @GET("/celebrities/{id}")
    fun getOne(@Path("id") id: Int): Call<celebrityDetails>

    // add new ç
    @Headers("Content-Type: application/json")
    @POST("/celebrities/")
    fun addCelebrity(@Body celebrityData: celebrityDetails): Call<celebrityDetails>

    // delete a celebrity
    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>

    // update a celebrity
    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body celebrityData: celebrityDetails): Call<celebrityDetails>
}