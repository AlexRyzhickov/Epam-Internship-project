package com.epam.bet.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.epam.bet.fragments.FollowersFragment
import com.epam.bet.fragments.IFollowFragment

class ProfileTabsAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        if (position == 0)
            return FollowersFragment()
        else
            return IFollowFragment()
    }
}