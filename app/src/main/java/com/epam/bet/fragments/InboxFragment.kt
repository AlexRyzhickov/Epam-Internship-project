package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.bet.R
import com.epam.bet.adapters.InboxDataAdapter
import com.epam.bet.adapters.UserDataAdapter
import com.epam.bet.databinding.IFollowFragmentBinding
import com.epam.bet.databinding.InboxFragmentLayoutBinding
import com.epam.bet.fragments.dialogs.BetDescriptionDialog
import com.epam.bet.interfaces.RecyclerViewClickListener
import com.epam.bet.viewmodel.InboxViewModel
import com.epam.bet.viewmodel.SubscribeViewModel
import org.koin.android.ext.android.get

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%201%20copy.png

class InboxFragment : Fragment(R.layout.inbox_fragment_layout), RecyclerViewClickListener {
    private var bindingVar: InboxFragmentLayoutBinding? = null
    private val binding get() = bindingVar!!
    private lateinit var inboxAdapter: InboxDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingVar = InboxFragmentLayoutBinding.inflate(inflater, container, false)
        val viewModel = get<InboxViewModel>()//ViewModelProvider(requireActivity()).get(InboxViewModel::class.java)

        inboxAdapter = InboxDataAdapter(viewModel.getSubscriptionOptions(), this)
        binding.inboxRecyclerView.adapter = inboxAdapter
        binding.inboxRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        inboxAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        inboxAdapter.stopListening()
    }

    override fun onRecyclerViewItemClickListener(view: View, id: Int) {
        when (view.id)
        {
            R.id.inboxRow -> {
                val betDescriptionDialog: BetDescriptionDialog = BetDescriptionDialog()
                val args = Bundle()
                args.putInt("id", id)
                args.putBoolean("isInbox", true)
                betDescriptionDialog.arguments = args
                betDescriptionDialog.show(parentFragmentManager, "StatSettingsDialog")
            }
        }

    }

}