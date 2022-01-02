package com.kelompok9.elinga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList


class HourAdapter (
    private val listEvent : ArrayList<Event>
    ) : RecyclerView.Adapter<HourAdapter.ListViewHolder>() {
        inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            var _timeTV : TextView = itemView.findViewById(R.id.timeTV)
            var _events : TextView = itemView.findViewById(R.id.event1)
            //var _btnAdd : ImageButton = itemView.findViewById(R.id.btnAdd)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.hour_cell, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var event = listEvent[position]

        holder._timeTV.text = event.time.toString()
        holder._events.text = event.name
        /*if (event.name != "") {
            holder._btnAdd.visibility = View.GONE
        }*/
        /*holder._btnAdd.setOnClickListener{
            Toast.makeText(holder.itemView.context, "add", Toast.LENGTH_SHORT).show()
            var date = dailyInteraction.change_date.text.toString()
            MainActivity.db.collection(date).document("NewItem").set(Event("NewItem", date, event.time))
        }*/
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }
}