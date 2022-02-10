package com.example.hiltstarter.ui.base

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context
    private val isInitialized = AtomicBoolean(false)

    open fun init(item: T) {}

    open fun callback(item: T) {}

    @CallSuper
    open fun bind(item: T) {
        if (isInitialized.compareAndSet(false, true)) init(item)
        callback(item)
    }

    open fun bindPayload(item: T) {}

    open fun bindPlaceholder() {}
}

fun <T> BaseViewHolder<T>.getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(context, id)
}