package com.vogella.android.fragmenttesting

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment: DialogFragment() {
    fun replace(containerId: Int, baseFragment: BaseFragment, argument: String?) {
        (activity as FragmentActivity).supportFragmentManager.beginTransaction().replace(containerId,baseFragment,argument).commit()
    }
}