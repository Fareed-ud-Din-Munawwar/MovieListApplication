package com.example.assignmentoptimized.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignmentoptimized.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = MyViewPagerAdapter(childFragmentManager, lifecycle)
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All Movies"
                    //tab.icon =
                }
                1 -> {
                    tab.text = "Favourites"
                    //tab.icon = ""
                }
            }
        }.attach()
    }

}


