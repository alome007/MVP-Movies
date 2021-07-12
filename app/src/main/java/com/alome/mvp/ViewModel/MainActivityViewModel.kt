package com.alome.mvp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Model.Results
import com.alome.mvp.Network.RetroInterface
import com.alome.mvp.Network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    lateinit var resultLiveData: MutableLiveData<Results>
    init {
        resultLiveData = MutableLiveData()
    }

    fun getResultObserver() : MutableLiveData<Results>{
        return resultLiveData
    }

    fun makeAPICall(){
        viewModelScope.launch(Dispatchers.IO) {
           val retroInterface= RetrofitInstance.initInstance().create(RetroInterface::class.java)
            val resp = retroInterface.getPopularMovies(Constants.MOVIE_DB_BASE_API_KEY)
            resultLiveData.postValue(resp)
        }
    }
}