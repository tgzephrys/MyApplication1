package com.example.myapplication1.main

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R

class MyContactsAdapter(private val mContacts: ArrayList<MyContact>):
    RecyclerView.Adapter<MyContactsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var wholeView: View = view.findViewById(R.id.whole_view)
        var name: TextView = view.findViewById(R.id.name)
        var number: TextView = view.findViewById(R.id.number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contacts_list_item, parent, false)

        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount() = mContacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = mContacts[position].name
        holder.number.text = mContacts[position].number
    }
}

class MyContact(val name: String, val number: String)