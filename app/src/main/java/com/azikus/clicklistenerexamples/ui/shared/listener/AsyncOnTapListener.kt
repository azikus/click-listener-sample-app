package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

open class AsyncOnTapListener(
    private val onTap: () -> Unit
) : OnTapListener {

    override val view: View?
        get() = viewWeakReference?.get()

    private var viewWeakReference: WeakReference<View?>? = null
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (clickDisabled()) {
            return
        }
        viewWeakReference = WeakReference(v)
        disable()
        onTap()
    }

    override fun enable() {
        super.enable()
        canClick.set(true)
        viewWeakReference = null
    }

    private fun clickDisabled() = !canClick.getAndSet(false)
}

open class AsyncAndDisableOnTapListener(
    onTap: () -> Unit
) : AsyncOnTapListener(onTap) {
    override fun onEnable(v: View?) {
        v?.isEnabled = true
    }

    override fun onDisable(v: View?) {
        v?.isEnabled = false
    }
}

fun View.onTapAsync(onTap: () -> Unit): AsyncOnTapListener {
    val tapListener = AsyncOnTapListener(onTap)
    setOnClickListener(tapListener)
    return tapListener
}

fun View.onTapAsyncAndDisable(onTap: () -> Unit): AsyncOnTapListener {
    val tapListener = AsyncAndDisableOnTapListener(onTap)
    setOnClickListener(tapListener)
    return tapListener
}
