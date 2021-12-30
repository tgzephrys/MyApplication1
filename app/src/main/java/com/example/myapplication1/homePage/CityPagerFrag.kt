package com.example.myapplication1.homePage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.homePage.mView.DailyLineChart
import com.example.myapplication1.homePage.model.*
import com.example.myapplication1.homePage.model.netWork.entity.PlaceResponse
import kotlinx.android.synthetic.main.city_pager.*
import okhttp3.internal.notify
import java.util.*
import kotlin.collections.ArrayList


class CityPagerFrag : Fragment(){

    var hourlyList = ArrayList<HourlySky>()
    var dailyList = ArrayList<DailySky>()
    var airInfoList = ArrayList<AirInfo>()
    var lifeIndexList = ArrayList<LifeIndex>()

    var hourlyListAdapter = HourlyListAdapter(hourlyList)
    var dailyListAdapter = DailyListAdapter(dailyList)
    var airInfoListAdapter = AirInfoListAdapter(airInfoList)
    var lifeIndexListAdapter = LifeIndexListAdapter(lifeIndexList)

    var weather: Weather? = null
    var place: PlaceResponse.Place? = null

    private val viewModel = activityViewModels<WeatherViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.city_pager,container,false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val verticalManager = LinearLayoutManager(activity)
        verticalManager.orientation = LinearLayoutManager.VERTICAL
        airInfoListView.layoutManager = verticalManager
        airInfoListView.adapter = airInfoListAdapter

        val horizontalManager1 = LinearLayoutManager(activity)
        horizontalManager1.orientation = LinearLayoutManager.HORIZONTAL
        hourlyListView.layoutManager = horizontalManager1
        hourlyListView.adapter = hourlyListAdapter

        val horizontalManager2 = LinearLayoutManager(activity)
        horizontalManager2.orientation = LinearLayoutManager.HORIZONTAL
        dailyListView.layoutManager = horizontalManager2
        dailyListView.adapter = dailyListAdapter


        val gridLayoutManager = GridLayoutManager(activity, 3)
        lifeIndexListView.layoutManager = gridLayoutManager
        lifeIndexListView.adapter = lifeIndexListAdapter

        refresh_frag.setOnRefreshListener {
            viewModel.value.requestWeather(place!!)
        }

        cityPager.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            viewModel.value.scrollDistance.value = scrollY
        }

        if (weather != null && place != null) refreshWeather()

        viewModel.value.scrollDistance.value?.let { scroll(it) }
    }

    fun refreshWeather(){
        if (place!!.name =="海淀区") isLocationGo.visibility = View.VISIBLE


        refreshTime.text = "上次更新时间： ${Date().date2timeStr()}"
        nameGo.text = place!!.name
        realTimeTemper.text = weather!!.realTime.temperature.toInt().toString()
        aqi_view.drawView(weather!!.realTime.airQuality.aqi.chn.toInt(),
                          weather!!.realTime.airQuality.description.chn)



        hourlyList.clear()
        hourlyList.addAll(getHourlyList(weather!!))
        dailyList.clear()
        dailyList.addAll(getDailyList(weather!!))

        airInfoList.clear()
        airInfoList.addAll(getAirInfoList(weather!!))
        lifeIndexList.clear()
        lifeIndexList.addAll(getLifeIndexList(weather!!))

        hourlyListAdapter.notifyDataSetChanged()
        dailyListAdapter.notifyDataSetChanged()
        airInfoListAdapter.notifyDataSetChanged()
        lifeIndexListAdapter.notifyDataSetChanged()

    }

    fun scroll(scrollY: Int){
        cityPager.scrollTo(0, scrollY)
    }


    inner class HourlyListAdapter(private val hourlyList: List<HourlySky>):
        RecyclerView.Adapter<HourlyListAdapter.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var curTime = view.findViewById<TextView>(R.id.hourly_time)
            var skyConIc = view.findViewById<ImageView>(R.id.hourly_skyconIc)
            var temper = view.findViewById<TextView>(R.id.hourly_temper)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hourly_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = hourlyList.size

        override fun onBindViewHolder(holder: HourlyListAdapter.ViewHolder, position: Int) {
            holder.curTime.text = hourlyList[position].time
            holder.skyConIc.setImageResource(hourlyList[position].icon)
            holder.temper.text = hourlyList[position].temper
        }

    }

    inner class DailyListAdapter(private val dailyList: List<DailySky>):
        RecyclerView.Adapter<DailyListAdapter.ViewHolder>(){


        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var day = view.findViewById<TextView>(R.id.daily_day)
            var date = view.findViewById<TextView>(R.id.date)
            var skyConIc = view.findViewById<ImageView>(R.id.daily_skyconIc)
            var skyCon = view.findViewById<TextView>(R.id.daily_skycon)
            var lineChart = view.findViewById<DailyLineChart>(R.id.line_chart)
            var aqiDesc = view.findViewById<TextView>(R.id.aqi_desc)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = dailyList.size

        override fun onBindViewHolder(holder: DailyListAdapter.ViewHolder, position: Int) {
            holder.day.text = dailyList[position].day
            holder.date.text = dailyList[position].date
            holder.skyConIc.setImageResource(dailyList[position].icon)
            holder.skyCon.text = dailyList[position].skycon
            var maxTemper = -100f
            var minTemper = 100f
            dailyList.forEach {
                if (it.index.max > maxTemper) maxTemper = it.index.max
                if (it.index.min < minTemper) minTemper = it.index.min
            }
            Log.d("zuigaozuidi", "$maxTemper , $minTemper")
            var temperArray = floatArrayOf(
                dailyList[position].index.max, dailyList[position].index.min,  //当天最高最低温度
                maxTemper!!, minTemper!!,    //  近几天最高最低温度
                0f, 0f,     //  下一组最高最低温度
                0f, 0f,     //  上一组最高最低温度
                0f          //  标志位
            )
            when(position){
                0 -> {
                    temperArray[4] = dailyList[position + 1].index.max
                    temperArray[5] = dailyList[position + 1].index.min
                    temperArray[temperArray.lastIndex] = 1f   //最左边
                }
                dailyList.lastIndex -> {
                    temperArray[6] = dailyList[position - 1].index.max
                    temperArray[7] = dailyList[position - 1].index.min
                    temperArray[temperArray.lastIndex] = 2f   //最右边
                }
                else -> {
                    temperArray[4] = dailyList[position + 1].index.max
                    temperArray[5] = dailyList[position + 1].index.min
                    temperArray[6] = dailyList[position - 1].index.max
                    temperArray[7] = dailyList[position - 1].index.min
                }
            }
            holder.lineChart.drawLines(temperArray)
            holder.aqiDesc.text = dailyList[position].airInfo
        }

    }

    inner class AirInfoListAdapter(val airInfoList: List<AirInfo>):
        RecyclerView.Adapter<AirInfoListAdapter.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var gasName = view.findViewById<TextView>(R.id.gas_name)
            var gasIndex = view.findViewById<TextView>(R.id.gas_index)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.air_info_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = airInfoList.size

        override fun onBindViewHolder(holder: AirInfoListAdapter.ViewHolder, position: Int) {
            holder.gasName.text = airInfoList[position].gasName
            holder.gasIndex.text = airInfoList[position].index.toString()
        }

    }

    inner class LifeIndexListAdapter(val lifeIndexList: List<LifeIndex>):
        RecyclerView.Adapter<LifeIndexListAdapter.ViewHolder>(){

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            var lifeIndexName = view.findViewById<TextView>(R.id.life_index_name)
            var lifeIndexIc = view.findViewById<ImageView>(R.id.life_index_ic)
            var lifeIndexDesc = view.findViewById<TextView>(R.id.life_index_desc)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.life_index_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = lifeIndexList.size

        override fun onBindViewHolder(holder: LifeIndexListAdapter.ViewHolder, position: Int) {
            holder.lifeIndexName.text = lifeIndexList[position].itemName
            holder.lifeIndexIc.setImageResource(lifeIndexList[position].icon)
            holder.lifeIndexDesc.text = lifeIndexList[position].desc
        }

    }

}