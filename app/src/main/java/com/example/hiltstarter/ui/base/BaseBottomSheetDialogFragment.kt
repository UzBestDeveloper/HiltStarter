package com.example.hiltstarter.ui.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hiltstarter.utils.color
import com.example.hiltstarter.utils.px
import com.example.hiltstarter.utils.screenResolution
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDialogFragment(
    @LayoutRes private val resId: Int
) : BottomSheetDialogFragment(

) {

    var canFullHeight = false
    var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(resId, container, false)
        return mView
    }

    @CallSuper
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet: FrameLayout? =
                    findViewById(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
                onShow(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                collect()
            }
        }
        observe()
        setup()
        clicks()
    }

    open suspend fun collect() {}
    open fun observe() {}
    open fun setup() {}
    open fun clicks() {}

    @CallSuper
    open fun onShow(dialogInterface: DialogInterface) {
        if (canFullHeight) {
            val dialog = dialogInterface as BottomSheetDialog
            val sheet: FrameLayout? =
                dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)

            val layoutParams = sheet?.layoutParams

            val windowHeight = requireActivity().screenResolution.height

            layoutParams?.height = windowHeight
            sheet?.layoutParams = layoutParams
        }
    }

    fun bottomSheetBackground(
        @ColorRes dynamicColor: Int? = null,
        dynamicCornerRadius: Float? = null,
        v1: Float = 0f,
        v2: Float = 0f,
        v3: Float = 0f,
        v4: Float = 0f
    ): Drawable {
        return GradientDrawable().apply {

            if (dynamicColor != null)
                setColor(color(dynamicColor))

            if (dynamicCornerRadius != null)
                cornerRadius = px(dynamicCornerRadius)

            cornerRadii = floatArrayOf(v1, v1, v2, v2, v3, v3, v4, v4)
        }
    }
}