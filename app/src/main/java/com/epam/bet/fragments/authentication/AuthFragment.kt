package com.epam.bet.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.epam.bet.databinding.AuthFragmentBinding
import com.epam.bet.extensions.showToast
import com.epam.bet.interfaces.AuthInterface
import com.epam.bet.viewmodel.BetsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    private var _binding: AuthFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var authInterface: AuthInterface
    private lateinit var viewModel: BetsViewModel

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(this).get(BetsViewModel::class.java)

        activity?.let{
            instantiateAutorizationInterface(it)
        }

        binding.authorizationBtn.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val pass = binding.userPass.text.toString()
            signIn(email, pass)
        }

        binding.openRegistrationBtn.setOnClickListener {
            val action = AuthFragmentDirections.actionAuthFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    fun signIn(email : String, pass: String){
        if (email.trim().length >= 0 && pass.trim().length >= 6) {
            mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        users.document(email).get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    context?.showToast("Welcome to app !")
                                    val name = document.data?.get("name").toString()
                                    authInterface.signIn(name, email)
                                    val action = AuthFragmentDirections.actionAuthFragmentToBetFragment()
                                    findNavController().navigate(action)
                                } else {
                                    context?.showToast("Authentication failed")
                                }
                            }
                            .addOnFailureListener { exception ->
                                context?.showToast("Authentication failed")
                            }
                    } else {
                        context?.showToast("Authentication failed")
                    }
                }
        }
    }

    private fun instantiateAutorizationInterface(context: FragmentActivity) {
        authInterface = context as AuthInterface
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}