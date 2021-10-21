package com.example.myapplication1.common.mControls

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewParent
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.R
import kotlinx.android.synthetic.main.title_bar.view.*

class TitleBarLayout(context: Context, attr: AttributeSet): LinearLayout(context, attr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this)
    }

    fun setText(text: String){
        title_name.text = text
    }

    fun setTextColor(color: Int){
        title_name.setTextColor(color)
    }

    fun initBar(parent: Activity){
        title_name.text = parent.intent.getStringExtra("activity_name")    }
}