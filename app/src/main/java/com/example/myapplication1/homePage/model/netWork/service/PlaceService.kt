package com.example.myapplication1.homePage.model.netWork.service


import com.example.myapplication1.homePage.common.TOKEN
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?&token=${TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") query: String): Call<PlaceResponse>
}