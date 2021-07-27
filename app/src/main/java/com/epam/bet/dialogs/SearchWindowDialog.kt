package com.epam.bet.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.epam.bet.databinding.SearchWindowDialogBinding

class SearchWindowDialog: DialogFragment() {

    private var _binding: SearchWindowDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchWindowDialogBinding.inflate(inflater, container, false)

        binding.searchButton.setOnClickListener{
            var email = binding.searchField.text.trim()
        }
        binding.cancelButton.setOnClickListener{
            dismiss()
        }
        return binding.root
    }
}