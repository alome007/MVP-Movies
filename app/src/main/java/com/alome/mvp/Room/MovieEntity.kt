package com.alome.mvp.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(@PrimaryKey
   @ColumnInfo(name = "id")
   var id:String,
   @ColumnInfo(name = "original_title")
   var original_title:String,
   @ColumnInfo(name = "poster_path")
   var poster_path:String,
   @ColumnInfo(name = "overview")
   var overview: String,
   @ColumnInfo(name = "vote_average")
   var vote_average:String)

