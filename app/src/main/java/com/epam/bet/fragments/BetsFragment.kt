package com.epam.bet.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epam.bet.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%202.png

class BetsFragment : Fragment(R.layout.bets_fragment_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val action = BetsFragmentDirections.actionBetFragmentToPickUserFragment()
            findNavController().navigate(action)
        }

    }

}