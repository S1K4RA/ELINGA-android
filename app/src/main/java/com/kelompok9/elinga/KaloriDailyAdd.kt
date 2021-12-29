package com.kelompok9.elinga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
class KaloriDailyAdd : Fragment() {

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

        if (receiveBundle != null) {
            _calBMR.text = receiveBundle.getString("value")
            _calNeed.text = (total?.minus(receiveBundle.getString("value").toString().toUInt())).toString()
        }


        _btnCalSubmit.setOnClickListener {
            var _inputCal : EditText = view.findViewById(R.id.addCalInput)

            _inputCal.text.toString().toInt()

        }

    }

    companion object {
        var total : UByte? = null
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