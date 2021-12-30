package com.example.myapplication1

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.test_frag.*

class TestFragment(): Fragment() {

    var scrollView: ScrollView? = null

    private val vm: TestViewModel by viewModels()
    private val vm2 = TestViewModel()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.test_frag, container, false)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollView = view.findViewById(R.id.scroll2)

        Log.d("lifeCycle---", "onViewCreated")
//        scroll2.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            viewModel.i.value = scrollY
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("lifeCycle---", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifeCycle---", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle---", "onResume")
    }

    override fun onPause() {
        super.onPause()
        green.setBackgroundColor(Color.RED)
        Log.d("lifeCycle---", "onPause")
    }

    fun tchange(scrollY: Int){
        scrollView?.scrollTo(0,scrollY)
        Log.d("----------","pppppppp")
    }

    fun textChange(string: String){
        green.text = string
    }
}