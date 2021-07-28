package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.bet.R
import com.epam.bet.adapters.FollowersRecyclerAdapter
import com.epam.bet.databinding.IFollowFragmentBinding
import com.epam.bet.dialogs.SearchWindowDialog
import com.epam.bet.entities.User
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.adapters.BetsListAdapter
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.IFollowViewModel
import org.koin.android.ext.android.get


class IFollowFragment : Fragment(R.layout.i_follow_fragment){
    private var _binding: IFollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: FollowersRecyclerAdapter
    private lateinit var iFollowRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = IFollowFragmentBinding.inflate(inflater, container, false)
        iFollowRecyclerView = binding.iFollowRecyclerView

        val viewModel =  get<IFollowViewModel>()
        viewModel.getIFollowList()

        viewModel.iFollowList.observe(viewLifecycleOwner, Observer {
            it.let {
                listAdapter = FollowersRecyclerAdapter(it)
                iFollowRecyclerView.apply{
                    layoutManager = LinearLayoutManager(activity);
                    adapter = listAdapter
                    //addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                }
            }
        })

        binding.floatingActionButton.setOnClickListener {
            val searchWindowDialog = SearchWindowDialog()
            searchWindowDialog.show(parentFragmentManager, "search")
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}