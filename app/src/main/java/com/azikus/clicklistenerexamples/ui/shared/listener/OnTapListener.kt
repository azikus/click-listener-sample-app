package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View

interface OnTapListener : View.OnClickListener {

    val view: View?

    fun enable() {
        onEnable(view)
    }

    fun disable() {
        onDisable(view)
    }

    fun onEnable(v: View?) {
    }

    fun onDisable(v: View?) {
    }
}
