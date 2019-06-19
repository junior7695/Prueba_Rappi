package com.jaime.marvel.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jaime.marvel.R
import com.jaime.marvel.activities.MovieActivity
import com.jaime.marvel.utils.Utils
import com.jaime.marvel.models.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*


class AdapterMoviesRecycler(var movies:ArrayList<Movie>) : RecyclerView.Adapter<AdapterMoviesRecycler.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  movies.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.setView(movies.get(p1))
    }

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imagePoster : ImageView = itemView.poster
        val txtRating : TextView = itemView.rating
        val context = itemView.context
        val txtDate = itemView.release_date
        public fun setView(movie: Movie):Unit {
            Picasso.get()
                .load(Utils.baseUrlImages+movie.poster_path)
                .error(R.drawable.ic_broken_image)
                .into(imagePoster)
            txtDate.text = movie.release_date
            txtRating.text = movie.vote_average.toString()
            imagePoster.setOnClickListener(View.OnClickListener {
                val intentGoToMovie = Intent(context,MovieActivity::class.java)
                intentGoToMovie.putExtra("movie",movie)
                context.startActivity(intentGoToMovie)
            })
        }
    }

        public fun filterList(filterMovies : ArrayList<Movie> ) : Unit {
            this.movies = filterMovies
            notifyDataSetChanged()
        }
    }