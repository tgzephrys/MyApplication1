package com.example.myapplication1

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
import kotlinx.android.synthetic.main.test_frag.*

class TestFragment(): Fragment() {

    var scrollView: ScrollView? = null

    private val viewModel: TestViewModel by activityViewModels()

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

//        scroll2.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            viewModel.i.value = scrollY
//        }
    }

    fun tchange(scrollY: Int){
        scrollView?.scrollTo(0,scrollY)
        Log.d("----------","pppppppp")
    }

    fun textChange(string: String){
        green.text = string
    }
}