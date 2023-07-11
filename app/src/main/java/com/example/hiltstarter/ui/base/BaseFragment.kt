package com.example.hiltstarter.ui.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.hiltstarter.R
import com.example.hiltstarter.utils.color
import kotlinx.coroutines.launch

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    val navController by lazy { findNavController() }

    private var progressBarDialog: ProgressBarDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.rootView.setBackgroundColor(color(R.color.background))
        setup()
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                collect()
            }
        }
        observe()
        clicks()
    }

    open suspend fun collect() {}

    open fun observe() {}

    open fun setup() {}

    open fun clicks() {}

    fun back() = navController.popBackStack()

    fun hideProgress() {
        if (progressBarDialog != null) progressBarDialog!!.dismiss()
    }

    fun updateStatusColor(@ColorInt color: Int) {
        with(activity?.window ?: return) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = color
        }
    }

    fun fullScreenBar() {
        with(activity?.window ?: return) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insetsController?.apply {
                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    hide(WindowInsets.Type.statusBars())
                }
            } else setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

}