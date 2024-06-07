package com.example.assignment_1.data.network

import com.example.assignment_1.data.model.Movies
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkRepository {
    private val BASE_URL = "https://api.themoviedb.org/3/movie/"
    suspend fun getAllMovies(): Call<Movies>{

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        return api.getAllMoviesList()
    }
}