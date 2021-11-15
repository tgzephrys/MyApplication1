package com.example.myapplication1.homePage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication1.homePage.model.Repository
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse

class WeatherViewModel: ViewModel() {


    var scrollDistance = MutableLiveData<Int>() //滑动距离

    private val initLocal = MutableLiveData<Any?>()  //改变即代表需要一个海淀区

    private val newCityPlace = MutableLiveData<PlaceResponse.Place>()  //需要一个新地址

    var isInitialized = false

    val places = ArrayList<PlaceResponse.Place>()  //地区列表

    val cities = ArrayList<CityPagerFrag>()

    var placeIndex = 0  //初始化遍历指针


    //拿到地址  得到该地天气, 改变即更新天气
    val savedCityWeather = Transformations.switchMap(newCityPlace) {
        Log.d("listPlace", it.name)
        Repository.refreshWeather(it.location.lng, it.location.lat)
    }

    val localPlace = Transformations.switchMap(initLocal) {
        Repository.searchPlaces("海淀区")
    }

    fun requestWeather(place: PlaceResponse.Place){
        newCityPlace.value = place
    }

    fun initLocal(){
        initLocal.value = initLocal.value
    }

    fun startInit(){
        requestWeather(places[placeIndex])
    }
}