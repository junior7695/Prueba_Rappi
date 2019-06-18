package com.jaime.marvel.services

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.jaime.marvel.application.BackendVolley
import com.jaime.marvel.interfaces.ServiceInterface
import com.jaime.marvel.utils.Utils
import org.json.JSONObject
import java.nio.charset.Charset
import android.R.attr.data
import com.android.volley.NoConnectionError
import com.android.volley.VolleyError



class ServiceVolley : ServiceInterface {

    val TAG = "ServiceVolley"

    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.GET, Utils.baseUrlApi + path, null,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/post request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization","Bearer "+Utils.accessTokken)
                return headers
            }
            override fun deliverError(error: VolleyError) {
                if (error is NoConnectionError) {
                    val entry = this.cacheEntry
                    if (entry != null) {
                        val response = parseNetworkResponse(NetworkResponse(entry.data, entry.responseHeaders))
                        deliverResponse(response.result)
                        return
                    }
                }
                super.deliverError(error)
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
}