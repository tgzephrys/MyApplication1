package com.example.myapplication1.newsPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import kotlinx.android.synthetic.main.activity_news.*
import okhttp3.OkHttpClient

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.hide()
        ActivityController.addActivity(this)
        title_bar.initBar(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

}
