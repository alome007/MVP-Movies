package com.alome.mvp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alome.mvp.Room.MovieEntity
import com.alome.mvp.Room.MoviesDb

class LocalViewModel(app:Application): AndroidViewModel(app) {

    lateinit var list: MutableLiveData<List<MovieEntity>>
    init {
        list = MutableLiveData()
        getAllMovies()
    }

    fun getAllMovieObserver():MutableLiveData<List<MovieEntity>>{
        return list
    }
    fun getAllMovies(){
        val movieDao = MoviesDb.getDatabase(getApplication())?.MovieDao()
        val movies = movieDao?.getAllMovies()
        list.postValue(movies)
    }

    fun  insertMovie(movieEntity: MovieEntity){
        val movieDao = MoviesDb.getDatabase(getApplication())?.MovieDao()
        movieDao?.insertMovie(movieEntity)
        getAllMovies()
    }
    fun  deleteMovie(movieEntity: MovieEntity){
        val movieDao = MoviesDb.getDatabase(getApplication())?.MovieDao()
        movieDao?.deleteMovie(movieEntity)
        getAllMovies()
    }
}