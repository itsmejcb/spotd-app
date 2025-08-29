package com.reyson.spotd.Class.Components.Fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.reyson.spotd.Class.Fragments.Home.Foryou.Foryou

class FragFragmentAdapter(context: Context, manager: FragmentManager?) :
    FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var tabCount = 0

    fun setTabCount(tabCount: Int) {
        this.tabCount = tabCount
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Post"
            // Handle other positions if needed
            else -> null
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Foryou.newInstance()
            // Handle other positions if needed
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
