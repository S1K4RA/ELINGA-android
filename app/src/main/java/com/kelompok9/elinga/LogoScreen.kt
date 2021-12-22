package com.kelompok9.elinga

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation


@Suppress("DEPRECATION")
class LogoScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logo_screen, container, false)

        Handler().postDelayed({
            Navigation.findNavController(view).navigate(R.id.home2)
        }, 3000)

        return view
    }
}