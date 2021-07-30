package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.bet.R
import com.epam.bet.adapters.UserDataAdapter
import com.epam.bet.databinding.FollowersFragmentBinding
import com.epam.bet.viewmodel.SubscribeViewModel

class FollowersFragment: Fragment(R.layout.followers_fragment) {
    private var _binding: FollowersFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersAdapter: UserDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowersFragmentBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(SubscribeViewModel::class.java)

        followersAdapter = UserDataAdapter(viewModel.getFollowersOptions())
        binding.followersRecyclerView.adapter = followersAdapter
        binding.followersRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        followersAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        followersAdapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}