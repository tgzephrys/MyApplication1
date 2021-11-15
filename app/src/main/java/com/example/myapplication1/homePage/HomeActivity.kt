package com.example.myapplication1.homePage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import com.example.myapplication1.homePage.common.toPx
import com.example.myapplication1.homePage.model.Weather
import com.example.myapplication1.homePage.model.dao.PlaceDao
import com.example.myapplication1.homePage.model.getBg
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.city_pager.*

class HomeActivity : AppCompatActivity() {

    private val FIRST_DISTANCE_DP = 20f
    private val SECOND_DISTANCE_DP = 70f
    private val TITLE_Y_DP = 80f
    private val MAX_PAGES = 8
    private val cityAdapter = MyCityAdapter(this)

    private val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityController.addActivity(this)

        //数据初始化
        init()
        setContentView(R.layout.activity_home)

        window.statusBarColor = resources.getColor(R.color.transparent)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        pagerHolder.offscreenPageLimit = MAX_PAGES
        pagerHolder.adapter = cityAdapter

        menu.setOnClickListener {
            showOptionWindow()
        }


        pagerHolder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (viewModel.cities.isNotEmpty())
                    viewModel.cities[position].weather?.let {
                    background.setImageResource(getBg(it).bg)
                } else background.setImageResource(R.drawable.original_bg)

                name_out.text = viewModel.cities[position].place!!.name
//                if (viewModel.cities[position].place!!.name != "海淀区")
//                    isLocation_out.visibility = View.GONE
            }
        })

        viewModel.localPlace.observe(this, {
            val places = it.getOrNull()
            if (places != null){
                viewModel.requestWeather(places[0])
                viewModel.places.add(places[0])
                PlaceDao.savePlace(places[0])
            }
        })

        viewModel.savedCityWeather.observe(this, {
            val weather = it.getOrNull()
            if (weather != null){
                if (viewModel.isInitialized)
                    refreshWeather(weather)
                else addCityPage(weather, viewModel.places[viewModel.placeIndex], true)
            } else Toast.makeText(this, "网络出错！", Toast.LENGTH_SHORT).show()
        })

        viewModel.scrollDistance.observe(this, { scrollY ->

            viewModel.cities.forEach {
                it.scroll(scrollY)
            }

            if (scrollY >= FIRST_DISTANCE_DP.toPx()) {
                viewModel.cities.forEach {
                    it.nameGo.visibility = View.INVISIBLE
                    it.isLocationGo.visibility = View.INVISIBLE
                    name_out.visibility = View.VISIBLE
//                    if (viewModel.cities[pagerHolder.currentItem].place!!.name == "海淀区")
//                        isLocation_out.visibility = View.VISIBLE
                }
            } else {
                viewModel.cities.forEach {
                    it.nameGo.visibility = View.VISIBLE
                    if (it.place!!.name == "海淀区")
                        it.isLocationGo.visibility = View.VISIBLE
                }
                name_out.visibility = View.INVISIBLE
//                isLocation_out.visibility = View.GONE
            }
            if (scrollY <= SECOND_DISTANCE_DP.toPx()) {
                name_out.y = TITLE_Y_DP.toPx() - scrollY.toFloat()
//                isLocation_out.y = TITLE_Y_DP.toPx() - scrollY.toFloat()
            }
        })


//        val pagerAdapter = MyCityAdapter()
//        cityPager.adapter = pagerAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)

    }

    override fun onBackPressed() {
        ActivityController.quitApplication()
    }


    override fun onNewIntent(intent: Intent?) {
        val weather = Gson().fromJson(intent?.getStringExtra("weather"), Weather::class.java)
        val place = Gson().fromJson(intent?.getStringExtra("place"), PlaceResponse.Place::class.java)
        if (weather != null && place != null) addCityPage(weather, place, false)
        super.onNewIntent(intent)
    }


    private fun showOptionWindow(){
        val contentView = LayoutInflater.from(this).inflate(R.layout.option_item, null)
        val optionWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        optionWindow.contentView = contentView
        val addCityBtn = contentView.findViewById<TextView>(R.id.add_city)
        val removeCityBtn = contentView.findViewById<TextView>(R.id.remove_city)

        addCityBtn.setOnClickListener {
            if (viewModel.cities.size < 5)
                startActivity(Intent(this, CityActivity::class.java))
            else Toast.makeText(this, "最多添加5个城市！", Toast.LENGTH_SHORT).show()
            optionWindow.dismiss()
        }

        removeCityBtn.setOnClickListener {
            val index = pagerHolder.currentItem
            removeCityPage(index)
            optionWindow.dismiss()
        }
        optionWindow.isOutsideTouchable = true;
        //optionWindow.setBackgroundDrawable(BitmapDrawable())
        optionWindow.showAsDropDown(menu)
    }

    private fun refreshWeather(weather: Weather){
        viewModel.cities[pagerHolder.currentItem].apply {
            this.refresh_frag.isRefreshing = false
            this.weather = weather
            this.refreshWeather()
        }

    }

    private fun scrollChanged(){

    }

    //背景图片渐变
    private fun changeBg(){

    }

    private fun addCityPage(weather: Weather, place: PlaceResponse.Place, isInitial: Boolean){
        if (isInitial){  //正在初始化
            val newCityPager = CityPagerFrag()
            newCityPager.weather = weather
            newCityPager.place = place
            viewModel.cities.add(newCityPager)
            Log.d("placeIndex", "${viewModel.placeIndex}")
            if (viewModel.placeIndex < viewModel.places.size - 1) {
                viewModel.placeIndex ++
                viewModel.startInit()
            } else viewModel.isInitialized = true

            val nWeather = Gson().fromJson(intent.getStringExtra("weather"), Weather::class.java)
            val nPlace = Gson().fromJson(intent.getStringExtra("place"), PlaceResponse.Place::class.java)
            if (nWeather != null && nPlace != null) addCityPage(nWeather, nPlace, false)
            cityAdapter.notifyDataSetChanged()
            menu.visibility = View.VISIBLE

        } else {   //添加了新城市
            if (PlaceDao.isPlaceSaved(place)){ //添加过该城市，直接跳转
                Log.d("已经添加了城市", place.name)
                viewModel.cities.apply {
                    for (i in indices){
                        if (this[i].place?.id == place.id) pagerHolder.currentItem = i
                    }
                }
            } else {
                val newCityPager = CityPagerFrag()
                newCityPager.weather = weather
                newCityPager.place = place
                viewModel.cities.add(newCityPager)
                cityAdapter.notifyDataSetChanged()
                PlaceDao.savePlace(place)
                pagerHolder.currentItem = viewModel.cities.size - 1
            }
        }
    }

    private fun removeCityPage(index: Int){
        if (viewModel.cities.isNotEmpty()) {

            Log.d("cityName","${viewModel.cities[index].place?.name}")

            if (viewModel.cities[index].place != null) PlaceDao.deletePlace(viewModel.cities[index].place!!)

            viewModel.cities.removeAt(index)

            cityAdapter.notifyDataSetChanged()
        }
    }

    private fun init() {
        val places = PlaceDao.getSavedPlaces()
        if (places.isEmpty()) {
            viewModel.initLocal()
            Log.d("isEmpty","isEmpty!!!!!")
        }
        else {
            viewModel.places.addAll(places)
            viewModel.startInit()
        }
    }



    inner class MyCityAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return viewModel.cities.size
        }

        override fun createFragment(position: Int): CityPagerFrag {
            return viewModel.cities[position]
        }

        override fun getItemId(position: Int): Long {
            return viewModel.cities[position].hashCode().toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return LongArray(viewModel.cities.size){
                viewModel.cities[it].hashCode().toLong()
            }.contains(itemId)
        }
    }


    //主页城市切换
//    inner class MyCityAdapter(private val cities: ArrayList<MyContact>):
//        RecyclerView.Adapter<MyCityAdapter.ViewHolder>() {
//
//        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//            var wholeView: View = view.findViewById(R.id.whole_view)
//            var name: TextView = view.findViewById(R.id.nameGo)
//            var number: TextView = view.findViewById(R.id.number)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.contacts_list_item, parent, false)
//
//
//            val viewHolder = ViewHolder(view)
//
//            return viewHolder
//        }
//
//        override fun getItemCount() = mContacts.size
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//            holder.name.text = mContacts[position].name
//            holder.number.text = mContacts[position].number
//            Log.d("-----", "$position")
//        }
//    }
}