package com.example.myapplication1.homePage.model.netWork.entity

import com.google.gson.annotations.SerializedName

data class RealTimeResponse(val status: String, val result: Result){

    data class Result(val realtime: RealTime)

    data class RealTime(val skycon: String,   //天气描述
                        val temperature: Float,
                        val humidity: Float,   //湿度
                        @SerializedName("air_quality") val ariQuality: AirQuality,
                        val wind: Wind,
                        val apparent_temperature: Float)//体表温度

    data class AirQuality(val pm25: Float,
                          val pm10: Float,
                          val o3: Float,
                          val so2: Float,
                          val no2: Float,
                          val co: Float,
                          val aqi: AQI,
                          val description: Description
    )

    data class AQI(val chn: Float)  //空气污染指数
    data class Description(val chn: String)   //空气污染描述

    data class Wind(val speed: Float, val direction: Float)

}

