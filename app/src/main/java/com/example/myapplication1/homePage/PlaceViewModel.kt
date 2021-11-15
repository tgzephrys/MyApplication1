package com.example.myapplication1.homePage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse.Place
import com.example.myapplication1.homePage.model.Repository
import com.example.myapplication1.homePage.model.Weather
import java.lang.ref.SoftReference

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    val choosingPlace = MutableLiveData<Place>()

    val placeList = arrayListOf<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query->
        Repository.searchPlaces(query)
    }
    val weatherLiveData =Transformations.switchMap(choosingPlace){
        Repository.refreshWeather(it.location.lng, it.location.lat)
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
    fun getWeather(place: Place){
        choosingPlace.value = place
        Log.d("place: ", "${place.location.lng}, ${place.location.lat}")
    }

}