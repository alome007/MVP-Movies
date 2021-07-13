package com.alome.mvp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Results(var results:ArrayList<Movies>)

@Entity(tableName = "movies_table")
data class Movies(@PrimaryKey var id:Int, var original_title:String, var poster_path:String, var overview: String, var vote_average:String)
