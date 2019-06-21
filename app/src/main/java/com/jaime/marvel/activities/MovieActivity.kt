package com.jaime.marvel.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.jaime.marvel.R
import com.jaime.marvel.models.Movie
import com.jaime.marvel.utils.Utils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*
import java.lang.Exception



class MovieActivity : AppCompatActivity() {

    var isFullScreenYouTubePlayer : Boolean = false;
    var youTubePlayer:YouTubePlayer? = null
    lateinit var movieActivity: MovieActivity
    lateinit var trailer : YouTubePlayerFragment
    private lateinit var movie : Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        movieActivity = this
        movie = intent.getSerializableExtra("movie") as Movie
        val trailer= fragmentManager.findFragmentById(R.id.trailer_fragment) as YouTubePlayerFragment
        rating.text = (movie.vote_average*10).toString() + "%"
        rating_bar.setProgress((movie.vote_average*10).toInt())

//        Picasso.get()
//            .load(Utils.baseUrlImages+movie.backdrop_path)
//            .error(R.drawable.ic_broken_image)
//            .into(image_back_drop)
        Picasso.get().load(Utils.baseUrlImages+movie.backdrop_path).into(object : Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // loaded bitmap is here (bitmap)

                app_bar.background = BitmapDrawable(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })

        toolbar_layout.title = movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Picasso.get()
            .load(Utils.baseUrlImages+movie.poster_path)
            .error(R.drawable.ic_broken_image)
            .into(poster)
        overview.text = movie.overview


        if (movie.trailers.size>0) {
            play_trailer.visibility = View.GONE
            trailer.initialize(Utils.apiKeyGoogle,object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youTubePlayer:  YouTubePlayer?, wasRestored: Boolean) {

                        youTubePlayer?.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI)
                        youTubePlayer?.loadVideo(movie.trailers.get(0).key)
                    if(!wasRestored) {
                        movieActivity.youTubePlayer = youTubePlayer
                        youTubePlayer?.cueVideo(movie.trailers.get(0).key)
                    }
                    youTubePlayer?.setOnFullscreenListener { fullScreen ->
                        movieActivity.isFullScreenYouTubePlayer = fullScreen
                    }

                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                }
            })
        } else {
            panel_trailer.visibility = View.GONE
        }

    }
    override fun onBackPressed() {
        if (isFullScreenYouTubePlayer) {
            youTubePlayer?.setFullscreen(false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
