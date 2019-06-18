package com.jaime.marvel.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
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

    var movies:ArrayList<Movie> = ArrayList();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        recycler_movies.layoutManager = GridLayoutManager(this,2)
        recycler_movies.adapter = AdapterMoviesRecycler(movies)
        getReloadApi()

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            recycler_movies.visibility = View.VISIBLE
            val serviceVolley = ServiceVolley()
            val apiController = APIController(serviceVolley)
            apiController.get(Utils.getEndpointList()){response ->
                if (response != null){
                    (recycler_movies.adapter as AdapterMoviesRecycler).clear()
                    val gson = Gson()
                    val results:JSONArray = response?.get("results") as JSONArray
                    (0..(results.length()-1)).forEach {i->
                        movies.add(gson.fromJson(results.getJSONObject(i).toString(),Movie::class.java))
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    (recycler_movies.adapter as AdapterMoviesRecycler).notifyDataSetChanged()
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
    }

    public fun getReloadApi():Unit {
        recycler_movies.visibility = View.VISIBLE
        val serviceVolley = ServiceVolley()
        val apiController = APIController(serviceVolley)
        apiController.get(Utils.getEndpointList()){response ->
            if (response != null){
                val gson = Gson()
                val results:JSONArray = response?.get("results") as JSONArray
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
