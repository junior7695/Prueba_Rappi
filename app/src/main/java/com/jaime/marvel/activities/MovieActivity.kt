package com.jaime.marvel.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.jaime.marvel.R
import com.jaime.marvel.models.Movie
import com.jaime.marvel.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private lateinit var movie : Movie;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        movie = intent.getSerializableExtra("movie") as Movie

        Picasso.get()
            .load(Utils.baseUrlImages+movie.backdrop_path)
            .error(R.drawable.ic_broken_image)
            .into(image_back_drop)
        toolbar.title = movie.title
    }
}
