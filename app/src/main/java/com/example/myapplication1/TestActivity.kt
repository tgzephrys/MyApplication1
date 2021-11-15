package com.example.myapplication1

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.homePage.common.dp2px
import com.example.myapplication1.homePage.common.getStatusBarHeight
import com.example.myapplication1.homePage.common.px2dp
import com.example.myapplication1.homePage.mView.DailyLineChart
import com.example.myapplication1.main.MainActivity2
import com.example.myapplication1.main.MyContact
import kotlinx.android.synthetic.main.test_activity.*
import kotlinx.android.synthetic.main.test_frag.*

class TestActivity : AppCompatActivity() {

    val list = ArrayList<TestFragment>()
    var i = 0

    val man = supportFragmentManager
    val fragment = TestFragment()

    private val viewModel by lazy {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)

        repeat(6) {
            list.add(TestFragment())
        }


        //mView.drawLines(intArrayOf(16, 2, 16, -1, 11, -1, 16, 0, 0))

        val myAdapter = MyAdapter(this)
        holder.offscreenPageLimit = list.size
        holder.adapter = myAdapter


        mViewHolder.setOnClickListener {
            list.forEach {
                it.textChange(i.toString())
                i++
            }
            Log.d("changed: ","changed")
        }

        red.setOnClickListener {
            mView.drawView(63, "良")
        }

        holder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

            }
        })


        s1.setOnClickListener {

            window.statusBarColor = resources.getColor(R.color.light_gary)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }


//        viewModel.i.observe(this, { scrollY ->
//            list.forEach {
//                it.tchange( scrollY)
//            }
//        })
    //viewpager2.adapter = MyAdapter(this)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        return super.dispatchTouchEvent(ev)
    }


    inner class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return list.size
        }

        override fun createFragment(position: Int): Fragment {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return list[position].hashCode().toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return LongArray(list.size){
                list[it].hashCode().toLong()
            }.contains(itemId)
        }
    }

//    inner class MyAdapter():
//        RecyclerView.Adapter<MyAdapter.ViewHolder>() {
//
//        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//            var scrollView: ScrollView = view.findViewById(R.id.scroll2)
//        }
//
//        @RequiresApi(Build.VERSION_CODES.M)
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.test_frag, parent, false)
//
//            val viewHolder = ViewHolder(view)
//            viewHolder.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//                viewModel.i = scrollY
//                Log.d("scrollY: ","$scrollY")
//            }
//
//            return viewHolder
//        }
//
//        override fun getItemCount() = viewModel.list.size
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            holder.scrollView.scrollTo(0,viewModel.i)
//        }
//    }
}