package com.example.myapplication1

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication1.homePage.WeatherViewModel
import com.example.myapplication1.homePage.model.Weather
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse
import java.lang.ref.SoftReference

class TestViewModel:ViewModel() {
    inner class TestClass(val name: String)
    inner class Test2(val str: String)

    var i = MutableLiveData<Int>()

    var list = ArrayList<TestFragment>()
    var weather = MutableLiveData<ArrayList<Test2>>()


//    val listWather: LiveData<ArrayList<Test2>> = Transformations.switchMap(list){
//        MutableLiveData<ArrayList<Test2>>()
//    }

    var addOrNot = 0
    inner class page(val hourly: ArrayList<String>, val daily: ArrayList<String>, val realtime: ArrayList<String>)
    var hourly = ArrayList<String>()
    var daily = ArrayList<String>()
    var realtime = ArrayList<String>()
    var places = ArrayList<String>()

}