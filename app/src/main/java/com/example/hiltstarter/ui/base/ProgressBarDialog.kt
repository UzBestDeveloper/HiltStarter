package com.example.hiltstarter.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.hiltstarter.R
import com.example.hiltstarter.databinding.DialogProgressBinding

class ProgressBarDialog(context: Context) : Dialog(context, R.style.ProgressDialogTheme) {

    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (window != null) window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(true)
        this.setCanceledOnTouchOutside(true)
    }
}