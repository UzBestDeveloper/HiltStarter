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

    override fun setup() = with(binding) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url)
                return false // then it is not handled by default action
            }
        }
    }

    override fun observe() {

    }

    override fun clicks() = with(binding) {
        btnAuthenticate.singleClickable {
//            val your_client_id = "ihma_ins_web"
//            val your_redirect_uri = "com.example.hiltstarter"
//            val your_requested_scopes = "ihma_ins_api"
//
//            val authUrl = "https://tauth.ihmatest.uz/Account/Login" +
//                    "?response_type=code" +
//                    "&client_id=${your_client_id}" +
//                    "&redirect_uri=${your_redirect_uri}" +
//                    "&scope=${your_requested_scopes}"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))
//            startActivity(intent)
        }
    }

}