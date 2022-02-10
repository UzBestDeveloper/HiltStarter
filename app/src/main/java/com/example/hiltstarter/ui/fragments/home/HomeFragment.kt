package com.example.hiltstarter.ui.fragments.home

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hiltstarter.R
import com.example.hiltstarter.databinding.FragmentHomeBinding
import com.example.hiltstarter.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(
    R.layout.fragment_home
) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun setup() {

    }

    override fun observe() {

    }

    override fun clicks() = with(binding) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}