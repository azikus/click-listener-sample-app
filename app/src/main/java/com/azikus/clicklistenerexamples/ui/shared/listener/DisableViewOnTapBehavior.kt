package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

class DisableViewOnTapBehavior : OnTapBehavior {
    override fun onEnable(view: View?) {
        view?.isEnabled = true
    }

    override fun onDisable(view: View?) {
        view?.isEnabled = false
    }
}
