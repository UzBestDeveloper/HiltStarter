package com.example.hiltstarter.ui.activities

import android.content.Intent
import android.os.Bundle
import com.example.hiltstarter.databinding.ActivityMainBinding
import com.example.hiltstarter.ui.base.BaseActivity
import com.example.hiltstarter.ui.fragments.home.OAuthManager
import com.example.hiltstarter.ui.fragments.home.OpenIDConnectHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var oidcHelper: OpenIDConnectHelper
    private var oAuthManager: OAuthManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        oAuthManager = OAuthManager(this)
        oAuthManager!!.initiateOAuth()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val callbackUri = intent.data
        if (callbackUri != null) {
            oAuthManager!!.handleAuthorizationCallback(callbackUri, object :
                OAuthManager.AuthorizationCallback {
                override fun onAuthorizationSuccess(accessToken: OAuthManager.AccessToken) {
                    // Handle successful authorization
                    // Save the access token for future requests
                }

                override fun onAuthorizationFailed() {
                    // Handle failed authorization
                }
            })
        }
    }

}