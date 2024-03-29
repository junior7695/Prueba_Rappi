package com.jaime.marvel.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.jaime.marvel.R
import com.jaime.marvel.adapters.AdapterMoviesRecycler
import com.jaime.marvel.controllers.APIController
import com.jaime.marvel.models.Movie
import com.jaime.marvel.services.ServiceVolley
import com.jaime.marvel.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray



class MainActivity : AppCompatActivity() {

    var doubleBackToExitPressedOnce : Boolean = false;
    val serviceVolley = ServiceVolley()
    val apiController = APIController(serviceVolley)
    var movies:ArrayList<Movie> = ArrayList()
    var isShowPanelCategory : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            if (isShowPanelCategory) {
                panel_categorias.visibility = View.GONE
                isShowPanelCategory = false
            }
            else {
                panel_categorias.visibility = View.VISIBLE
                isShowPanelCategory = true
            }
        }
        recycler_movies.layoutManager = GridLayoutManager(this,2)
        recycler_movies.adapter = AdapterMoviesRecycler(movies)
        getReloadApi()
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            search_edit_text.text.clear()
            search_edit_text.clearFocus()
            recycler_movies.visibility = View.VISIBLE
            val serviceVolley = ServiceVolley()
            val apiController = APIController(serviceVolley)
            apiController.get(Utils.getEndpointList()){response ->
                if (response != null){
                    (recycler_movies.adapter as AdapterMoviesRecycler).clear()
                    movies.clear()
                    val gson = Gson()
                    val results:JSONArray = response.get("results") as JSONArray
                    (0..(results.length()-1)).forEach {i->
                        movies.add(gson.fromJson(results.getJSONObject(i).toString(),Movie::class.java))
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    (recycler_movies.adapter as AdapterMoviesRecycler).filterList(movies)
                } else {
                    recycler_movies.visibility = View.GONE
                    refresh_recycler.visibility = View.VISIBLE
                    refresh_recycler.setOnClickListener(View.OnClickListener {
                        refresh_recycler.visibility = View.GONE
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(this,"Error de conexión", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        })

        btn_popular.setOnClickListener(View.OnClickListener {
            val intentGoToPopularActivity = Intent(this, PopularActivity::class.java)
            startActivity(intentGoToPopularActivity)
        })
        btn_top_rated.setOnClickListener(View.OnClickListener {
            val intentGoToTopRatedActivity = Intent(this, TopRatedActivity::class.java)
            startActivity(intentGoToTopRatedActivity)
        })
        btn_upcoming.setOnClickListener(View.OnClickListener {
            val intetGoToUpcomingActivity = Intent(this, UpcomingActivity::class.java)
            startActivity(intetGoToUpcomingActivity)
        })
    }

    public fun getReloadApi():Unit {
        recycler_movies.visibility = View.VISIBLE
        apiController.get(Utils.getEndpointList()){response ->
            if (response != null){
                val gson = Gson()
                val results:JSONArray = response.get("results") as JSONArray
                (0..(results.length()-1)).forEach {i->
                    movies.add(gson.fromJson(results.getJSONObject(i).toString(),Movie::class.java))
                }
                (recycler_movies.adapter as AdapterMoviesRecycler).notifyDataSetChanged()
            } else {
                recycler_movies.visibility = View.GONE
                refresh_recycler.visibility = View.VISIBLE
                refresh_recycler.setOnClickListener(View.OnClickListener {
                    refresh_recycler.visibility = View.GONE
                    getReloadApi()
                })
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Presionar de nuevo para salir de Ukucela", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun filter(text: String) {
        //Nuevo elemento de tipo lista
        val filterdMovies : ArrayList<Movie> = ArrayList()

        //Ciclo que busca los elementos en la lista
        for (movie in movies) {
            //Si existe el elemento en la lista lo mostrara
            if (movie.title.toLowerCase().contains(text.toLowerCase())) {
                //Añadir el elemento a la lista
                filterdMovies.add(movie)
            }
        }

        // Llamar al metodo filtro de la lista
        (recycler_movies.adapter as AdapterMoviesRecycler).filterList(filterdMovies)
    }


}
