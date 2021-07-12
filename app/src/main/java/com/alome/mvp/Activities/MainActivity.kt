package com.alome.mvp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alome.mvp.Adapters.MovieAdapter
import com.alome.mvp.Model.Movies
import com.alome.mvp.Model.Results
import com.alome.mvp.R
import com.alome.mvp.ViewModel.MainActivityViewModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var moviesRecyclerView:RecyclerView;
    lateinit var adapter:MovieAdapter
    var movies = ArrayList<Movies>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initViewModel()
//        var movie = Movies("The Tomorrow War","xipF6XqfSYV8DxLqfLN6aWlwuRp.jpg","This is Black widow movie","3.9");
//        movies.add(movie);
//        var movie1 = Movies("The Boss Baby: Family Business","5dExO5G2iaaTxYnLIFKLWofDzyI.jpg","This is Black widow movie","5.0");
//        movies.add(movie1);
//        var movie2 = Movies("Infinite","niw2AKHz6XmwiRMLWaoyAOAti0G.jpg","This is Black widow movie","3.9");
//        movies.add(movie2);
//        var movie3 = Movies("Luca","jTswp6KyDYKtvC52GbHagrZbGvD.jpg","This is Black widow movie","3.9");
//        movies.add(movie3);
//        var movie4 = Movies("Black Widow","ozGjTevqyeR7ocJHg532oLKMJdG.jpg","This is Black widow movie","3.9");
//        movies.add(movie4);
//        var movie5 = Movies("F9","qIicLxr7B7gIt5hxZxo423BJLlK.jpg","This is Black widow movie","3.9");
//        movies.add(movie5);
//


    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getResultObserver().observe(this,  {
            if (it!=null){
              adapter.setupData(it.results)
            }   else {
                // Result is null

                Toast.makeText(this, "I am null", Toast.LENGTH_LONG).show()

            }
        })

        viewModel.makeAPICall()
    }

    private fun initUI() {
        adapter = MovieAdapter(this, movies)
        moviesRecyclerView = findViewById(R.id.recyclerView)
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.adapter = adapter
    }
}


