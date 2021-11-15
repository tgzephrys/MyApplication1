package com.example.myapplication1.homePage.model

import com.example.myapplication1.homePage.model.netWork.entity.DailyResponse
import com.example.myapplication1.homePage.model.netWork.entity.HourlyResponse
import com.example.myapplication1.homePage.model.netWork.entity.RealTimeResponse
import java.io.Serializable

data class Weather(val realTime: RealTimeResponse.RealTime,
                   val daily: DailyResponse.Daily,
                   val hourly: HourlyResponse.Hourly)