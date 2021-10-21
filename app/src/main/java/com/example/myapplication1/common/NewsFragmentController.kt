package com.example.myapplication1.common

import androidx.fragment.app.Fragment

object NewsFragmentController {
    var fragmentList = arrayListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    fun removeFragment(fragment: Fragment) {
        fragmentList.remove(fragment)
    }
}