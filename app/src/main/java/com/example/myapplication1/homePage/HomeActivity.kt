package com.example.myapplication1.homePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController

class HomeActivity : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.test_activity)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.test_activity)

        supportActionBar?.hide()
        ActivityController.addActivity(this)

        //home_title_bar.initBar(this)



    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

}