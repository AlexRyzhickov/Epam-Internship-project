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

class IFollowFragment : Fragment(R.layout.i_follow_fragment){
    private var _binding: IFollowFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = IFollowFragmentBinding.inflate(inflater, container, false)
        val recyclerView = binding.iFollowRecyclerView

        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Belal Khan", "BelalKhan@gmail.com"))
        users.add(User("Ramiz Khan", "RamizKhan@gmail.com"))
        users.add(User("Faiz Khan", "FaizKhan@gmail.com"))
        users.add(User("Belal Khan", "BelalKhan@gmail.com"))
        users.add(User("Ramiz Khan", "RamizKhan@gmail.com"))
        users.add(User("Faiz Khan", "FaizKhan@gmail.com"))
        users.add(User("Belal Khan", "BelalKhan@gmail.com"))
        users.add(User("Ramiz Khan", "RamizKhan@gmail.com"))
        users.add(User("Faiz Khan", "FaizKhan@gmail.com"))
        users.add(User("Belal Khan", "BelalKhan@gmail.com"))
        users.add(User("Ramiz Khan", "RamizKhan@gmail.com"))
        users.add(User("Faiz Khan", "FaizKhan@gmail.com"))

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = FollowersRecyclerAdapter(users)

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