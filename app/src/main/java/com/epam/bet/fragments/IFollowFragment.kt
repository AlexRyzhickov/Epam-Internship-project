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
import com.epam.bet.databinding.IFollowFragmentBinding
import com.epam.bet.dialogs.SearchWindowDialog
import com.epam.bet.viewmodel.SubscribeViewModel


class IFollowFragment : Fragment(R.layout.i_follow_fragment) {

    private var _binding: IFollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var subscribersAdapter: UserDataAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = IFollowFragmentBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(SubscribeViewModel::class.java)

        subscribersAdapter = UserDataAdapter(viewModel.getSubscriptionOptions())
        binding.iFollowRecyclerView.adapter = subscribersAdapter
        binding.iFollowRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.floatingActionButton.setOnClickListener {
            val searchWindowDialog = SearchWindowDialog()
            searchWindowDialog.show(parentFragmentManager, "search")
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        subscribersAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        subscribersAdapter.stopListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}