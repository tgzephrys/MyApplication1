package com.example.myapplication1.homePage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import com.example.myapplication1.homePage.model.Weather
import com.example.myapplication1.homePage.model.getBg
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private val fragManager = supportFragmentManager
    private var weather: Weather? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        ActivityController.addActivity(this)

        init()




        window.statusBarColor = resources.getColor(R.color.transparent)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        val listManager = LinearLayoutManager(this)
        listManager.orientation = LinearLayoutManager.VERTICAL
        val cityListAdapt = CityListAdapt(viewModel.placeList)
        list.adapter = cityListAdapt
        list.layoutManager = listManager

        delete.setOnClickListener{
            editText.text.clear()
        }

        back.setOnClickListener {
            super.onBackPressed()
        }

        addButton.setOnClickListener {
            val weather = Gson().toJson(weather)
            val place = Gson().toJson(viewModel.choosingPlace.value)
            val intent = Intent(this, HomeActivity::class.java)

            intent.putExtra("weather",weather)
            intent.putExtra("place",place)
            startActivity(intent)
        }


        editText.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()){
                delete.visibility = View.VISIBLE
                list.setBackgroundResource(R.drawable.card_rect_white)
            } else{
                list.visibility = View.VISIBLE
                no_place.visibility = View.INVISIBLE
                viewModel.placeList.clear()
                cityListAdapt.notifyDataSetChanged()
                delete.visibility = View.INVISIBLE
            }
            viewModel.searchPlaces(content)
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) hideKBD()
        }

        back_top.setOnClickListener {
            onBackPressed()
        }

        viewModel.placeLiveData.observe(this, {

            val places = it.getOrNull()
            if(places!=null){
                list.visibility = View.VISIBLE
                no_place.visibility = View.INVISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                cityListAdapt.notifyDataSetChanged()
            } else if (editText.text.toString().isNotEmpty()){
                list.visibility = View.INVISIBLE
                no_place.visibility = View.VISIBLE
            } else {
                viewModel.placeList.clear()
                cityListAdapt.notifyDataSetChanged()
            }
        })

        viewModel.weatherLiveData.observe(this){
            weather = it.getOrNull()
            if (weather!=null){
                showChoosingPage(weather!!)
            } else {
                Toast.makeText(this, "网络出错！", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroy() {
        ActivityController.removeActivity(this)
        super.onDestroy()
    }

    //如果需要隐藏软键盘，让editText失焦
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN){
            val v = currentFocus
            if(needHideKBD(ev)){
                v?.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (back_top.visibility == View.VISIBLE) hideChoosingPage()
        else super.onBackPressed()
    }

    //判断是否需要隐藏软键盘
    private fun needHideKBD(ev: MotionEvent?): Boolean{

        val location = IntArray(2)
        edit.getLocationOnScreen(location)
        val mTop = location[1]
        val mLeft = location[0]
        val mBottom = location[1] + edit.height
        val mRight = location[0] + edit.width

        return (ev?.rawX!! < mLeft || ev.rawX > mRight||
                ev.rawY < mTop || ev.rawY > mBottom)
    }

    private fun hideKBD(){
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken,0)
    }


    //本地数据初始化城市卡片
    private fun init(){

    }

    private fun showChoosingPage(weather: Weather){
        val tra = fragManager.beginTransaction()
        val frag = CityPagerFrag()
        frag.weather = weather
        frag.place = viewModel.choosingPlace.value
        tra.replace(R.id.choosingFrag, frag, "choosingFrag")
        tra.commit()
        addButton.visibility = View.VISIBLE
        weather_bg.visibility = View.VISIBLE
        weather_bg.setImageResource(getBg(weather).bg)
        back_top.visibility = View.VISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    fun hideChoosingPage(){
        val tra = fragManager.beginTransaction()
        fragManager.findFragmentByTag("choosingFrag")?.let { tra.remove(it) }
        tra.commit()
        addButton.visibility = View.INVISIBLE
        weather_bg.visibility = View.INVISIBLE
        back_top.visibility = View.INVISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    //城市卡片listAdapter
    inner class CityCardAdapt():
            RecyclerView.Adapter<CityCardAdapt.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }

    //城市搜索listAdapter
    inner class CityListAdapt(val placeList: List<PlaceResponse.Place>):
        RecyclerView.Adapter<CityListAdapt.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val placeAddress: TextView = view.findViewById(R.id.placeAddress)
            val bottomLine: View = view.findViewById(R.id.bottom_line)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.city_list_item,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val place = placeList[position]
            val spannableStr = SpannableString("${editText.text}, ${place.name},${place.address}")
            spannableStr.setSpan(
                ForegroundColorSpan(Color.parseColor("#FF2450ED")),
                0,
                editText.text.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            holder.placeAddress.text = spannableStr
            holder.placeAddress.setOnClickListener {
                viewModel.getWeather(viewModel.placeList[position])
            }
            if (position == itemCount) holder.bottomLine.visibility = View.INVISIBLE
        }

        override fun getItemCount(): Int {
            return placeList.size
        }
    }
}