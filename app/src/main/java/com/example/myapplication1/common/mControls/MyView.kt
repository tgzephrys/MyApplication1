package com.example.myapplication1.common.mControls

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.myapplication1.R

class MyView(context: Context, attr: AttributeSet) :View(context, attr) {



//    init {
//        LayoutInflater.from(context).inflate(R.layout.ac_test, this)
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(200F,200F,100F, Paint())
        println("canvas")
        Log.d("Canvas", canvas.toString())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(500,500)
    }

}