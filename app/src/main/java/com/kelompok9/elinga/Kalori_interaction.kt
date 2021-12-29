package com.kelompok9.elinga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Kalori_interaction.newInstance] factory method to
 * create an instance of this fragment.
 */
class Kalori_interaction : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalori_interaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var _btnSubmit : Button = view.findViewById(R.id.btnSubmit)

        _btnSubmit.setOnClickListener {
            var _age : EditText = view.findViewById(R.id.ageInput)
            var _height : EditText = view.findViewById(R.id.heightInput)
            var _weight : EditText = view.findViewById(R.id.weightInput)
            var _isMale : RadioButton = view.findViewById(R.id.male_select)
            var _isFemale : RadioButton = view.findViewById(R.id.female_select)

            var _boolIsMale : Boolean = false

            var age = _age.text.toString().toInt()
            var height = _height.text.toString().toInt()
            var weight = _weight.text.toString().toInt()
            var value : Float = 0F

            if (_isMale.isChecked) {
                _boolIsMale = true
                value = ((10 * weight) + (6.25 * height) - (5 * age) + 5).toFloat()
            } else if (_isFemale.isChecked) {
                _boolIsMale = false
                value = ((10 * weight) + (6.25 * height) - (5 * age) - 161).toFloat()
            }

            var valu = Math.round(value)

            var data = BMR(age,_boolIsMale,height,weight,valu)
            MainActivity.db.collection("BMR").document("Calories_Data").set(data)
                .addOnSuccessListener {
                    Toast.makeText(this.requireContext(),"Data Added", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this.requireContext(), "Failed to Add", Toast.LENGTH_SHORT).show()
                }

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
         * @return A new instance of fragment Kalori_interaction.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Kalori_interaction().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}