package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.epam.bet.R
import com.epam.bet.databinding.CreateNewFragmentLayoutBinding

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%202%20copy%202.png

class CreateNewBetFragment : Fragment(R.layout.create_new_fragment_layout) {
    private var bindingVar: CreateNewFragmentLayoutBinding? = null
    private val binding get() = bindingVar!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        bindingVar = CreateNewFragmentLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

}