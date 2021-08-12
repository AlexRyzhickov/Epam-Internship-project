package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.adapters.BetsListAdapter
import com.epam.bet.databinding.BetsFragmentLayoutBinding
import com.epam.bet.interfaces.RecyclerViewClickListener
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.SharedPreferencesProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.get

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%202.png

class BetsFragment : Fragment(R.layout.bets_fragment_layout), RecyclerViewClickListener {
    private var bindingVar: BetsFragmentLayoutBinding? = null
    private val binding get() = bindingVar!!
    private lateinit var weaponAdapter: BetsListAdapter
    private lateinit var betsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingVar = BetsFragmentLayoutBinding.inflate(inflater, container, false)
        betsList = binding.BetsList


        //val viewModel = ViewModelProvider(this).get(BetsViewModel::class.java)
        val viewModel =  get<BetsViewModel>()
        viewModel.fetchUser()
        //viewModel.addBet()

        viewModel.betsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                weaponAdapter = BetsListAdapter(it, this)
                betsList.apply{
                    layoutManager = LinearLayoutManager(activity);
                    adapter = weaponAdapter
                    //addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
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


