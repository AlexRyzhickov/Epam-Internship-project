package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.bet.R
import com.epam.bet.adapters.FollowersRecyclerAdapter
import com.epam.bet.databinding.FollowersFragmentBinding
import com.epam.bet.databinding.ProfileFragmentBinding
import com.epam.bet.entities.User

class FollowersFragment: Fragment(R.layout.followers_fragment) {
    private var _binding: FollowersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FollowersFragmentBinding.inflate(inflater, container, false)
        val recyclerView = binding.followersRecyclerView

        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Belal Khan", "Ranchi Jharkhand"))
        users.add(User("Ramiz Khan", "Ranchi Jharkhand"))
        users.add(User("Faiz Khan", "Ranchi Jharkhand"))

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = FollowersRecyclerAdapter(users)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}