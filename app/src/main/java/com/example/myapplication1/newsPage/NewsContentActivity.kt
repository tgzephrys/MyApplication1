package com.example.myapplication1.newsPage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication1.common.ActivityController
import com.example.myapplication1.R
import kotlinx.android.synthetic.main.activity_content_news.*


class NewsContentActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        fun actionStart(context: Context, title: String, content: String, time: String){
            val intent = Intent(context, NewsContentActivity::class.java)
            intent.apply {
                putExtra("title",title)
                putExtra("content",content)
                putExtra("time",time)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_news)
        ActivityController.addActivity(this)
        supportActionBar?.hide()

        news_title_bar.setText("内容详情")

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")


        if (title != null && time != null && content != null){
            val fragment = newsContentFragment as NewsContentFragment
            fragment.refresh(title, content, time)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }

//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.news_content_container, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
}