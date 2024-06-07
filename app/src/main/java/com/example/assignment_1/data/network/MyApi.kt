package com.example.assignment_1.data.network

import com.example.assignment_1.data.model.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("popular")
    fun getAllMoviesList(@Query("api_key") apiKey:String = "e5ea3092880f4f3c25fbc537e9b37dc1") : Call<Movies>
}