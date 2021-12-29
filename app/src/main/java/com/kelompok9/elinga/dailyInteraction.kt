package com.kelompok9.elinga

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalTime


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

data class BMR (
    var age : Int,
    var isMale : Boolean,
    var height : Int,
    var Weight : Int,
    var value : Int

)

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

        var _btnKalori : Button = view.findViewById(R.id.btnKalori)


        _btnKalori.setOnClickListener {

            var calory = MainActivity.db.collection("BMR").document("Calories_Data").get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        var data = BMR (
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

            println(caloryInit)
            if (caloryInit != null) { // kalo gk kosong
                val Kalori_DailyAdd_Frag = KaloriDailyAdd()
                Kalori_DailyAdd_Frag.arguments = receiveBundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, Kalori_DailyAdd_Frag, dailyInteraction::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
                } else { // kalo kosong
                val Kalori_Frag = Kalori_interaction()
                Kalori_Frag.arguments = receiveBundle
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainerView, Kalori_Frag, dailyInteraction::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }

        }

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

    companion object {
        lateinit var change_date : TextView
        var caloryInit : BMR? = null
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
}