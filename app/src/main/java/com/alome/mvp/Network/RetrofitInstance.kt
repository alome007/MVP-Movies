package com.alome.mvp.Network

import com.alome.mvp.Misc.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        //create a retrofit instance
        fun initInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(Constants.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
    }

}