package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View

interface OnTapBehavior {
    fun onEnabled(view: View?)
    fun onDisabled(view: View?)
}
