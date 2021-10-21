package com.example.myapplication1.common.mUtils

import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object HttpUtil {
    fun sendOkHttpRequest(url: String, callback: Callback){
        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(callback)
    }
}

suspend fun simpleRequest(url: String): String{
    return suspendCoroutine {
        HttpUtil.sendOkHttpRequest(url, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                it.resume(response.toString())
            }
        })
    }
}