package com.vogella.android.fragmenttesting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vogella.android.fragmenttesting.databinding.FragmentContainerBinding
import com.vogella.android.fragmenttesting.databinding.FragmentFirstBinding

class ContainerFragment : BaseFragment() {
    private var _binding: FragmentContainerBinding? = null
    private val binding get()  = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContainerBinding.inflate(layoutInflater)


         binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    replace(R.id.container_view,NewsFragment(),"")
                    true
                }
                R.id.page_2 -> {
                    replace(R.id.container_view,WebViewFragment(),"")
                    true
                }
                else -> false
            }
        }
        return binding.root
    }
}