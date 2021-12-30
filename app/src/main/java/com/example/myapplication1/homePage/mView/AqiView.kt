package com.example.myapplication1.homePage.mView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.myapplication1.R
import com.example.myapplication1.homePage.common.toPx

class AqiView: View {

    private var aqi = 0
    private var desc = ""
    private var centerX = 0
    private var centerY = 0

    private var circleRadius = 0
    private var cLeft = 0
    private var cTop = 0
    private var cRight = 0
    private var cBottom = 0
    private var marginBottom = 12f.toPx()
    private var usableHeight = 0


    private val lineWidth = 13f.toPx()

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onDraw(canvas: Canvas?) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeCap = Paint.Cap.ROUND

        paint.color = Color.WHITE
        paint.textSize = 14f.toPx().toFloat()
        canvas?.drawText(desc, centerX.toFloat() - 6f.toPx(), centerY - 14F.toPx().toFloat(), paint)
        paint.textSize = 26f.toPx().toFloat()
        canvas?.drawText(aqi.toString(), centerX.toFloat() - 14f.toPx(), centerY + 20f.toPx().toFloat(), paint)

        paint.textSize = 12f.toPx().toFloat()
        paint.color = resources.getColor(R.color.light_grey)
        canvas?.drawText("0",
            (circleRadius / 2).toFloat() - 5f.toPx(),
            (centerY + circleRadius).toFloat() - 5f.toPx(), paint)
        canvas?.drawText("500",
            (circleRadius + (circleRadius / 2)).toFloat() - 5f.toPx(),
            (centerY + circleRadius).toFloat() - 5f.toPx(), paint)

        paint.strokeWidth = lineWidth.toFloat()
        paint.style = Paint.Style.STROKE

        when(aqi){
            in 0..50 -> paint.color = resources.getColor(R.color.light_green)
            in 51..100 -> paint.color = resources.getColor(R.color.light_yellow)
            in 101..150 -> paint.color = resources.getColor(R.color.light_brown)
            else -> paint.color = resources.getColor(R.color.moderate_brown)
        }
        canvas?.drawArc(
            cLeft.toFloat(), cTop.toFloat(),
            cRight.toFloat(), cBottom.toFloat(),
            135f, (aqi / 500f) * 270f,
            false, paint
        )

        paint.color = Color.parseColor("#32F9FbFD")
        canvas?.drawArc(
            cLeft.toFloat(), cTop.toFloat(),
            cRight.toFloat(), cBottom.toFloat(),
            135f, 270f,
            false, paint
        )

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        usableHeight = measuredHeight - marginBottom

        centerX = measuredWidth / 2
        centerY = usableHeight / 2

        if (usableHeight > measuredWidth) {
            circleRadius = measuredWidth / 2
            cLeft = 10f.toPx()
            cTop = centerY - circleRadius + 10f.toPx()
            cRight = measuredWidth - 10f.toPx()
            cBottom = centerY + circleRadius - 10f.toPx()
        }
        else {
            circleRadius = usableHeight / 2
            cLeft = centerX - circleRadius + 10f.toPx()
            cTop = 10f.toPx()
            cRight = circleRadius + centerX - 10f.toPx()
            cBottom = usableHeight - 10f.toPx()
        }
    }



    fun drawView(aqi: Int, desc: String){
        this.aqi = aqi
        this.desc = desc
        invalidate()
    }
}