@file:Suppress("DEPRECATION")

package com.example.hiltstarter.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.core.text.HtmlCompat
import androidx.viewpager2.widget.ViewPager2
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

fun formatDoubleNumber(value: Double): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = ' '
    val df = DecimalFormat("#,###", symbols)
    return df.format(value).replace(",", ".")
}

fun formatLongNumber(value: Long): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = ' '
    val df = DecimalFormat("#,###.###", symbols)
    return df.format(value).replace(",", ".")
}

fun calculateAgeFromDob(birthDate: String, dateFormat: String): Int {

    val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    val dob = Calendar.getInstance()
    dob.time = sdf.parse(birthDate) as Date

    val today = Calendar.getInstance()

    val curYear = today.get(Calendar.YEAR)
    val dobYear = dob.get(Calendar.YEAR)

    var age = curYear - dobYear

    try {
        // if dob is month or day is behind today's month or day
        // reduce age by 1
        val curMonth = today.get(Calendar.MONTH + 1)
        val dobMonth = dob.get(Calendar.MONTH + 1)
        if (dobMonth > curMonth) { // this year can't be counted!
            age--
        } else if (dobMonth == curMonth) { // same month? check for day
            val curDay = today.get(Calendar.DAY_OF_MONTH)
            val dobDay = dob.get(Calendar.DAY_OF_MONTH)
            if (dobDay > curDay) { // this year can't be counted!
                age--
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return age
}


fun getDP(context: Context, dp: Int): Int {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun fromHtml(html: String?): Spanned? {
    return when {
        html == null -> {
            SpannableString("")
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        }

        else -> {
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}

fun ViewPager2.currentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width,
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(animation: Animator) {
            endFakeDrag()
        }

        override fun onAnimationCancel(animation: Animator) { /* Ignored */
        }

        override fun onAnimationRepeat(animation: Animator) { /* Ignored */
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun vibrate(context: Context) {
    val v = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun getColumnSize(activity: Activity, orientation: Int): Double {
    val outMetrics = DisplayMetrics()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val display = activity.display
        display?.getRealMetrics(outMetrics)
    } else {
        @Suppress("DEPRECATION")
        val display = activity.windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(outMetrics)
    }

    return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        (outMetrics.widthPixels / 2.0)
    } else {
        (outMetrics.widthPixels / 3.2)
    }
}

fun getLanguage(language: String?): String {
    return when {
        language.isNullOrEmpty() -> {
            "uz_cyrl"
        }

        language == "uz" -> {
            "uz_cyrl"
        }

        language == "la" -> {
            "uz_latn"
        }

        else -> {
            language
        }
    }
}
