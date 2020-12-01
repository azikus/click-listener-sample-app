package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

class DoNothingOnTapBehavior : OnTapBehavior {
    override fun onEnable(view: View?) {
    }

    override fun onDisable(view: View?) {
    }
}
