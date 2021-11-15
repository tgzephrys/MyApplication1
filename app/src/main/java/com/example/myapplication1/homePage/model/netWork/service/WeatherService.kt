package com.example.myapplication1.homePage.model.netWork.service

import com.example.myapplication1.homePage.common.TOKEN
import com.example.myapplication1.homePage.model.netWork.entity.DailyResponse
import com.example.myapplication1.homePage.model.netWork.entity.HourlyResponse
import com.example.myapplication1.homePage.model.netWork.entity.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${TOKEN}/{lng},{lat}/realtime.json")
    fun getRealTimeWeather(@Path("lng")lng:String,
                           @Path("lat")lat:String): Call<RealTimeResponse>

    @GET("v2.5/${TOKEN}/{lng},{lat}/hourly.json")
    fun getHourlyWeather(@Path("lng")lng:String,
                         @Path("lat")lat:String): Call<HourlyResponse>

    @GET("v2.5/${TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng:String,
                        @Path("lat")lat:String): Call<DailyResponse>
}