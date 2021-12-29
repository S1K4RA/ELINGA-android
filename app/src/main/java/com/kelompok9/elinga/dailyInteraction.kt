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
<<<<<<< Updated upstream
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
=======
        override fun onCreateView(
>>>>>>> Stashed changes
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiveBundle : Bundle? = arguments
        change_date = view.findViewById(R.id.daily_date)
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

<<<<<<< Updated upstream
    companion object {
        lateinit var change_date : TextView
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dailyInteraction.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dailyInteraction().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
=======
>>>>>>> Stashed changes
}