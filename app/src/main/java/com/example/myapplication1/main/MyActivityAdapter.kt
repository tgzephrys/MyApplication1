package com.example.myapplication1.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R

class MyActivityAdapter(private val myActivities: ArrayList<MyActivity>):
            RecyclerView.Adapter<MyActivityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val activityImage: ImageView = view.findViewById(R.id.activity_image)
        val activityName: TextView = view.findViewById(R.id.activity_name)
        val wholeView: View = view.findViewById(R.id.whole_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activities_list_item, parent, false)

        val viewHolder = ViewHolder(view)
        viewHolder.wholeView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val name = myActivities[position].name
            val intent = Intent("com.example.myapplication1.${name}")

            intent.putExtra("activity_name", name)
            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    override fun getItemCount() = myActivities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newActivity = myActivities[position]
        holder.activityImage.setImageResource(newActivity.imageId)
        holder.activityName.text = newActivity.name
    }
}

class MyActivity(val name: String, val imageId:Int)