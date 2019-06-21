package com.jaime.marvel.utils

object Utils {
    val baseUrlImages: String = "https://image.tmdb.org/t/p/w500"
    val accessTokken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlODBmMGJiMTE0OWMyMDI0ZDVkMzRhNzc1MDYwZTYzZCIsInN1YiI6IjVkMDhmOGMwYzNhMzY4N2M3NTFlZWI0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KUC5_JuFUs1A-A160FOEr4ZoKOLZMkZZHajT12qsyg0"
    val baseUrlApi: String = "https://api.themoviedb.org"
    val endpointGetList: String = "/4/list/"
    val apiKeyDbms : String = "?api_key=e80f0bb1149c2024d5d34a775060e63d"
    val apiKeyGoogle : String = "AIzaSyB50deWbKAKbW0IaTw7sYQWvHALc3aNjfg"
    private var index:Int = 1

    public fun getEndpointList():String {
        if (index == 1) index = 5
        else if (index == 5) index = 1
        else index = 1
        return endpointGetList + index
    }

    public fun getEndpointGetVideos(idMovie : Int):String{
        return "/3/movie/"+ idMovie +"/videos" + apiKeyDbms +"&language=en-US"
    }

    public fun getEndpointGetMovieDetails(idMovie : Int):String{
        return "/3/movie/"+ idMovie + apiKeyDbms +"&language=en-US"
    }

    public fun getEndPointGetPopular():String{
        return "/3/movie/popular"+ apiKeyDbms +"&language=en-US&page=1"
    }

    public fun getEndPointGetTopRated():String {
        return "/3/movie/top_rated" + apiKeyDbms+"&language=en-US&page=1"
    }

    public fun getEndPointGetUpcoming():String {
        return "/3/movie/upcoming" + apiKeyDbms+"&language=en-US&page=1"
    }
}