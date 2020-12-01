package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
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
                behavior.onEnable(viewWeakReference.get())
            } else {
                behavior.onDisable(viewWeakReference.get())
            }
        }

    private var viewWeakReference: WeakReference<View?> = WeakReference(null)
    private val canTap = AtomicBoolean(true)

    open fun onTap(view: View?) {
        if (tapDisabled()) {
            return
        }
        viewWeakReference = WeakReference(view)
        isEnabled = false
        onTap()
    }

    private fun tapDisabled() = !canTap.getAndSet(false)
}
