package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import androidx.annotation.ColorRes
import com.azikus.taplistenerexamples.R

open class AsyncOnTapListener(
    onTap: () -> Unit,
    behavior: OnTapBehavior
) : OnTapListener(onTap, behavior)

fun View.onTapAsync(onTap: () -> Unit): OnTapListener {
    val tapListener = AsyncOnTapListener(onTap, DoNothingOnTapBehavior())
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapAsyncAndDisable(onTap: () -> Unit): OnTapListener {
    val tapListener = AsyncOnTapListener(onTap, DisableViewOnTapBehavior())
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapAsyncAndChangeBackgroundColor(
    @ColorRes enabledColor: Int = R.color.primaryColor,
    @ColorRes disabledColor: Int = R.color.error,
    onTap: () -> Unit
): OnTapListener {
    val tapListener = AsyncOnTapListener(onTap, ChangeBackgroundColorOnTapBehavior(enabledColor, disabledColor))
    setOnClickListener(tapListener::onTap)
    return tapListener
}
