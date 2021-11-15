package com.example.myapplication1.homePage.model.netWork.entity

import java.util.*

data class HourlyResponse(val result: Result, val status: String){

    data class Result(val hourly: Hourly)

    data class Hourly(val temperature: List<Temperature>,
                      val skycon: List<Skycon>)

    data class Temperature(val datetime: Date, val value: Float)

    data class Skycon(val datetime: Date, val value: String)
}