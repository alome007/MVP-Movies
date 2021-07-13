package com.alome.mvp.Model


data class Results(var results:ArrayList<Movies>)
data class Movies(var id:Int, var original_title:String, var poster_path:String, var overview: String, var vote_average:String)
