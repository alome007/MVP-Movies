package com.alome.mvp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alome.mvp.Adapters.MovieAdapter
import com.alome.mvp.Fragments.SearchFragment
import com.alome.mvp.Misc.Loader
import com.alome.mvp.Misc.Utils.Companion.showNoInternet
import com.alome.mvp.Model.Movies
import com.alome.mvp.Model.Results
import com.alome.mvp.Network.ConnectionLiveData
import com.alome.mvp.ViewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

import androidx.core.content.ContextCompat

import android.view.WindowManager

import android.view.Window
import com.alome.mvp.R


class MainActivity : AppCompatActivity() {

//    Declare the views
    lateinit var moviesRecyclerView:RecyclerView;
    lateinit var adapter:MovieAdapter
    var movies = ArrayList<Movies>()
    lateinit var container: ConstraintLayout
    private lateinit var search: ImageView
    lateinit var favourite: ImageView
    lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Init Views
        initUI()

        //check for Internet connectivity and show dialog accordingly
      val dialog=  showNoInternet(this, false)
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, {

            if (it){
                dialog.dismiss()
                initViewModel()
            }else {
                dialog.show()
            }
        })

        //open Search Fragment

        search.setOnClickListener{
            val searchFragment = SearchFragment()
            searchFragment.show(supportFragmentManager, "search")
        }

        //open favourite activity
        favourite.setOnClickListener {
            startActivity(Intent(this, FavouriteMovie::class.java))
        }
    }

    private fun initViewModel() {
        var loader = Loader()
        loader.isCancelable = false
        loader.show(supportFragmentManager, "loading")
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        //get Observer
        viewModel.getResultObserver().observe(this,  {
            loader.dismiss()
            if (it!=null){
              adapter.setupData(it.results)
            }   else {
                // Result is null
                Snackbar.make(container, getString(R.string.empty_result), Snackbar.LENGTH_LONG).show()

            }
        })

        //Call Remote API
        viewModel.makeAPICall()
    }

    private fun initUI() {
        adapter = MovieAdapter(this, movies)
        moviesRecyclerView = findViewById(R.id.recyclerView)
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.adapter = adapter
        container = findViewById(R.id.container)
        search = findViewById(R.id.search)
        favourite = findViewById(R.id.favourite)

        //change status bar color to white
        val window: Window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}


