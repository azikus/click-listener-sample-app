package com.azikus.clicklistenerexamples.ui.shared.listener

import android.view.View
import androidx.databinding.BindingAdapter
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

private const val DEFAULT_INTERVAL = 250L

open class DebouncedOnTapListener(
    private val onTap: () -> Unit,
    private val intervalMs: Long = DEFAULT_INTERVAL
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

        v?.postDelayed({
            canClick.set(true)
            enable()
        }, intervalMs)
    }

    private fun clickDisabled() = !canClick.getAndSet(false)
}

open class DebouncedAndDisableOnTapListener(
    onTap: () -> Unit,
    intervalMs: Long = DEFAULT_INTERVAL
) : DebouncedOnTapListener(onTap, intervalMs) {
    override fun onEnable(v: View?) {
        v?.isEnabled = true
    }

    override fun onDisable(v: View?) {
        v?.isEnabled = false
    }
}

@BindingAdapter(value = ["onTapDebounced", "onTapDebouncedAndDisable", "intervalMs"], requireAll = false)
fun View.onTapDebouncedAndDisableBinding(onTap: View.OnClickListener?, onTapAndDisable: View.OnClickListener?, intervalMs: Long?) {
    when {
        onTap != null -> onTapDebounced({ onTap.onClick(null) }, intervalMs ?: DEFAULT_INTERVAL)
        onTapAndDisable != null -> onTapDebouncedAndDisable({ onTapAndDisable.onClick(null) }, intervalMs ?: DEFAULT_INTERVAL)
        else -> setOnClickListener(null)
    }
}

fun View.onTapDebounced(onTap: () -> Unit, intervalMs: Long = DEFAULT_INTERVAL): DebouncedOnTapListener {
    val tapListener = DebouncedOnTapListener(onTap, intervalMs)
    setOnClickListener(tapListener)
    return tapListener
}

fun View.onTapDebouncedAndDisable(onTap: () -> Unit, intervalMs: Long = DEFAULT_INTERVAL): DebouncedOnTapListener {
    val tapListener = DebouncedAndDisableOnTapListener(onTap, intervalMs)
    setOnClickListener(tapListener)
    return tapListener
}
