package com.alome.mvp.Network

import com.alome.mvp.Model.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroInterface {

    //Get Popular Movies
    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key") query: String): Results
}