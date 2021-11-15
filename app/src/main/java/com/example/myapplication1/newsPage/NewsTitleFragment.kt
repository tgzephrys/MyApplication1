package com.example.myapplication1.newsPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news_title.*
import kotlinx.android.synthetic.main.news_title_list_item.*
import java.lang.StringBuilder
import kotlin.properties.Delegates

class NewsTitleFragment: Fragment() {

    private var isTwoPage = false


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_title, container, false)

    }

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isTwoPage = activity?.findViewById<View>(R.id.frag_news_content) != null

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        title_recyclerview.layoutManager = layoutManager
        title_recyclerview.adapter = NewsTitleAdapter(getNews())
    }


    inner class NewsTitleAdapter(val newsList: List<News>):
        RecyclerView.Adapter<NewsTitleAdapter.ViewHolder>(){
        var selectedList = Array(itemCount){false}

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var newsTitleImage: ImageView = view.findViewById(R.id.news_title_image)
            var newsTitle: TextView = view.findViewById(R.id.news_title)
            var newsContent: TextView = view.findViewById(R.id.news_content)
            var time: TextView = view.findViewById(R.id.post_time)
            val wholeView: View = view.findViewById(R.id.whole_view)
            val selectedView: View = view.findViewById(R.id.selected)
        }

        //@SuppressLint("ResourceAsColor")
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_title_list_item, parent, false)

            val holder = ViewHolder(view)

            holder.wholeView.setOnClickListener{

            }

            return holder
        }

        override fun getItemCount() = newsList.size

        //@SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: NewsTitleAdapter.ViewHolder, position: Int) {
            holder.newsTitleImage.setImageResource(newsList[position].image)
            holder.newsTitle.text = newsList[position].title
            holder.newsContent.text = newsList[position].content
            holder.time.text = newsList[position].time
            if(selectedList[position]){
                holder.selectedView.visibility = View.VISIBLE
            }else{
                holder.selectedView.visibility = View.INVISIBLE
            }

            holder.wholeView.setOnClickListener {
                val news = newsList[holder.adapterPosition]
                for (i in 1..itemCount){
                    selectedList[i-1] = false
                }
                selectedList[position] = true
                notifyDataSetChanged()

                if(isTwoPage) {
                    val fragment = newsContentFrag as NewsContentFragment
                    fragment.refresh(news.title, news.content, news.time)
                }else {
                    NewsContentActivity.actionStart(whole_view.context, news.title, news.content, news.time)
                }
            }

        }

    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for(i in 1..50){
            val news = News("This is news title $i", getRandomStr("This is news content $i"), "16:30", R.drawable.good)
            newsList.add(news)
        }
        return newsList
    }
    private fun getRandomStr(str: String) :String{
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(str)
        }

        return builder.toString()
    }
}