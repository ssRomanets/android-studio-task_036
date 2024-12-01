package com.example.task_036

import com.example.task_036.models.ApiData
import retrofit2.Response

import retrofit2.http.GET

interface ApiInterface {
    @GET("woof.json?ref=apilist.fun") suspend fun getRandomDog(): Response<ApiData>
}