package com.kelompok9.elinga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalTime

class dailyInteraction : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiveBundle : Bundle? = arguments
        val change_date : TextView = view.findViewById(R.id.daily_date)
        val change_day : TextView = view.findViewById(R.id.dayOfWeek)

        val hour_list : RecyclerView = requireView().findViewById(R.id.dailyInteractionRV)
        var arr_event = arrayListOf<Event>()

        Log.d("bundle", receiveBundle!!.get("date").toString())
        var disp_atas = receiveBundle.getString("tanggal") + " " + receiveBundle.getString("bulan") + " " + receiveBundle.getString("tahun")
        change_date.text = disp_atas
        change_day.text = receiveBundle.getString("hari")

        setView(hour_list, arr_event)
    }

    private fun setTime(adapter : ArrayList<Event>){
        adapter.clear()

        for (hour in 0..23) {
            val time: LocalTime = LocalTime.of(hour, 0)
            //val date: ArrayList<Event> = Event.eventsForDateAndTime(selectedDate, time)
            val event = Event(time = time)
            //val hourEvent = HourEvent(time, events)
            adapter.add(event)
        }
    }

    private fun setView(rv : RecyclerView, adapter: ArrayList<Event>) {
        println("Please work")
        setTime(adapter)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context, 1)
        rv.layoutManager = layoutManager
        rv.adapter = HourAdapter(adapter)
    }
}