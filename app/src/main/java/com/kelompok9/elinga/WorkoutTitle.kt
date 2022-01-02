package com.kelompok9.elinga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class WorkoutTitle : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _ezbtn = view.findViewById<Button>(R.id.ez_workout)
        val _medbtn = view.findViewById<Button>(R.id.med_workout)
        val _hardbtn = view.findViewById<Button>(R.id.hard_workout)
        val _backbtn = view.findViewById<ImageButton>(R.id.backbtn)

        _ezbtn.setOnClickListener {
            val ez_Frag = EasyWorkout()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerView,
                    ez_Frag,
                    dailyInteraction::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        _medbtn.setOnClickListener {
            val med_Frag = MediumWorkout()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerView,
                    med_Frag,
                    dailyInteraction::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        _hardbtn.setOnClickListener {
            val hard_Frag = HardWorkout()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragmentContainerView,
                    hard_Frag,
                    dailyInteraction::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }

        _backbtn.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            fm.popBackStack()
        }
    }
}