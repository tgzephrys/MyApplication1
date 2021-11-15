package com.example.myapplication1.homePage.model.netWork.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyResponse(val status: String, val result: Result){

    data class Result(val daily: Daily)

    data class Daily(val astro: List<Astro>,
                     val temperature: List<Index>,
                     val humidity: List<Index>,
                     val cloudrate: List<Index>,
                     val pressure: List<Index>,
                     val visibility: List<Index>,
                     @SerializedName("air_quality")val airQuality: AirQuality,
                     val pm25: List<Index>,
                     val skycon: List<Skycon>,
                     @SerializedName("life_index")val lifeIndex: LifeIndex
    )

    data class LifeIndex(val ultraviolet: List<LifeDesc>,
                         val carWashing: List<LifeDesc>,
                         val dressing: List<LifeDesc>,
                         val comfort: List<LifeDesc>,
                         val coldRisk: List<LifeDesc>)

    data class Astro(val sunrise: SunRiseSet, val sunset: SunRiseSet)
    data class SunRiseSet(val time: String)

    //用以描述weather参数中的三个数值
    data class Index(val max: Float, val min: Float, val avg: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeDesc(val index: Float, val desc: String)

    data class AirQuality(val aqi: List<Aqi>) //空气质量
    data class Aqi(val min: AirIndex, val max: AirIndex, val avg: AirIndex)
    data class AirIndex(val chn: Float)  //空气质量参数
}

