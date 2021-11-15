package com.example.myapplication1.homePage.mView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.example.myapplication1.homePage.common.toPx

class DailyLineChart : View {

    // [topTemper, bottomTemper, maxTemper, minTemper,
    // nextTop, nextBottom, lastTop, lastBottom,
    // index = 0/1/2]

    constructor(context: Context):super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)


    private var positions = FloatArray(9){it.toFloat()}
    private var topTemper = positions[0]
    private var bottomTemper = positions[1]
    private var maxTemper = positions[2]
    private var minTemper = positions[3]
    private var nextTop = positions[4]
    private var nextBottom = positions[5]
    private var lastTop = positions[6]
    private var lastBottom = positions[7]
    private var index = positions[8]

    var inCircleColor = Color.parseColor("#FFFFFFFF")
    var outCircleColor = Color.parseColor("#44F4F4F4")
    var lineColor = Color.parseColor("#FFCCCCCC")

    var midHorizontal = 0

    var blockHeight = 0
    var topTemperPositionY = 0
    var bottomTemperPositionY = 0

    var topHeight = 32F.toPx()


    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var path = Path()

    override fun onDraw(canvas: Canvas?) {

        paint.style = Paint.Style.STROKE

        if (index == 1f){  //最左边，向右画
            drawNext()
            canvas?.drawPath(path, paint)
        } else if(index == 2f){ //最右边，向左画
            drawLast()
            canvas?.drawPath(path, paint)
        } else {
            drawAll()
            canvas?.drawPath(path, paint)
        }


        paint.style = Paint.Style.FILL

        paint.color = inCircleColor
        canvas?.drawCircle(
            midHorizontal.toFloat(),
            topTemperPositionY.toFloat(),
            1.5F.toPx().toFloat(),
            paint)

        paint.color = outCircleColor
        canvas?.drawCircle(
            midHorizontal.toFloat(),
            topTemperPositionY.toFloat(),
            3F.toPx().toFloat(),
            paint)

        //最低温度图
        paint.color = inCircleColor
        canvas?.drawCircle(
            midHorizontal.toFloat(),
            bottomTemperPositionY.toFloat(),
            1.5F.toPx().toFloat(),
            paint)

        paint.color = outCircleColor
        canvas?.drawCircle(
            midHorizontal.toFloat(),
            bottomTemperPositionY.toFloat(),
            3F.toPx().toFloat(),
            paint)

        paint.color = inCircleColor
        paint.textSize = 15F.toPx().toFloat()

        val topTemperStr = topTemper.toInt().toString()
        val bottomTemperStr = bottomTemper.toInt().toString()
        if(topTemperStr.length >= 2){
            canvas?.drawText(
                "$topTemperStr°",
                midHorizontal.toFloat() - 10F.toPx(),
                (topTemperPositionY - (9F.toPx())).toFloat(),
                paint
            )
        } else {
            canvas?.drawText(
                "$topTemperStr°",
                midHorizontal.toFloat() - 4.8F.toPx(),
                (topTemperPositionY - (9F.toPx())).toFloat(),
                paint
            )
        }

        if (bottomTemperStr.length >= 2){
            canvas?.drawText(
                "$bottomTemperStr°",
                midHorizontal.toFloat() - 10F.toPx(),
                (bottomTemperPositionY + (20F.toPx())).toFloat(),
                paint
            )
        }else{
            canvas?.drawText(
                "$bottomTemperStr°",
                midHorizontal.toFloat() - 4.8F.toPx(),
                (bottomTemperPositionY + (20F.toPx())).toFloat(),
                paint
            )
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        midHorizontal = measuredWidth/2

        blockHeight = (measuredHeight - (2 * topHeight) ) / (maxTemper - minTemper).toInt()

        topTemperPositionY = ((maxTemper-topTemper) * blockHeight + topHeight).toInt()
        bottomTemperPositionY = ((maxTemper-bottomTemper)*blockHeight+topHeight).toInt()
    }


    private fun drawNext(){
        paint.color = lineColor
        paint.strokeWidth = 1.8F.toPx().toFloat()

        path.moveTo(midHorizontal.toFloat(), topTemperPositionY.toFloat())
        path.lineTo(
            (measuredWidth + midHorizontal).toFloat(),
            ((maxTemper - nextTop)*blockHeight +topHeight).toFloat()
        )

        path.moveTo(midHorizontal.toFloat(), bottomTemperPositionY.toFloat())
        path.lineTo(
            (measuredWidth + midHorizontal).toFloat(),
            ((maxTemper - nextBottom)*blockHeight +topHeight).toFloat()
        )
    }
    private fun drawLast(){
        paint.color = lineColor
        paint.strokeWidth = 1.8F.toPx().toFloat()
        path.moveTo(midHorizontal.toFloat(), topTemperPositionY.toFloat())
        path.lineTo(
            (-midHorizontal).toFloat(),
            ((maxTemper - lastTop)*blockHeight +topHeight).toFloat()
        )

        path.moveTo(midHorizontal.toFloat(), bottomTemperPositionY.toFloat())
        path.lineTo(
            (-midHorizontal).toFloat(),
            ((maxTemper - lastBottom)*blockHeight +topHeight).toFloat()
        )

    }
    private fun drawAll(){
        drawLast()
        drawNext()
    }

    fun drawLines(positions: FloatArray){
        this.positions = positions
        topTemper = positions[0]
        bottomTemper = positions[1]
        maxTemper = positions[2]
        minTemper = positions[3]
        nextTop = positions[4]
        nextBottom = positions[5]
        lastTop = positions[6]
        lastBottom = positions[7]
        index = positions[8]
        invalidate()
    }
}