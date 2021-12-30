package com.kelompok9.elinga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class WorkoutTitle : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _ez = view.findViewById<ImageButton>(R.id.ez_workout)
        val _med = view.findViewById<ImageButton>(R.id.med_workout)
        val _hard = view.findViewById<ImageButton>(R.id.hard_workout)

        _ez.setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_easyWorkout)
        }
        _med.setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_mediumWorkout)
        }
        _hard.setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_hardWorkout)
        }
    }

}