package com.jaime.marvel.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.jaime.marvel.R
import com.jaime.marvel.models.Movie
import com.jaime.marvel.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*
import java.lang.Exception

class MovieActivity : AppCompatActivity() {

    private lateinit var movie : Movie;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        movie = intent.getSerializableExtra("movie") as Movie

//        Picasso.get()
//            .load(Utils.baseUrlImages+movie.backdrop_path)
//            .error(R.drawable.ic_broken_image)
//            .into(image_back_drop)
        Picasso.get().load(Utils.baseUrlImages+movie.backdrop_path).into(object : com.squareup.picasso.Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // loaded bitmap is here (bitmap)

                app_bar.background = BitmapDrawable(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })

        toolbar_layout.title = movie.title
        Picasso.get()
            .load(Utils.baseUrlImages+movie.poster_path)
            .error(R.drawable.ic_broken_image)
            .into(poster)
        overview.text = movie.overview
    }
}
