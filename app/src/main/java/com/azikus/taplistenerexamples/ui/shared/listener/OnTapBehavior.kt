package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View

interface OnTapBehavior {
    fun onEnable(view: View?)
    fun onDisable(view: View?)
}
