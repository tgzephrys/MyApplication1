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
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circleRadius = 0
    private var cLeft = 0
    private var cTop = 0
    private var cRight = 0
    private var cBottom = 0
    private var marginBottom = 16f.toPx()
    private var usableHeight = 0


    private val lineWidth = 10f.toPx()

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onDraw(canvas: Canvas?) {
        paint.style = Paint.Style.STROKE
        paint.color = resources.getColor(R.color.light_gary)
        paint.strokeWidth = lineWidth.toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        canvas?.drawArc(
            cLeft.toFloat(), cTop.toFloat(),
            cRight.toFloat(), cBottom.toFloat(),
            120f, 300f,
            false, paint
        )

        when(aqi){
            in 0..50 -> paint.color = resources.getColor(R.color.light_green)
            in 51..100 -> paint.color = resources.getColor(R.color.light_yellow)
            in 101..150 -> paint.color = resources.getColor(R.color.light_brown)
            else -> paint.color = resources.getColor(R.color.moderate_brown)
        }
        canvas?.drawArc(
            cLeft.toFloat(), cTop.toFloat(),
            cRight.toFloat(), cBottom.toFloat(),
            120f, ((aqi / 500) * 300).toFloat(),
            false, paint
        )

        paint.color = Color.WHITE
        paint.textSize = 14f.toPx().toFloat()
        canvas?.drawText(desc, centerX.toFloat(), centerY - 20F.toPx().toFloat(), paint)
        paint.textSize = 30f.toPx().toFloat()
        canvas?.drawText(aqi.toString(), centerX.toFloat(), centerY - 14f.toPx().toFloat(), paint)

        paint.textSize = 12f.toPx().toFloat()
        paint.color = resources.getColor(R.color.light_gary)
        canvas?.drawText("0", (circleRadius / 2).toFloat(),
            (centerY + circleRadius).toFloat(), paint)
        canvas?.drawText("500", circleRadius + (circleRadius / 2).toFloat(),
            (centerY + circleRadius).toFloat(), paint)
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