package com.epam.bet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.epam.bet.R
import com.epam.bet.adapters.ProfileTabsAdapter
import com.epam.bet.databinding.ProfileFragmentBinding
import com.epam.bet.databinding.RegistrationFragmentBinding
import com.epam.bet.interfaces.AuthInterface
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//https://epam.sharepoint.com/sites/EPAMSummerStudents2021/Shared%20Documents/General/Mocks/New%20Wireframe%201.png

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private var bindingVar: ProfileFragmentBinding? = null
    private val binding get() = bindingVar!!
    private lateinit var authInterface: AuthInterface
    private lateinit var adapter: ProfileTabsAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var tabNames: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingVar = ProfileFragmentBinding.inflate(inflater, container, false)

        activity?.let{
            instantiateAutorizationInterface(it)
        }

        tabNames = arrayOf(
            resources.getString(R.string.followers),
            resources.getString(R.string.iFollow)
        )

        val user = authInterface.getUserData()
        if (!user.name.equals("none") && !user.email.equals("none")){
            binding.userProfileName.text = resources.getString(R.string.userNickname, user.name)
            binding.userProfileEmail.text = resources.getString(R.string.userEmail, user.email)
        }

        binding.logOutBtn.setOnClickListener {
            authInterface.logOut()
            val action = ProfileFragmentDirections.actionProfileFragmentToAuthFragment()
            findNavController().navigate(action)
        }

        adapter = ProfileTabsAdapter(requireActivity())
        viewPager = binding.followersView
        viewPager.adapter = adapter
        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        return binding.root
    }

    private fun instantiateAutorizationInterface(context: FragmentActivity) {
        authInterface = context as AuthInterface
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingVar = null
    }

}