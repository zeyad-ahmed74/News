package com.example.news.ui

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.news.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class SplashFragment : Fragment() {

    private var bottomNav : BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav = requireActivity().findViewById(R.id.bottomNav)
        bottomNav?.visibility = View.GONE

        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_postFragment)
        },5000)

    }
}