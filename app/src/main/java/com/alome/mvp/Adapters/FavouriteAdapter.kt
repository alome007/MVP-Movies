package com.alome.mvp.Adapters

import android.content.Context
import android.content.Intent
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import com.alome.mvp.R
import com.squareup.picasso.Picasso
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.alome.mvp.Activities.MovieDetails
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Model.Movies
import com.alome.mvp.Room.MovieEntity

class FavouriteAdapter(var context: Context, arrayList: ArrayList<MovieEntity>) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    var movies = ArrayList<MovieEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    fun setupData(movies:ArrayList<MovieEntity>){
        this.movies =movies;
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id,title, poster_url , desc, rating) = movies[position]

        if(title.length>21){
            holder.title.text = title.substring(0, 19)+"..."
        }   else {
            holder.title.text = title
        }
        holder.rating.text = rating
        Picasso.get()
            .load(Constants.POSTER_BASE_URL + poster_url)
            .into(holder.poster)
        holder.movieCard.setOnClickListener{
            var intent = Intent(context, MovieDetails::class.java)
                .apply {
                    putExtra("title", title)
                    putExtra("poster_url", poster_url)
                    putExtra("desc", desc)
                    putExtra("rating", rating)
                    putExtra("id", id)
                }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var rating: TextView
        var poster: ImageView
        var movieCard: CardView

        init {
            title = view.findViewById(R.id.title)
            rating = view.findViewById(R.id.rating)
            poster = view.findViewById(R.id.poster)
            movieCard = view.findViewById(R.id.cardView)
        }
    }

    init {
        movies = arrayList
    }
}