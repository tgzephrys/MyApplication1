package com.example.myapplication1.homePage.model.netWork.entity

import com.google.gson.annotations.SerializedName


data class PlaceResponse(val status: String, val places: List<Place>){

    data class Location(val lng:String, val lat: String)

    data class Place(val id: String, val name: String, val location: Location,
                     @SerializedName("formatted_address")val address: String,
                     @SerializedName ("place_id") val placeID: String)
}