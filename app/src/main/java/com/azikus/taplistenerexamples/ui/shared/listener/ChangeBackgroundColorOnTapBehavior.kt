package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class ChangeBackgroundColorOnTapBehavior(
    @ColorRes private val enabledColor: Int,
    @ColorRes private val disabledColor: Int
) : OnTapBehavior {

    override fun onEnable(view: View?) {
        if (view != null) {
            view.isEnabled = true
            view.setBackgroundColor(ContextCompat.getColor(view.context, enabledColor))
        }
    }

    override fun onDisable(view: View?) {
        if (view != null) {
            view.isEnabled = false
            view.setBackgroundColor(ContextCompat.getColor(view.context, disabledColor))
        }
    }
}
