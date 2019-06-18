package com.jaime.marvel.utils

object Utils {
    val baseUrlImages: String = "https://image.tmdb.org/t/p/w500"
    val accessTokken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlODBmMGJiMTE0OWMyMDI0ZDVkMzRhNzc1MDYwZTYzZCIsInN1YiI6IjVkMDhmOGMwYzNhMzY4N2M3NTFlZWI0NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.KUC5_JuFUs1A-A160FOEr4ZoKOLZMkZZHajT12qsyg0"
    val baseUrlApi: String = "https://api.themoviedb.org"
    val endpointGetList: String = "/4/list/"
    private var index:Int = 1

    public fun getEndpointList():String {
        if (index == 1) index = 5
        else if (index == 5) index = 1
        else index = 1
        return endpointGetList + index
    }
}