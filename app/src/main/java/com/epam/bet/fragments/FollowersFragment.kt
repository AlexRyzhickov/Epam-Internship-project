package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.epam.bet.R
import com.epam.bet.adapters.FollowersRecyclerAdapter
import com.epam.bet.databinding.FollowersFragmentBinding
import com.epam.bet.databinding.ProfileFragmentBinding
import com.epam.bet.entities.Follower
import com.epam.bet.entities.User
import com.epam.bet.viewmodel.FollowersViewModel
import com.epam.bet.viewmodel.IFollowViewModel
import org.koin.android.ext.android.get

class FollowersFragment: Fragment(R.layout.followers_fragment) {
    private var _binding: FollowersFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: FollowersRecyclerAdapter
    private lateinit var followersRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowersFragmentBinding.inflate(inflater, container, false)
        followersRecyclerView = binding.followersRecyclerView
        val viewModel =  get<FollowersViewModel>()
        viewModel.getFollowersList()

        viewModel.followersList.observe(viewLifecycleOwner, Observer {
            it.let {
                listAdapter = FollowersRecyclerAdapter(it)
                followersRecyclerView.apply{
                    layoutManager = LinearLayoutManager(activity);
                    adapter = listAdapter
                    //addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                }
            }
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}