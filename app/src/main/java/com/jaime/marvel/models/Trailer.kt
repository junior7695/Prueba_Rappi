package com.jaime.marvel.models

import java.io.Serializable

data class Trailer(val id : String,
                   val key: String,
                   val name: String,
                   val site: String,
                   val size: Int,
                   val type: String
            ) : Serializable