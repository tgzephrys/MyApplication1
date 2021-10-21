package com.example.myapplication1.newsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication1.R
import com.example.myapplication1.common.NewsFragmentController
import kotlinx.android.synthetic.main.fragment_news_content.*
import kotlinx.android.synthetic.main.fragment_news_content.news_content
import kotlinx.android.synthetic.main.fragment_news_content.news_title
import kotlinx.android.synthetic.main.fragment_news_content.post_time

class NewsContentFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        NewsFragmentController.addFragment(this)
        return inflater.inflate(R.layout.fragment_news_content, container, false)
    }

    fun refresh(title: String, content: String, time: String){
        frag_news_content.visibility = View.VISIBLE
        news_title.text = title
        news_content.text = content
        post_time.text = time
    }
}