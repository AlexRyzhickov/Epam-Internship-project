package com.epam.bet.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.epam.bet.databinding.SearchWindowDialogBinding
import com.epam.bet.extensions.showToast
import com.epam.bet.viewmodel.FollowersViewModel
import com.epam.bet.viewmodel.IFollowViewModel
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

        val viewModel =  get<IFollowViewModel>()

        binding.searchButton.setOnClickListener{
            if (!searchInProgress) {
                var email: String = binding.searchField.text.trim().toString()
                if (!viewModel.isIFollow(email)){
                    searchInProgress = true
                    viewModel.addIFollow(email, ::searchSuccessCallback, ::searchFailCallback)
                }
                else {
                    context?.showToast("You're already following this user!")
                }
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