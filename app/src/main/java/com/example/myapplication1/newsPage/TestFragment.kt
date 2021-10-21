package com.example.myapplication1.newsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication1.R
import com.example.myapplication1.common.NewsFragmentController

class TestFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        NewsFragmentController.addFragment(this)
        return inflater.inflate(R.layout.fragment_test, container, false)
    }
}