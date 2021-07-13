package com.alome.mvp.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Model.Results
import com.alome.mvp.Network.RetroInterface
import com.alome.mvp.Network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel: ViewModel() {
    lateinit var resultLiveData: MutableLiveData<Results>
    init {
        resultLiveData = MutableLiveData()
    }

    fun getResultObserver() : MutableLiveData<Results>{
        return resultLiveData
    }

    fun makeAPICall(query:String){
        viewModelScope.launch(Dispatchers.IO) {
            val retroInterface= RetrofitInstance.initInstance().create(RetroInterface::class.java)

            try {
                val resp = retroInterface.searchMovie(Constants.MOVIE_DB_BASE_API_KEY, query )
                resultLiveData.postValue(resp)
            } catch (t: Throwable){
                when (t) {
                    is IOException -> {
                        Log.d("DataError", t.message.toString())
                    }
                    else -> {
                        Log.d("DataError", t.message.toString())
                    }
                }
            }
        }
    }
}