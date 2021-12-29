package com.kelompok9.elinga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

data class Item (
    var description:String,
    var category:Int,
    var date: Long,
)

class adapterLog(
    private val listLog: ArrayList<Item>
) : RecyclerView.Adapter<adapterLog.ListViewHolder>()
{
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       /* var _value : TextView = itemView.findViewById(R.id.LogValue)
        var _category : TextView = itemView.findViewById(R.id.LogKategori)
        var _date : TextView = itemView.findViewById(R.id.LogDate)*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterLog.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.log_item,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterLog.ListViewHolder, position: Int) {
      /*  var Logs = listLog[position]
        holder._value.setText(Logs.value.toString())
        holder._category.setText(Logs.category)

        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(Logs.date)

        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat)
        val dateString = sdf.format(calendar.time)
        holder._date.setText(dateString)*/

    }

    override fun getItemCount(): Int {
        return listLog.size
    }
}