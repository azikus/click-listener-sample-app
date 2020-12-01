package com.azikus.clicklistenerexamples.ui.shared.listener

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.azikus.clicklistenerexamples.R

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
