package com.kelompok9.elinga

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalTime

data class BMR (
    var age : Int,
    var isMale : Boolean,
    var height : Int,
    var Weight : Int,
    var value : Int

)

class dailyInteraction : Fragment(), HourAdapter.Navigate {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_daily_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiveBundle: Bundle? = arguments
        change_date = view.findViewById(R.id.daily_date)
        val change_day: TextView = view.findViewById(R.id.dayOfWeek)

        val hour_list: RecyclerView = requireView().findViewById(R.id.dailyInteractionRV)
        val arr_event = arrayListOf<Event>()

        val disp_atas =
            receiveBundle?.getString("tanggal") + " " + receiveBundle?.getString("bulan") + " " + receiveBundle?.getString(
                "tahun"
            )
        change_date.text = disp_atas
        change_day.text = receiveBundle?.getString("hari")

        //setView(hour_list, arr_event)
        loaddataDatabase(arr_event, hour_list, view)

        var _btnKalori : MaterialButton = view.findViewById(R.id.btnKalori)
        _btnKalori.setOnClickListener {
            var calory = MainActivity.db.collection("BMR").document("Calories_Data").get()
                .addOnSuccessListener { document ->
                    println(document.data)
                    if (document.data != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        var data = BMR(
                            document.data?.get("age").toString().toInt(),
                            document.data?.get("male").toString().toBooleanStrict(),
                            document.data?.get("height").toString().toInt(),
                            document.data?.get("weight").toString().toInt(),
                            document.data?.get("value").toString().toInt()
                        )
                        caloryInit = data

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
                .addOnCompleteListener {
                    if (caloryInit != null) { // kalo gk kosong
                        val Kalori_DailyAdd_Frag = KaloriDailyAdd()
                        //Log.e(TAG, caloryInit!!.value.toString())
                        var dataBundle = Bundle()
                        dataBundle.putInt("value", caloryInit!!.value)
                        Kalori_DailyAdd_Frag.arguments = dataBundle
                        val mFragmentManager = parentFragmentManager
                        mFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.fragmentContainerView,
                                Kalori_DailyAdd_Frag,
                                dailyInteraction::class.java.simpleName
                            )
                            addToBackStack(null)
                            commit()
                        }
                    } else { // kalo kosong
                        val Kalori_Frag = Kalori_interaction()
                        Kalori_Frag.arguments = receiveBundle
                        val mFragmentManager = parentFragmentManager
                        mFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.fragmentContainerView,
                                Kalori_Frag,
                                dailyInteraction::class.java.simpleName
                            )
                            addToBackStack(null)
                            commit()
                        }
                    }
                }

        }

        var _btnAdd: FloatingActionButton = view.findViewById(R.id.btnAddDailyActivity)
        _btnAdd.setOnClickListener {
            val Hour_Interact_Frag = hourInteraction()

            // Hour_Interact_Frag.arguments = receiveBundle
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerView,
                    Hour_Interact_Frag,
                    hourInteraction::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }

            Log.e(TAG, "onViewCreated: should be working", )
        }

        val _btnWO = view.findViewById<MaterialButton>(R.id.btnWorkout)
        _btnWO.setOnClickListener {
            val WO_frag = WorkoutTitle()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerView,
                    WO_frag,
                    dailyInteraction::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setTime(adapter: ArrayList<Event>) {
        adapter.clear()

        for (hour in 0..23) {
            val time: LocalTime = LocalTime.of(hour, 0)
            //val date: ArrayList<Event> = Event.eventsForDateAndTime(selectedDate, time)
            val event = Event(time = time)
            //val hourEvent = HourEvent(time, events)
            adapter.add(event)
        }
    }

   //private fun setView(rv: RecyclerView, adapter: ArrayList<Event>) {
   //    //setTime(adapter)
   //    val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
   //    rv.layoutManager = layoutManager
   //    rv.adapter = HourAdapter(adapter, view)

   //}

    private fun loaddataDatabase(adapter: ArrayList<Event>, rv: RecyclerView, view : View) {
        var date = change_date.text.toString()
        MainActivity.db.collection(date).get().addOnSuccessListener {
            for (event in it) {
                var db_time = event.data.get("time") as Map<*, *>
                var timelocal = LocalTime.of(db_time.get("hour").toString().toInt(), db_time.get("minute").toString().toInt())

                val data = Event(
                    event.data.get("name").toString(),
                    timelocal,
                    event.data.get("link").toString(),
                    event.data.get("type").toString())
                println(data)
                if (data.name != "null" || data.type != "null") {
                    adapter.add(data)
                }
            }
            Log.d("From database", adapter.toString())
            rv.layoutManager = GridLayoutManager(context, 1)
            rv.adapter = HourAdapter(adapter, view)
        }
    }

    override fun onRecyclerViewItemClicked(location: Event) {
        println("why")
        val eIntent = Intent(Intent.ACTION_VIEW)
        eIntent.setData(Uri.parse("google.com"))
        startActivity(eIntent)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var change_date: TextView
        var caloryInit: BMR? = null
    }
}
