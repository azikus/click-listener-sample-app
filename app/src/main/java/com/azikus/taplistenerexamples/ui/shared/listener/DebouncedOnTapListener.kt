package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import androidx.annotation.ColorRes
import com.azikus.taplistenerexamples.R

private const val DEFAULT_INTERVAL = 5000L

open class DebouncedOnTapListener(
    private val intervalMs: Long = DEFAULT_INTERVAL,
    behavior: OnTapBehavior,
    onTap: () -> Unit
) : OnTapListener(behavior, onTap) {

    override fun onTap(view: View?) {
        super.onTap(view)
        view?.postDelayed({
            isEnabled = true
        }, intervalMs)
    }
}

fun View.onTapDebounced(intervalMs: Long = DEFAULT_INTERVAL, onTap: () -> Unit): OnTapListener {
    val tapListener = DebouncedOnTapListener(intervalMs, DoNothingOnTapBehavior(), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapDebouncedAndDisable(intervalMs: Long = DEFAULT_INTERVAL, onTap: () -> Unit): OnTapListener {
    val tapListener = DebouncedOnTapListener(intervalMs, DisableViewOnTapBehavior(), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapDebouncedAndChangeBackgroundColor(
    intervalMs: Long = DEFAULT_INTERVAL,
    @ColorRes enabledColor: Int = R.color.primaryColor,
    @ColorRes disabledColor: Int = R.color.error,
    onTap: () -> Unit
): OnTapListener {
    val tapListener = DebouncedOnTapListener(intervalMs, ChangeBackgroundColorOnTapBehavior(enabledColor, disabledColor), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}
