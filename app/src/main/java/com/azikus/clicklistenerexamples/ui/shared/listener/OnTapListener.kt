package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

abstract class OnTapListener(
    private val onTap: () -> Unit,
    private val behavior: OnTapBehavior
) {

    var isEnabled: Boolean
        get() = canClick.get()
        set(value) {
            canClick.set(value)
            if (value) {
                behavior.onEnable(viewWeakReference.get())
            } else {
                behavior.onDisable(viewWeakReference.get())
            }
        }

    private var viewWeakReference: WeakReference<View?> = WeakReference(null)
    private val canClick = AtomicBoolean(true)

    open fun onTap(view: View?) {
        if (clickDisabled()) {
            return
        }
        viewWeakReference = WeakReference(view)
        isEnabled = false
        onTap()
    }

    private fun clickDisabled() = !canClick.getAndSet(false)
}
