package com.example.myapplication1.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object ActivityController{
    var activityList = arrayListOf<FragmentActivity>()

    fun quitApplication(){
        activityList.forEach{
            it.finish()
        }
    }

    fun addActivity(ac:FragmentActivity){
        activityList.add(ac)
    }

    fun removeActivity(ac:FragmentActivity){
        activityList.remove(ac)
    }
}