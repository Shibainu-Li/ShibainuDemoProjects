package com.sbl.demoapp.ui.activity.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sbl.demoapp.ui.fragment.OtherFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val mFragments = arrayListOf<Fragment>(OtherFragment(1), OtherFragment(2))

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

}