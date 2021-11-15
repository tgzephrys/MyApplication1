package com.example.myapplication1.homePage.common

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplication1.MyApplication

fun getStatusBarHeight(context: Context):Int {
    var result = 0
    var resID = context.resources
        .getIdentifier("status_bar_height", "dimen", "android")
    return if (resID > 0){
        result = context.resources.getDimensionPixelOffset(resID)
        result
    } else result
}

//dp值转px值
fun dp2px(context: Context, dp: Float) = (dp * ( context.resources.displayMetrics.density ) + 0.5f).toInt()
//px值转dp值
fun px2dp(context: Context, dp: Float) = (dp / ( context.resources.displayMetrics.density ) + 0.5f)

fun Float.toPx() = dp2px(MyApplication.globalContext, this)
fun Float.toDp() = px2dp(MyApplication.globalContext, this)