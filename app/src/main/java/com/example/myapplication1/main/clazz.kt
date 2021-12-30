package com.example.myapplication1.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

fun main(){
    GlobalScope.launch() {
        repeat(1000){println(Thread.currentThread())}
    }
    repeat(1000){println(Thread.currentThread())}
}