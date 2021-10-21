package com.example.myapplication1.common.mUtils

class MyIterator<T>(var mList: ArrayList<T>) {
    private var mIterator = mList.iterator()

    fun circle(): T{
        return if (mIterator.hasNext()){
            mIterator.next()
        }else {
            mIterator = mList.iterator()
            mIterator.next()
        }
    }
}