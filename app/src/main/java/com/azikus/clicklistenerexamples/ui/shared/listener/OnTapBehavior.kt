package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

interface OnTapBehavior {
    fun onEnable(view: View?)
    fun onDisable(view: View?)
}
