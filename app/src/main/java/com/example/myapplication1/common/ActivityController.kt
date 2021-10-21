package com.example.myapplication1.common

import androidx.appcompat.app.AppCompatActivity

object ActivityController{
    var activityList = arrayListOf<AppCompatActivity>()

    fun quitApplication(){
        activityList.forEach{
            it.finish()
        }
    }

    fun addActivity(ac:AppCompatActivity){
        activityList.add(ac)
    }

    fun removeActivity(ac:AppCompatActivity){
        activityList.remove(ac)
    }
}