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
import com.alome.mvp.Model.Movies
import com.alome.mvp.Model.Results
import com.alome.mvp.Network.ConnectionLiveData
import com.alome.mvp.R
import com.alome.mvp.ViewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

//    Declare the views
    lateinit var moviesRecyclerView:RecyclerView;
    lateinit var adapter:MovieAdapter
    var movies = ArrayList<Movies>()
    lateinit var container: ConstraintLayout
    lateinit var progressBar: ProgressBar
    lateinit var search: ImageView
    lateinit var favourite: ImageView
    lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Init Views
        initUI()
        //check for Internet connectivity
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, {
            if (it){
                //there's internet
                initViewModel()
            }   else {
                // there's no internet
                Snackbar.make(container, getString(R.string.empty_result), Snackbar.LENGTH_LONG).show()

            }
        })

        search.setOnClickListener{
            val searchFragment = SearchFragment()
            searchFragment.show(supportFragmentManager, "search")
        }

        favourite.setOnClickListener {
            startActivity(Intent(this, FavouriteMovie::class.java))
        }
    }

    private fun initViewModel() {
        var loader = Loader()
        loader.isCancelable = false
        loader.show(supportFragmentManager, "loading")
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getResultObserver().observe(this,  {
            loader.dismiss()
            if (it!=null){
              adapter.setupData(it.results)
            }   else {
                // Result is null
                Snackbar.make(container, getString(R.string.empty_result), Snackbar.LENGTH_LONG).show()

            }
        })

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
    }
}


