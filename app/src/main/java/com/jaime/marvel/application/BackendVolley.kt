package com.jaime.marvel.application

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley

class BackendVolley : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

                // Set up the network to use HttpURLConnection as the HTTP client.
                val network = BasicNetwork(HurlStack())
                return RequestQueue(cache, network).apply {
                    start()
                }
            }
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private val TAG = BackendVolley::class.java.simpleName
        @get:Synchronized var instance: BackendVolley? = null
            private set
    }
}