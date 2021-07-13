package com.alome.mvp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alome.mvp.Adapters.MovieAdapter
import com.alome.mvp.Misc.Loader
import com.alome.mvp.Model.Movies
import com.alome.mvp.R
import com.alome.mvp.ViewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class FavouriteMovie : AppCompatActivity() {

    //    Declare the views
    lateinit var moviesRecyclerView:RecyclerView;
    lateinit var adapter:MovieAdapter
    var movies = ArrayList<Movies>()
    lateinit var container: ConstraintLayout
    lateinit var close: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        //Init Views
        initUI()
        initData()
        close.setOnClickListener {
            finish()
        }

    }

    private fun initData() {
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
        close = findViewById(R.id.close)
    }
}


