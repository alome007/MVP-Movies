package com.alome.mvp.Activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alome.mvp.Misc.Constants
import com.alome.mvp.R
import com.alome.mvp.Misc.Utils
import com.squareup.picasso.Picasso


class MovieDetails : AppCompatActivity() {
    lateinit var title:TextView
    lateinit var desc:TextView
    lateinit var rating:TextView
    lateinit var poster:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)
        Utils.setStatusBarTranslucent(true, this);
        initUI()
        setupData()
    }

    private fun setupData() {
        title.text = intent.getStringExtra("title")
        desc.text = intent.getStringExtra("desc")
        rating.text = intent.getStringExtra("rating")
        Picasso.get()
            .load(Constants.POSTER_BASE_URL+intent.getStringExtra("poster_url"))
            .into(poster)
    }

    private fun initUI() {
        title = findViewById(R.id.movie_name);
        desc = findViewById(R.id.desc)
        rating = findViewById(R.id.rating)
        poster = findViewById(R.id.poster)
    }


}