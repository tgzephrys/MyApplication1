package com.example.myapplication1

import android.graphics.Outline
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.homePage.common.toDp
import com.example.myapplication1.homePage.common.toPx
import kotlinx.android.synthetic.main.test_activity.*
import kotlinx.android.synthetic.main.test_activity.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


object ShadowCompatPlus {
    fun setShadow(view: View, alpha: Float, elevationDp: Float, radius: Int, horOffset: Int = 0, verOffset: Int = 0) {
        val provider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0,
                    0,
                    view.width,
                    view.height,
                    radius.toFloat())
                outline.alpha = alpha
                Log.d("viewWidth","${view.width}dp ${view.height}dp")
            }
        }
        view.clipToOutline = true
        view.outlineProvider = provider
        ViewCompat.setElevation(view, elevationDp.toFloat())
    }
}


class TestActivity : AppCompatActivity() {

    val list = ArrayList<TestFragment>()
    var i = 0
    var i2 = 0

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



        viewModel.i.observe(this){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
        }

        edit.addTextChangedListener{
            val content = it.toString()
            if (content.isNotEmpty()){
                test_text.text = content
            }
        }

        CoroutineScope(Dispatchers.Main).launch {

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
            fl.removeView(
                fl.findViewWithTag("TestView")
            )
        }


        red.setOnClickListener {
//            val lp1 = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
//            val flc = FrameLayout(this)
//
//            flc.layoutParams = lp1
//            flc.setBackgroundColor(resources.getColor(R.color.light_grey))
//            flc.tag = "TestView"
//            fl.addView(flc)

            viewModel.i = MutableLiveData()
            val lp2  = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 50f.toPx())
            val view = TextView(this)
            lp2.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            lp2.setMargins(30,0,30,30)
            view.setBackgroundColor(resources.getColor(R.color.light_green))
            view.layoutParams = lp2
            view.tag = "TestView"
            view.setTextColor(resources.getColor(R.color.black))
            view.text = i2++.toString()

            fl.addView(view)
            Log.d("flWidth","${fl.width.toFloat().toDp()}dp ${fl.height.toFloat().toDp()}dp")
        }

        holder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

            }
        })


        s1.setOnClickListener {

            window.statusBarColor = resources.getColor(R.color.light_grey)
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