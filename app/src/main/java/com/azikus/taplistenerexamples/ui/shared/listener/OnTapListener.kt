package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import androidx.annotation.CallSuper
import java.util.concurrent.atomic.AtomicBoolean

abstract class OnTapListener(
    private val behavior: OnTapBehavior,
    private val onTap: () -> Unit
) {

    var isEnabled: Boolean
        get() = canTap.get()
        set(value) {
            canTap.set(value)
            if (value) {
                behavior.onEnabled(view)
            } else {
                behavior.onDisabled(view)
            }
        }

    private var view: View? = null
    private val canTap = AtomicBoolean(true)
    private val isTapDisabled: Boolean
        get() = !canTap.getAndSet(false)

    @CallSuper
    open fun onTap(view: View?) {
        if (isTapDisabled) return
        this.view = view
        isEnabled = false
        onTap()
    }
}
