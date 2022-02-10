package com.example.hiltstarter.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

var View.visible: Boolean
    set(value) {
        isInvisible = !value
    }
    get() {
        return isVisible
    }

val ViewGroup.layoutInflater: LayoutInflater
    get() {
        return LayoutInflater.from(context)
    }

var TextView.textError: String?
    get() {
        return text.toString()
    }
    set(value) {
        text = value

        if (value == null) isGone = true
        else isVisible = true
    }

val Number.dp get() = toFloat() * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

var View.fadeTo: Boolean
    set(value) {
        fadeTo(value)
    }
    get() {
        return isVisible
    }

fun RecyclerView.itemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
    if (itemDecorationCount == 0) addItemDecoration(itemDecoration)
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.build(
    recyclerAdapter: RecyclerView.Adapter<VH>,
    recyclerLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
) {
    adapter = recyclerAdapter
    layoutManager = recyclerLayoutManager
}

fun View.resize(newWidth: Int = layoutParams.width, newHeight: Int = layoutParams.height) {
    layoutParams.width = newWidth
    layoutParams.height = newHeight
}

fun View.fadeTo(visible: Boolean, duration: Long = 500, startDelay: Long = 0, toAlpha: Float = 1f) {
    // Make this idempotent.
    val tagKey = "fadeTo".hashCode()
    if (visible == isVisible && animation == null && getTag(tagKey) == null) return
    if (getTag(tagKey) == visible) return

    setTag(tagKey, visible)
    setTag("fadeToAlpha".hashCode(), toAlpha)

    if (visible && alpha == 1f) alpha = 0f
    animate()
        .alpha(if (visible) toAlpha else 0f)
        .withStartAction {
            if (visible) isVisible = true
        }
        .withEndAction {
            setTag(tagKey, null)
            if (isAttachedToWindow && !visible) isVisible = false
        }
        .setInterpolator(FastOutSlowInInterpolator())
        .setDuration(duration)
        .setStartDelay(startDelay)
        .start()
}

/**
 * Cancels the animation started by [fadeTo] and jumps to the end of it.
 */
fun View.cancelFade() {
    val tagKey = "fadeTo".hashCode()
    val visible = getTag(tagKey)?.castOrNull<Boolean>() ?: return
    animate().cancel()
    isVisible = visible
    alpha = if (visible) getTag("fadeToAlpha".hashCode())?.castOrNull<Float>() ?: 1f else 0f
    setTag(tagKey, null)
}

/**
 * Cancels the fade for this view and any ancestors.
 */
fun View.cancelFadeRecursively() {
    cancelFade()

    castOrNull<ViewGroup>()?.children?.asSequence()?.forEach { it.cancelFade() }
}

inline fun <reified R> Any.castOrNull(): R? {
    return this as? R
}