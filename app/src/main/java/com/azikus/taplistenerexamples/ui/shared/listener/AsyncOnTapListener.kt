package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import androidx.annotation.ColorRes
import com.azikus.taplistenerexamples.R

open class AsyncOnTapListener(
    behavior: OnTapBehavior,
    onTap: () -> Unit
) : OnTapListener(behavior, onTap)

fun View.onTapAsync(onTap: () -> Unit): OnTapListener {
    val tapListener = AsyncOnTapListener(DoNothingOnTapBehavior(), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapAsyncAndDisable(onTap: () -> Unit): OnTapListener {
    val tapListener = AsyncOnTapListener(DisableViewOnTapBehavior(), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}

fun View.onTapAsyncAndChangeBackgroundColor(
    @ColorRes enabledColor: Int = R.color.primaryColor,
    @ColorRes disabledColor: Int = R.color.error,
    onTap: () -> Unit
): OnTapListener {
    val tapListener = AsyncOnTapListener(ChangeBackgroundColorOnTapBehavior(enabledColor, disabledColor), onTap)
    setOnClickListener(tapListener::onTap)
    return tapListener
}
