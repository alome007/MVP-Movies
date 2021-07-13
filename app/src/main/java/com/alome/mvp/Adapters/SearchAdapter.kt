package com.alome.mvp.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
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
import androidx.constraintlayout.widget.ConstraintLayout
import com.alome.mvp.Activities.MovieDetails
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Misc.Constants.Companion.BLACKLISTED_MOVIES
import com.alome.mvp.Misc.Constants.Companion.MOVIE
import com.alome.mvp.Model.Movies
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SearchAdapter(var context: Context, arrayList: ArrayList<Movies>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var movies = ArrayList<Movies>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    fun setupData(movies:ArrayList<Movies>){
        this.movies =movies;
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (movie_id, title, poster_url , desc, rating) = movies[position]

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
            val sequence = arrayOf<CharSequence>("Show Details", "Don't show this again")
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Options")
            dialog.setItems(
                sequence
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        var intent = Intent(context, MovieDetails::class.java)
                            .apply {
                                putExtra("title", title)
                                putExtra("poster_url", poster_url)
                                putExtra("desc", desc)
                                putExtra("rating", rating)
                            }
                        context.startActivity(intent)
                    }
                    1->{
                        // Blacklist its Id locally
                        blackListFromSearch(movies[position], MOVIE)
                        movies.remove(movies[position])
                        notifyDataSetChanged()
                    }
                }
            }.show()
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var rating: TextView
        var poster: ImageView
        var movieCard: ConstraintLayout

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



    fun blackListFromSearch(list: Movies?, key: String?) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(BLACKLISTED_MOVIES, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
        Toast.makeText(context, context.getString(R.string.black_listed_text), Toast.LENGTH_LONG).show()
    }
}