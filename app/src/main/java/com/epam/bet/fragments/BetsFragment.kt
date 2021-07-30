package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.adapters.BetsListAdapter
import com.epam.bet.databinding.BetsFragmentLayoutBinding
import com.epam.bet.interfaces.RecyclerViewClickListener
import com.epam.bet.viewmodel.BetsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.get

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%202.png

class BetsFragment : Fragment(R.layout.bets_fragment_layout), RecyclerViewClickListener {
    private var _binding: BetsFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var betsAdapter: BetsListAdapter
    private lateinit var betsList: RecyclerView
    private var followerNamesList: Array<String> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BetsFragmentLayoutBinding.inflate(inflater, container, false)
        betsList = binding.BetsList


        //val viewModel = ViewModelProvider(this).get(BetsViewModel::class.java)
        val viewModel =  get<BetsViewModel>()
        viewModel.fetchUser()
        viewModel.getIFollowList()
        //viewModel.addBet()


        viewModel.iFollowList.observe(viewLifecycleOwner, Observer {
            it?.let {
                followerNamesList =  viewModel.getFollowersNames()
                val followerAdapter = ArrayAdapter(requireActivity(), R.layout.followers_spinner_item, followerNamesList)
                val followerList: Spinner = binding.FollowerSpinner
                followerList.adapter = followerAdapter
                followerAdapter.notifyDataSetChanged()

            }
        })

        viewModel.betsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                betsAdapter = BetsListAdapter(it, this)
                betsList.apply{
                    layoutManager = LinearLayoutManager(activity);
                    adapter = betsAdapter
                    addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                }
            }
        })


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val action = BetsFragmentDirections.actionBetFragmentToPickUserFragment()
            findNavController().navigate(action)
        }



    }

    override fun onRecyclerViewItemClickListener(view: View, id: Int) {
        TODO("Not yet implemented")
    }

}


