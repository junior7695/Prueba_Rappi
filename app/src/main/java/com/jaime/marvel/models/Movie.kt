package com.jaime.marvel.models

import java.io.Serializable

data class Movie( val id:Int,
                  val title:String,
                  val original_language:String,
                  val vote_average:Double,
                  val media_type:String,
                  val release_date:String,
                  val overview:String,
                  val poster_path:String,
                  val backdrop_path:String,
                  val popularity:Float,
                  val otra_cosa:Int,
                  var trailers:ArrayList<Trailer>
                ) : Serializable


