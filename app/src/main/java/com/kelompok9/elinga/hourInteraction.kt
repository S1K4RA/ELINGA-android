package com.kelompok9.elinga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [hourInteraction.newInstance] factory method to
 * create an instance of this fragment.
 */
class hourInteraction : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var _timepicker : TimePicker = view.findViewById(R.id.NewAcTimePicker)
        _timepicker.setIs24HourView(false)

        var _typespinner : Spinner = view.findViewById(R.id.typeSpinner)
        _typespinner.onItemSelectedListener

        _typespinner.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.array_adapter_spinner_textview, activityType) as SpinnerAdapter
        _typespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = parent?.getItemAtPosition(position).toString()
                Toast.makeText(activity,type, Toast.LENGTH_SHORT).show()
                println(type)
            }
        }

        //On click submit

        var _btnNext : Button = view.findViewById(R.id.btnNewAcNext)
        _btnNext.setOnClickListener {
            if (type.isNullOrBlank()) {
                Toast.makeText(activity,"Please Select Activity",Toast.LENGTH_SHORT).show()
            } else { // pindah fragment
                //selain video conference masukno ke database
                    if (type == "Video Conference") {
                        println("THIS DONT GO TO DATABASE YET, MAKE NEW FRAGMENT")
                    } else {
                        var receivedate = dailyInteraction.change_date.text.toString()
                        var title_activity: EditText = view.findViewById(R.id.title_activity)
                        var selected_time : LocalTime = LocalTime.of(_timepicker.hour, _timepicker.minute)
                        MainActivity.db.collection(receivedate).document(title_activity.text.toString())
                            .set(Event(title_activity.text.toString(), selected_time, type = type))
                    }
            }
        }

        var _btnCancel : Button = view.findViewById(R.id.btnNewAcCancel)
        _btnCancel.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()
        }

    }

    companion object {
        var activityType = arrayOf<String>("Video Conference", "Workout", "Sleep", "Alarm")

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment hourInteraction.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            hourInteraction().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}