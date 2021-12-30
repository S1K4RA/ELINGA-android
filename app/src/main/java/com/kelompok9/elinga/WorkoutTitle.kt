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
        val view = inflater.inflate(R.layout.fragment_workout_title, container, false)

        view.findViewById<ImageButton>(R.id.ez_workout).setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_easyWorkout)
        }
        view.findViewById<ImageButton>(R.id.med_workout).setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_mediumWorkout)
        }
        view.findViewById<ImageButton>(R.id.hard_workout).setOnClickListener {
            findNavController().navigate(R.id.action_workoutTitle_to_hardWorkout)
        }
        return view
    }
}