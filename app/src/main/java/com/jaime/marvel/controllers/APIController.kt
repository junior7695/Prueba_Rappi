package com.jaime.marvel.controllers

import com.jaime.marvel.interfaces.ServiceInterface
import org.json.JSONObject

class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {

    private val service: ServiceInterface = serviceInjection


    override fun get(path: String, completionHandler: (response: JSONObject?) -> Unit) {
        service.get(path, completionHandler)
    }

}