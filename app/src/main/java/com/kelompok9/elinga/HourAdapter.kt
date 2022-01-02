package com.kelompok9.elinga

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList



class HourAdapter (
    private val listEvent : ArrayList<Event>,private val viewType : View
    ) : RecyclerView.Adapter<HourAdapter.ListViewHolder>() {
    interface Navigate{
        fun onRecyclerViewItemClicked(location: Event)
    }
        var listener : Navigate? = null
        inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            var _timeTV : TextView = itemView.findViewById(R.id.eventTime)
            var _events : TextView = itemView.findViewById(R.id.eventTitle)
            var _btnDelete : Button = itemView.findViewById(R.id.btnDelete)
            var _eventType : TextView = itemView.findViewById(R.id.eventType)
            var _cellitem : ConstraintLayout = itemView.findViewById(R.id.hour_cell_item)

            //init {
            //    itemView.setOnClickListener {
            //        val eIntent = Intent(Intent.ACTION_VIEW)
            //        eIntent.setData(Uri.parse(listEvent.))
            //        itemView.context.startActivity(eIntent)
            //    }
            //}
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.hour_cell, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var event = listEvent[position]

        holder._timeTV.text = event.time.toString()
        holder._events.text = event.name
        holder._eventType.text = event.type
        holder._btnDelete.setOnClickListener {
            listEvent.removeAt(position)
            MainActivity.db.collection(dailyInteraction.change_date.text.toString())
                .document(event.name.toString())
                .delete()
            notifyDataSetChanged()
        }
        holder._cellitem.setOnClickListener{
            if (event.type == "Video Conference") {
                val eIntent = Intent(Intent.ACTION_VIEW)
                eIntent.setData(Uri.parse(event.link))
                viewType.context.startActivity(eIntent)
            }
        }
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