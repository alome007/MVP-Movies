package com.alome.mvp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alome.mvp.Adapters.FavouriteAdapter
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Misc.Loader
import com.alome.mvp.R
import com.alome.mvp.ViewModel.LocalViewModel
import com.alome.mvp.Room.MovieEntity
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class FavouriteMovie : AppCompatActivity() {

    //    Declare the views
    lateinit var moviesRecyclerView:RecyclerView;
    lateinit var adapter:FavouriteAdapter
    var movies = ArrayList<MovieEntity>()
    lateinit var close: ImageView
    lateinit var mainContainer: NestedScrollView
    lateinit var noResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        //Init Views
        initUI()

        //Init Data from Room
        initData()

        //close Activity
        close.setOnClickListener {
            finish()
        }
    }

    private fun initData() {
        var loader = Loader()
        loader.isCancelable = false
        loader.show(supportFragmentManager, "loading")
        val viewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
        viewModel.getAllMovieObserver().observe(this,  {
            loader.dismiss()
            if (it!=null){
                if(it.size==0){

                    Toast.makeText(this, getString(R.string.no_item), Toast.LENGTH_LONG).show()
                    noResult.visibility = View.VISIBLE
                }

                noResult.visibility = View.GONE
                adapter.setupData(ArrayList(it))

            }   else {
                // Result is null
                Snackbar.make(mainContainer, getString(R.string.no_item), Snackbar.LENGTH_LONG).show()

            }
        })
    }

    private fun initUI() {
        adapter = FavouriteAdapter(this, movies)
        moviesRecyclerView = findViewById(R.id.recyclerView)
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.adapter = adapter
        mainContainer = findViewById(R.id.container)
        close = findViewById(R.id.close)
        noResult = findViewById(R.id.no_result)
    }
}


