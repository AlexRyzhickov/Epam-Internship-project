package com.epam.bet.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.epam.bet.databinding.RegistrationFragmentBinding
import com.epam.bet.extensions.showToast
import com.epam.bet.interfaces.AuthInterface
import com.epam.bet.viewmodel.BetsViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    private var bindingVar: RegistrationFragmentBinding? = null
    private val binding get() = bindingVar!!
    private lateinit var authInterface: AuthInterface
    lateinit var viewModel: BetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingVar = RegistrationFragmentBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this).get(BetsViewModel::class.java)

        activity?.let{
            instantiateAutorizationInterface(it)
        }

        binding.registrationBtn.setOnClickListener {
            val name = binding.userName.text.toString()
            val email = binding.userEmail.text.toString()
            val pass = binding.userPass.text.toString()
            val repeat_pass = binding.userRepeatPass.text.toString()

            createUser(name, email, pass, repeat_pass)
        }

        binding.openAuthBtn.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToAuthFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    fun createUser(name: String, email: String, pass : String, repeat_pass: String ){
        if (!name.equals("") && !email.equals("") && pass.equals(repeat_pass) && pass.length > 6) {
            mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        authInterface.signIn(name,email)
                        viewModel.addUser(email, name, context)
                        val action = RegistrationFragmentDirections.actionRegistrationFragmentToBetFragment()
                        findNavController().navigate(action)
                    } else {
                        context?.showToast("You haven't registered yet")
                    }
                }
        }
    }

    private fun instantiateAutorizationInterface(context: FragmentActivity) {
        authInterface = context as AuthInterface
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingVar = null
    }

}