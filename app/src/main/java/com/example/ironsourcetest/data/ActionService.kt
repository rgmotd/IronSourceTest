package com.example.ironsourcetest.data

import com.example.ironsourcetest.data.ActionData
import retrofit2.Response
import retrofit2.http.GET

interface ActionService {

    @GET("/androidexam/butto_to_action_config.json")
    suspend fun getActions(): Response<List<ActionData>>
}