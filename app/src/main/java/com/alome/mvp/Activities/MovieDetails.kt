package com.alome.mvp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.alome.mvp.Misc.Constants
import com.alome.mvp.R
import com.alome.mvp.Misc.Utils
import com.alome.mvp.Model.Movies
import com.alome.mvp.Room.MovieEntity
import com.alome.mvp.ViewModel.LocalViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.util.ArrayList


class MovieDetails : AppCompatActivity() {
    lateinit var title:TextView
    lateinit var desc:TextView
    lateinit var rating:TextView
    lateinit var poster:ImageView
    lateinit var favIcon:ImageView
    lateinit var addToFav:View
    lateinit var container:NestedScrollView
    var alreadyAdded:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)
        Utils.setStatusBarTranslucent(true, this);
        initUI()
        setupData()
        addToFav.setOnClickListener {
            
            //check if already added to Favourite Movies
            if (alreadyAdded){
                // already added to Favourite Movies, remove from list
                val viewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
                val movie= MovieEntity(intent.getStringExtra("id").toString(),intent.getStringExtra("title").toString(), intent.getStringExtra("poster_url").toString(),
                    intent.getStringExtra("desc").toString(), intent.getStringExtra("rating").toString())
                viewModel.deleteMovie(movie)
                alreadyAdded = false
                favIcon.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                Snackbar.make(container, getString(R.string.movie_removed), Snackbar.LENGTH_LONG).show()
            }else {
                // Not added, create a new record of it
                alreadyAdded = true
                val viewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
                rating.text = intent.getStringExtra("rating")
                val movie= MovieEntity(intent.getStringExtra("id").toString(),intent.getStringExtra("title").toString(), intent.getStringExtra("poster_url").toString(),
                    intent.getStringExtra("desc").toString(), intent.getStringExtra("rating").toString())
                viewModel.insertMovie(movie)
                Snackbar.make(container, getString(R.string.movie_added), Snackbar.LENGTH_LONG).setAction("Show") {
                    startActivity(Intent(this, FavouriteMovie::class.java))
                }.show()
            }

        }
    }

    private fun setupData() {
        title.text = intent.getStringExtra("title").toString()
        desc.text = intent.getStringExtra("desc").toString()
        rating.text = intent.getStringExtra("rating").toString()
        Picasso.get()
            .load(Constants.POSTER_BASE_URL+intent.getStringExtra("poster_url").toString())
            .into(poster)

        val viewModel = ViewModelProvider(this).get(LocalViewModel::class.java)
        viewModel.getAllMovieObserver().observe(this,  {

            //check if already added to favourite
            val movie= MovieEntity(intent.getStringExtra("id").toString(),intent.getStringExtra("title").toString(), intent.getStringExtra("poster_url").toString(),
                intent.getStringExtra("desc").toString(), intent.getStringExtra("rating").toString())
            if (it.contains(movie)){
                alreadyAdded = true
                favIcon.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24)
            }
        })
    }

    private fun initUI() {
        title = findViewById(R.id.movie_name);
        desc = findViewById(R.id.desc)
        rating = findViewById(R.id.rating)
        poster = findViewById(R.id.poster)
        favIcon = findViewById(R.id.fav_icon)
        addToFav = findViewById(R.id.add_fav)
        container = findViewById(R.id.container)
    }


}