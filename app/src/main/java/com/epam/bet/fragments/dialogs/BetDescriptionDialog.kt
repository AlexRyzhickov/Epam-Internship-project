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
import com.epam.bet.extensions.showToast
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
    private var operationInProgress: Boolean = false

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
            binding.agreeButton.visibility = View.INVISIBLE
            binding.declineButton.visibility = View.INVISIBLE
            loadData(viewModel.betsList.value?.get(betNumber)!!)
        }
        else {
            val viewModel =  get<InboxViewModel>()
            loadDataInbox(viewModel.inboxList.get(betNumber))
            binding.agreeButton.setOnClickListener{
                if (!operationInProgress) {
                    operationInProgress = true
                    viewModel.approveBet(viewModel.inboxList.get(betNumber), ::approveSuccessCallback, ::approveFailCallback)
                }
                else{
                    context?.showToast("Operation in progress. Please, wait.")
                }
            }
            binding.declineButton.setOnClickListener{
                if (!operationInProgress) {
                    operationInProgress = true
                    viewModel.declineBet(viewModel.inboxList.get(betNumber), ::declineSuccessCallback, ::declineFailCallback)
                }
                else{
                    context?.showToast("Operation in progress. Please, wait.")
                }
            }
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
        loadData(inboxMessage.bet)
    }

    private fun approveSuccessCallback(){
        operationInProgress = false
        context?.showToast("You approved the bet!")
        dismiss()
    }

    private fun approveFailCallback(){
        operationInProgress = false
        context?.showToast("Error! Try again later")
    }

    private fun declineSuccessCallback(){
        operationInProgress = false
        context?.showToast("You declined the bet!")
        dismiss()
    }

    private fun declineFailCallback(){
        context?.showToast("Error! Try again later")
        operationInProgress = false
    }
}