package com.example.hiltstarter.ui.fragments.home

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.hiltstarter.R
import com.example.hiltstarter.databinding.FragmentHomeBinding
import com.example.hiltstarter.ui.base.BaseFragment
import com.example.hiltstarter.utils.singleClickable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(
    R.layout.fragment_home
) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun observe() {

    }

    override fun clicks() {

    }

}