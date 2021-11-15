package com.example.myapplication1.homePage.model

import android.util.Log
import androidx.lifecycle.liveData
import com.example.myapplication1.homePage.model.netWork.service.serviceImpl.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.Result
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query: String) = fireWall(Dispatchers.IO){
        val placeResponse = NetworkRequest.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            Result.success(places)
        } else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fireWall(Dispatchers.IO){

        Log.d("refreshweathernow: ","$lng, $lat")
        coroutineScope {
            val deferredRealTimeResponse = async {
                NetworkRequest.getRealTimeWeather(lng, lat)
            }
            val deferredDailyResponse = async {
                NetworkRequest.getDailyWeather(lng, lat)
            }
            val deferredHourlyResponse = async {
                NetworkRequest.getHourlyWeather(lng, lat)
            }
            val realTimeResponse = deferredRealTimeResponse.await()
            val dailyResponse = deferredDailyResponse.await()
            val hourlyResponse = deferredHourlyResponse.await()

            if(realTimeResponse.status == "ok" &&
                dailyResponse.status == "ok" &&
                hourlyResponse.status == "ok"){
                val weather = Weather(
                    realTimeResponse.result.realtime,
                    dailyResponse.result.daily,
                    hourlyResponse.result.hourly
                )
                Result.success(weather)
            } else{
                Result.failure(
                    RuntimeException(
                        "realTime response status is ${realTimeResponse.status} " +
                        "daily response status is ${dailyResponse.status}" +
                        "hourly response status is ${hourlyResponse.status}"
                    )
                )
            }
        }
    }

    private fun<T> fireWall(context: CoroutineContext, block: suspend ()-> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception){
                Result.failure(e)
            }
            emit(result)
        }
}