package com.example.myapplication1.homePage.model


import android.annotation.SuppressLint
import com.example.myapplication1.R
import com.example.myapplication1.homePage.model.netWork.entity.DailyResponse
import java.sql.Time
import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.ArrayList

//将实时天气和图标背景对应
class MapperBg(val skycon: String, val bg: Int, val card_bg: Int)
class MapperSky(val skycon: String, val icon: Int)

private val bgDay = mapOf(
    "CLEAR_DAY" to MapperBg("晴", R.drawable.sunny, R.drawable.sunny_card),
    "CLEAR_NIGHT" to MapperBg("晴",  R.drawable.sunny_night, R.drawable.sunny_card_night),
    "PARTLY_CLOUDY_DAY" to MapperBg("多云",  R.drawable.partly_cloudy, R.drawable.partly_cloudy_card),
    "PARTLY_CLOUDY_NIGHT" to MapperBg("多云",  R.drawable.partly_cloudy_night, R.drawable.parly_cloudy_card_night),
    "CLOUDY" to MapperBg("阴", R.drawable.cloudy, R.drawable.cloudy_card),
    "WIND" to MapperBg("大风", R.drawable.wind, R.drawable.cloudy_card),
    "LIGHT_RAIN" to MapperBg("小雨", R.drawable.rain, R.drawable.rain_card),
    "MODERATE_RAIN" to MapperBg("中雨", R.drawable.rain, R.drawable.rain_card),
    "HEAVY_RAIN" to MapperBg("大雨", R.drawable.rain, R.drawable.rain_card),
    "STORM_RAIN" to MapperBg("暴雨", R.drawable.rain, R.drawable.rain_card),
    "THUNDER_SHOWER" to MapperBg("雷阵雨", R.drawable.rain, R.drawable.rain_card),
    "SLEET" to MapperBg("雨夹雪", R.drawable.rain, R.drawable.rain_card),
    "LIGHT_SNOW" to MapperBg("小雪", R.drawable.snow, R.drawable.snow_card),
    "MODERATE_SNOW" to MapperBg("中雪️", R.drawable.snow, R.drawable.snow_card),
    "HEAVY_SNOW" to MapperBg("大雪", R.drawable.snow, R.drawable.snow_card),
    "STORM_SNOW" to MapperBg("暴雪", R.drawable.snow, R.drawable.snow_card),
    "HAIL" to MapperBg("冰雹", R.drawable.snow, R.drawable.snow_card),
    "LIGHT_HAZE" to MapperBg("轻度霾", R.drawable.haze, R.drawable.haze_card),
    "MODERATE_HAZE" to MapperBg("中度霾", R.drawable.haze, R.drawable.haze_card),
    "HEAVY_HAZE" to MapperBg("重度霾", R.drawable.haze, R.drawable.haze_card),
    "FOG" to MapperBg("雾", R.drawable.fog, R.drawable.fog_card),
    "DUST" to MapperBg("沙尘", R.drawable.fog, R.drawable.fog_card),
    "UNKNOWN" to MapperBg("未知",R.drawable.sunny, R.drawable.sunny_card)
)
private val bgNight = mapOf(
    "CLEAR_DAY" to MapperBg("晴", R.drawable.sunny, R.drawable.sunny_card),
    "CLEAR_NIGHT" to MapperBg("晴", R.drawable.sunny_night, R.drawable.sunny_card_night),
    "PARTLY_CLOUDY_DAY" to MapperBg("多云", R.drawable.partly_cloudy, R.drawable.partly_cloudy_card),
    "PARTLY_CLOUDY_NIGHT" to MapperBg("多云", R.drawable.partly_cloudy_night, R.drawable.parly_cloudy_card_night),
    "CLOUDY" to MapperBg("阴", R.drawable.cloudy, R.drawable.cloudy_card_night),
    "WIND" to MapperBg("大风", R.drawable.wind_night, R.drawable.cloudy_card_night),
    "LIGHT_RAIN" to MapperBg("小雨", R.drawable.rain_night, R.drawable.rain_card_night),
    "MODERATE_RAIN" to MapperBg("中雨", R.drawable.rain_night, R.drawable.rain_card_night),
    "HEAVY_RAIN" to MapperBg("大雨", R.drawable.rain_night, R.drawable.rain_card_night),
    "STORM_RAIN" to MapperBg("暴雨", R.drawable.rain_night, R.drawable.rain_card_night),
    "THUNDER_SHOWER" to MapperBg("雷阵雨", R.drawable.rain_night, R.drawable.rain_card_night),
    "SLEET" to MapperBg("雨夹雪", R.drawable.rain_night, R.drawable.rain_card_night),
    "LIGHT_SNOW" to MapperBg("小雪", R.drawable.snow_night, R.drawable.snow_card_night),
    "MODERATE_SNOW" to MapperBg("中雪️", R.drawable.snow_night, R.drawable.snow_card_night),
    "HEAVY_SNOW" to MapperBg("大雪", R.drawable.snow_night, R.drawable.snow_card_night),
    "STORM_SNOW" to MapperBg("暴雪", R.drawable.snow_night, R.drawable.snow_card_night),
    "HAIL" to MapperBg("冰雹", R.drawable.snow_night, R.drawable.snow_card_night),
    "LIGHT_HAZE" to MapperBg("轻度霾", R.drawable.haze_night, R.drawable.haze_card_night),
    "MODERATE_HAZE" to MapperBg("中度霾", R.drawable.haze_night, R.drawable.haze_card_night),
    "HEAVY_HAZE" to MapperBg("重度霾", R.drawable.haze_night, R.drawable.haze_card_night),
    "FOG" to MapperBg("雾", R.drawable.fog_night, R.drawable.fog_card_night),
    "DUST" to MapperBg("沙尘", R.drawable.fog_night, R.drawable.fog_card_night),
    "UNKNOWN" to MapperBg("未知", R.drawable.sunny, R.drawable.sunny_card_night)
)

private val skyDay = mapOf(
    "CLEAR_DAY" to MapperSky("晴", R.drawable.ic_clear),
    "CLEAR_NIGHT" to MapperSky("晴", R.drawable.ic_clear_night),
    "PARTLY_CLOUDY_DAY" to MapperSky("多云", R.drawable.ic_partly_cloudy),
    "PARTLY_CLOUDY_NIGHT" to MapperSky("多云", R.drawable.ic_partly_cloudy_night),
    "CLOUDY" to MapperSky("阴", R.drawable.ic_cloudy),
    "WIND" to MapperSky("大风", R.drawable.ic_wind),
    "LIGHT_RAIN" to MapperSky("小雨", R.drawable.ic_light_rain),
    "MODERATE_RAIN" to MapperSky("中雨", R.drawable.ic_moderate_rain),
    "HEAVY_RAIN" to MapperSky("大雨", R.drawable.ic_heavy_rain),
    "STORM_RAIN" to MapperSky("暴雨", R.drawable.ic_storm_rain),
    "THUNDER_SHOWER" to MapperSky("雷阵雨", R.drawable.ic_thunder_shower),
    "SLEET" to MapperSky("雨夹雪", R.drawable.ic_sleet),
    "LIGHT_SNOW" to MapperSky("小雪", R.drawable.ic_light_snow),
    "MODERATE_SNOW" to MapperSky("中雪️", R.drawable.ic_moderate_snow),
    "HEAVY_SNOW" to MapperSky("大雪", R.drawable.ic_heavy_snow),
    "STORM_SNOW" to MapperSky("暴雪", R.drawable.ic_storm_snow),
    "HAIL" to MapperSky("冰雹", R.drawable.ic_hail),
    "LIGHT_HAZE" to MapperSky("轻度霾", R.drawable.ic_light_haze),
    "MODERATE_HAZE" to MapperSky("中度霾", R.drawable.ic_moderate_haze),
    "HEAVY_HAZE" to MapperSky("重度霾", R.drawable.ic_heavy_haze),
    "FOG" to MapperSky("雾", R.drawable.ic_fog),
    "DUST" to MapperSky("沙尘", R.drawable.ic_dust),
)


@SuppressLint("SimpleDateFormat")
fun getBg(weather: Weather): MapperBg{

    val sunRise = SimpleDateFormat("HH:mm")
                        .parse(weather.daily.astro[0].sunrise.time)


    val sunSet = SimpleDateFormat("HH:mm")
                        .parse(weather.daily.astro[0].sunset.time)

    val curTime = Date() //系统目前时间

    return if (sunRise!=null && sunSet!=null){
        if (curTime before sunSet && sunRise before curTime){
            bgDay[weather.realTime.skycon]?:bgDay["UNKNOWN"]!!
        }else bgNight[weather.realTime.skycon]?: bgNight["UNKNOWN"]!!
    } else bgDay["UNKNOWN"]!!

}

fun getHourlyList(weather: Weather): ArrayList<HourlySky>{
    val sunRiseToday = SimpleDateFormat("HH:mm")
        .parse(weather.daily.astro[0].sunrise.time)
    val sunSetToday = SimpleDateFormat("HH:mm")
        .parse(weather.daily.astro[0].sunset.time)

    val sunRiseTomorrow = SimpleDateFormat("HH:mm")
        .parse(weather.daily.astro[1].sunrise.time)
    val sunSetTomorrow = SimpleDateFormat("HH:mm")
        .parse(weather.daily.astro[1].sunset.time)

    val dateToday = weather.hourly.skycon[0].datetime


    var hourlyList = ArrayList<HourlySky>()
    for (i in 1..27){
        var sky = skyDay[weather.hourly.skycon[i-1].value]!!
        var time = weather.hourly.skycon[i-1].datetime.date2timeStr()

        if (dateToday before weather.hourly.skycon[i-1].datetime){
            if(weather.hourly.skycon[i-1].datetime.hours  ==
                    sunRiseTomorrow.hours + 1){
                hourlyList.add(
                    HourlySky(
                        sunRiseTomorrow.date2timeStr(),
                        R.drawable.sun_rise_ic,
                        "日出"
                        )
                )
            } else if (weather.hourly.skycon[i-1].datetime.hours  ==
                sunSetTomorrow.hours + 1){
                hourlyList.add(
                    HourlySky(
                        sunSetTomorrow.date2timeStr(),
                        R.drawable.sun_set_ic,
                        "日落")
                )
            }
        } else{
            if(weather.hourly.skycon[i-1].datetime.hours  ==
                sunRiseToday.hours + 1){
                hourlyList.add(
                    HourlySky(
                        sunRiseToday.date2timeStr(),
                        R.drawable.sun_rise_ic,
                        "日出")
                )
            } else if (weather.hourly.skycon[i-1].datetime.hours  ==
                sunSetToday.hours + 1){
                hourlyList.add(
                    HourlySky(
                        sunSetToday.date2timeStr(),
                        R.drawable.sun_set_ic,
                        "日落")
                )
            }
        }
        hourlyList.add(HourlySky(time, sky.icon, sky.skycon))
    }
    return hourlyList
}

fun getDailyList(weather: Weather): ArrayList<DailySky>{
    var dailyList = ArrayList<DailySky>()
    for (i in weather.daily.skycon.indices){
        var sky = skyDay[weather.daily.skycon[i].value]!!
        var date = weather.daily.skycon[i].date
        dailyList.add(DailySky(
            date.day.toWeekDay(),
            "${(date.month+1).format()}/${date.date.format()}",
            sky.icon,
            sky.skycon,
            weather.daily.airQuality.aqi[i].avg.chn.aqi2info(),
            weather.daily.temperature[i]
        ))
    }
    return dailyList
}

fun getAirInfoList(weather: Weather): ArrayList<AirInfo>{
    val airInfoList = ArrayList<AirInfo>()
    airInfoList.apply {
        add(AirInfo("PM10", weather.realTime.airQuality.pm10))
        add(AirInfo("PM2.5",weather.realTime.airQuality.pm25))
        add(AirInfo("NO2",weather.realTime.airQuality.no2))
        add(AirInfo("SO2",weather.realTime.airQuality.so2))
        add(AirInfo("O3",weather.realTime.airQuality.o3))
        add(AirInfo("CO",weather.realTime.airQuality.co))
    }
    return airInfoList
}

fun getLifeIndexList(weather: Weather): ArrayList<LifeIndex>{
    val lifeIndexList = ArrayList<LifeIndex>()
    lifeIndexList.apply {
        add(LifeIndex(R.drawable.humidity_ic, "湿度", weather.daily.humidity[0].avg.toString()))
        add(LifeIndex(R.drawable.temper_ic,"体感温度", "${weather.realTime.apparent_temperature.toString()}℃"))
        add(LifeIndex(R.drawable.wind_power_ic,"风力",weather.realTime.wind.speed.toString()))
        add(LifeIndex(R.drawable.wind_direction_ic,"风向",weather.realTime.wind.direction.toString()))
        add(LifeIndex(R.drawable.dressing_ic,"穿衣",weather.daily.lifeIndex.dressing[0].desc))
        add(LifeIndex(R.drawable.comfort_ic, "舒适度", weather.daily.lifeIndex.comfort[0].desc))
        add(LifeIndex(R.drawable.cold_risk_ic,"感冒",weather.daily.lifeIndex.coldRisk[0].desc))
        add(LifeIndex(R.drawable.ultraviolet_ic, "紫外线", weather.daily.lifeIndex.ultraviolet[0].desc))
        add(LifeIndex(R.drawable.car_washing_ic, "洗车", weather.daily.lifeIndex.carWashing[0].desc))
    }
    return lifeIndexList
}

class DailySky(val day: String, val date: String, val icon: Int, val skycon: String, val airInfo: String,val index: DailyResponse.Index)
class HourlySky(val time: String, val icon: Int, val temper: String)
class AirInfo(val gasName: String, val index: Float)
class LifeIndex(val icon: Int, val itemName: String, val desc: String)
infix fun Date.before(time: Date) = before(time)

fun Int.format(): String {
    return if (this in 0..9) "0$this"
            else this.toString()
}
fun Date.date2timeStr() =  Time(this.time).toString().substring(0,5)

fun Int.toWeekDay(): String {
    return when(this){
        0 -> "星期日"
        1 -> "星期一"
        2 -> "星期二"
        3 -> "星期三"
        4 -> "星期四"
        5 -> "星期五"
        6 -> "星期六"
        else -> "星期一"
    }
}

fun Float.aqi2info(): String{
    return if(this in 0F..50F) "优"
            else if (this in 51F..100F) "良"
            else if (this in 101F..150F) "中"
            else if (this in 151F..200F) "差"
            else if (this in 201F..300F) "重"
            else "严重"
}