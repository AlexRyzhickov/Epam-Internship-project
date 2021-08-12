package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import com.epam.bet.fragments.dialogs.BetDescriptionDialog
import com.epam.bet.interfaces.RecyclerViewClickListener
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.SharedPreferencesProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.get

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%202.png

class BetsFragment : Fragment(R.layout.bets_fragment_layout), RecyclerViewClickListener, AdapterView.OnItemSelectedListener {
    private var bindingVar: BetsFragmentLayoutBinding? = null
    private val binding get() = bindingVar!!
    private lateinit var betsAdapter: BetsListAdapter
    private lateinit var betsList: RecyclerView
    private var followerNamesList: Array<String> = arrayOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingVar = BetsFragmentLayoutBinding.inflate(inflater, container, false)
        betsList = binding.BetsList

        val viewModel =  get<BetsViewModel>()
        viewModel.fetchUser()
        viewModel.getIFollowList()

        val followerList: Spinner = binding.FollowerSpinner
        viewModel.iFollowList.observe(viewLifecycleOwner, Observer {
            it?.let {
                followerNamesList =  viewModel.getFollowersNames()
                val followerAdapter = ArrayAdapter(requireActivity(), R.layout.followers_spinner_item, followerNamesList)
                followerList.adapter = followerAdapter
                followerAdapter.notifyDataSetChanged()
            }
        })
        followerList.onItemSelectedListener = this

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
        when (view.id)
        {
            R.id.BetLayout ->{
                val betDescriptionDialog: BetDescriptionDialog = BetDescriptionDialog()
                val args = Bundle()
                args.putInt("id", id)
                betDescriptionDialog.arguments = args
                betDescriptionDialog.show(parentFragmentManager, "StatSettingsDialog")
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val viewModel =  get<BetsViewModel>()
        when (parent?.id){
            R.id.FollowerSpinner -> {
                viewModel.selectedFollowerNumber = position
                viewModel.setBetList()
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}


