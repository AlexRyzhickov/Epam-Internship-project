package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.epam.bet.R
import com.epam.bet.databinding.PickUserFragmentLayoutBinding
import com.epam.bet.viewmodel.BetsViewModel
import org.koin.android.ext.android.get
import androidx.lifecycle.Observer

class PickUserFragment : Fragment(R.layout.create_new_fragment_layout), AdapterView.OnItemSelectedListener {
    private lateinit var binding: PickUserFragmentLayoutBinding
    private var followerNamesList: Array<String> = arrayOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PickUserFragmentLayoutBinding.inflate(inflater, container, false)
        val viewModel =  get<BetsViewModel>()
        viewModel.fetchUser()
        viewModel.getIFollowList()

        val followerList: Spinner = binding.UserSpinner
        viewModel.iFollowList.observe(viewLifecycleOwner, Observer {
            it?.let {
                followerNamesList =  viewModel.getFollowersNames()
                val followerAdapter = ArrayAdapter(requireActivity(), R.layout.followers_spinner_item, followerNamesList)
                followerList.adapter = followerAdapter
                followerAdapter.notifyDataSetChanged()
            }
        })
        followerList.onItemSelectedListener = this

        binding.SaveButton.setOnClickListener {

        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val viewModel =  get<BetsViewModel>()
        when (parent?.id){
            R.id.FollowerSpinner -> {
                viewModel.selectedFollowerNumber = position
                //viewModel.setBetList()
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}