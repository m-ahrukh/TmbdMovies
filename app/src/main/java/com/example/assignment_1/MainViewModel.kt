package com.example.assignment_1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_1.data.model.Movies
import com.example.assignment_1.data.model.Result
import com.example.assignment_1.data.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//get data
class MainViewModel: ViewModel() {

    val _movies = MutableStateFlow<List<Result>>(emptyList()) //defines private
    val movies: StateFlow<List<Result>> = _movies

    fun fetchMovies(){
        viewModelScope.launch (Dispatchers.IO) {
            NetworkRepository.getAllMovies().enqueue(object : Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if (response.isSuccessful) {
                        _movies.value=response.body()?.results?: emptyList()
                    }
                }

                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.i("CheckResponse", "onFailure: ${t.message}")
                }

            })
        }
    }
}