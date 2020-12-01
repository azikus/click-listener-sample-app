package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import com.azikus.taplistenerexamples.R

private const val DEFAULT_INTERVAL = 5000L

open class DebouncedOnTapListener(
    onTap: () -> Unit,
    behavior: OnTapBehavior,
    private val intervalMs: Long = DEFAULT_INTERVAL
) : OnTapListener(onTap, behavior) {
    override fun onTap(view: View?) {
        super.onTap(view)
        view?.postDelayed({
            isEnabled = true
        }, intervalMs)
    }
}

fun View.onTapDebounced(onTap: () -> Unit, intervalMs: Long = DEFAULT_INTERVAL): OnTapListener {
    val tapListener = DebouncedOnTapListener(onTap, DoNothingOnTapBehavior(), intervalMs)
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapDebouncedAndDisable(onTap: () -> Unit): OnTapListener {
    val tapListener = DebouncedOnTapListener(onTap, DisableViewOnTapBehavior())
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapDebouncedAndChangeBackgroundColor(onTap: () -> Unit): OnTapListener {
    val tapListener = DebouncedOnTapListener(onTap, ChangeBackgroundColorOnTapBehavior(R.color.primaryColor, R.color.error))
    setOnClickListener(tapListener::onTap)
    return tapListener
}
