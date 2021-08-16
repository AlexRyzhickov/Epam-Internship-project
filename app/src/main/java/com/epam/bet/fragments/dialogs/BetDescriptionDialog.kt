package com.epam.bet.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.epam.bet.R
import com.epam.bet.databinding.BetDescriptionDialogBinding
import com.epam.bet.entities.Bet
import com.epam.bet.entities.InboxMessage
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.InboxViewModel
import com.epam.bet.viewmodel.SharedPreferencesProvider
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext


class BetDescriptionDialog : DialogFragment() {

    private var _binding: BetDescriptionDialogBinding? = null
    private val binding get() = _binding!!
    private var betNumber: Int = 0
    private var isInbox: Boolean = false
    private val sharedPreferences : SharedPreferencesProvider by lazy { GlobalContext.get().koin.get() }

    private val TAG = "MyCustomDialog"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val mArgs = arguments
            betNumber = mArgs?.getInt("id")!!
            isInbox = mArgs.getBoolean("isInbox")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner);

        _binding = BetDescriptionDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        //создание вью-модел и добавление обсервера
        if (!isInbox){
            val viewModel =  get<BetsViewModel>()
            loadData(viewModel.betsList.value?.get(betNumber)!!)
        }
        else {
            val viewModel =  get<InboxViewModel>()
            loadDataInbox(viewModel.inboxList.get(betNumber))
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.65).toInt()
        dialog!!.window?.setLayout(width, height)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(bet: Bet) {
        binding.Betname.text = bet.name
        binding.OpponentNameValue.text = bet.opponentName
        binding.OpponentEmailValue.text = bet.opponentEmail
        binding.BetDescriptionValue.text = bet.description
        binding.EndDateValue.text = bet.endDate
        binding.IfIWinValue.text = bet.ifImWin
        binding.IfOpponentWinValue.text = bet.ifOpponentWin
    }

    private fun loadDataInbox(inboxMessage: InboxMessage) {
        if (inboxMessage.bet.opponentEmail == sharedPreferences.get("email")) {
            val bet = inboxMessage.bet.copy()
            bet.opponentEmail = inboxMessage.from.email
            bet.opponentName = inboxMessage.from.name
            loadData(bet)
        }
        else {
            loadData(inboxMessage.bet)
        }
    }
}