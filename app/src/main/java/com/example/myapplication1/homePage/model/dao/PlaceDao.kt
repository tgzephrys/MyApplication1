package com.example.myapplication1.homePage.model.dao

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.myapplication1.MyApplication
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse.Place
import com.google.gson.Gson

object PlaceDao {
    fun savePlace(place: Place){
        if (!isPlaceSaved(place)) {
            sharedPreferences().edit {
                putString(place.id, Gson().toJson(place))
            }
        }
    }

    fun getSavedPlaces(): ArrayList<Place>{
        val sharedPlaces = sharedPreferences().all
        val places = arrayListOf<Place>()

        for ((_, value ) in sharedPlaces){
            Log.d("placeName", Gson().fromJson(value as String, Place::class.java).name)
            places.add(Gson().fromJson(value as String, Place::class.java))
        }
        return places
    }

    fun deletePlace(place: Place){
        Log.d("deleting", place.id)
        if (isPlaceSaved(place)) {
            sharedPreferences().edit(){
                remove(place.id)
            }
        }
    }

    fun isPlaceSaved(place: Place) = sharedPreferences().contains(place.id)

    private fun sharedPreferences() =
        MyApplication.globalContext.getSharedPreferences("cities", Context.MODE_PRIVATE)
}