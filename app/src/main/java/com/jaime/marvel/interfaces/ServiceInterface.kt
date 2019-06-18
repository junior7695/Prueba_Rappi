package com.jaime.marvel.interfaces

import org.json.JSONObject

interface ServiceInterface {
    fun get(path: String, completionHandler: (response: JSONObject?) -> Unit)
}