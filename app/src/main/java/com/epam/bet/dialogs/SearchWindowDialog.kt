package com.epam.bet.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.epam.bet.R
import com.epam.bet.databinding.SearchWindowDialogBinding
import com.epam.bet.extensions.showToast
import com.epam.bet.viewmodel.SubscribeViewModel
import org.koin.android.ext.android.get

class SearchWindowDialog: DialogFragment() {

    private var _binding: SearchWindowDialogBinding? = null
    private val binding get() = _binding!!
    private var searchInProgress = false;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchWindowDialogBinding.inflate(inflater, container, false)

        val viewModel =  get<SubscribeViewModel>()

        binding.searchButton.isEnabled = false
        binding.searchField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty()){
                    binding.searchButton.isEnabled = false
                    binding.searchButton.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                    binding.searchButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.grey))
                }
                else {
                    binding.searchButton.isEnabled = true
                    binding.searchButton.setBackgroundColor(ContextCompat.getColor(context!!, R.color.blue))
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.searchButton.setOnClickListener{
            if (!searchInProgress) {
                var email: String = binding.searchField.text.trim().toString()
                if (viewModel.isItMyEmail(email)){
                    context?.showToast("You can't follow yourself!")
                }
                else if (!viewModel.isIFollow(email, context)){
                    searchInProgress = true
                    viewModel.addIFollow(email, ::searchSuccessCallback, ::searchFailCallback)
                }
               /* else {
                    context?.showToast("You're already following this user!")
                }*/
            }
            else{
                context?.showToast("Search in progress. Please, wait.")
            }

        }
        binding.cancelButton.setOnClickListener{
            dismiss()
        }
        return binding.root
    }

    fun searchSuccessCallback(email: String){
        searchInProgress = false
        context?.showToast("You're following $email now!")
        dismiss()
    }
    fun searchFailCallback(){
        searchInProgress = false
        context?.showToast("No user with this email was found")
    }
}