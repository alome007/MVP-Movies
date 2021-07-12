package com.alome.mvp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alome.mvp.R
import com.alome.mvp.Misc.Utils


class MovieDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_activity)
        Utils.setStatusBarTranslucent(true, this);
    }


}