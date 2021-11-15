package com.example.myapplication1.common.mUI

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

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

}