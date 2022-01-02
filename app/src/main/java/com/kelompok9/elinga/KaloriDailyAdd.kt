package com.kelompok9.elinga

import android.app.UiAutomation
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import java.time.LocalTime
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KaloriDailyAdd.newInstance] factory method to
 * create an instance of this fragment.
 */
data class CaloriesToday (
    var NeededToday: UInt,
    var time : LocalTime = LocalTime.now()
    )


class KaloriDailyAdd : Fragment() {

    var CaloriesNeededToday : UInt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalori_daily_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiveBundle : Bundle? = arguments

        var _calBMR : TextView = view.findViewById(R.id.textCaloriesBMR)
        var _calNeed : TextView = view.findViewById(R.id.textCaloriesNeeded)

        var _btnCalSubmit : Button = view.findViewById(R.id.btnaddCalSubmit)

        _calBMR.text = receiveBundle?.getInt("value").toString()

        var total : UInt = 0u
        MainActivity.db.collection(dailyInteraction.change_date.text.toString())
            .document("Calories Needed Today").get()
            .addOnSuccessListener { document ->
                if (document.get("neededToday-pVg5ArA") != null) {
                    var data = CaloriesToday(
                        document.get("neededToday-pVg5ArA").toString().toUInt()
                    )
                    total = data.NeededToday
                    var butuhbrp = receiveBundle?.getInt("value").toString().toUInt()
                    if (butuhbrp.minus(total) < butuhbrp) {
                    _calNeed.text = butuhbrp.minus(total).toString()
                    } else {
                        _calNeed.text = "Too Much Calories"
                    }
                } else {
                    _calNeed.text = receiveBundle?.getInt("value").toString()
                }
            }.addOnFailureListener {
                _calNeed.text = "NULL"
            }

        _calBMR.text = receiveBundle?.getInt("value").toString()

        _btnCalSubmit.setOnClickListener {
            var _inputCal : EditText = view.findViewById(R.id.addCalInput)

            var Input = _inputCal.text.toString().toUInt() + total
            MainActivity.db.collection(dailyInteraction.change_date.text.toString())
                .document("Calories Needed Today")
                .set(CaloriesToday(Input,LocalTime.now()))

            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()

        }

        var _btnCalCancel : Button = view.findViewById(R.id.btnAddCalCancel)
        _btnCalCancel.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()
        }

    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KaloriDailyAdd.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KaloriDailyAdd().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}