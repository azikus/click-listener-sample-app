package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View

class DisableViewOnTapBehavior : OnTapBehavior {
    override fun onEnable(view: View?) {
        view?.isEnabled = true
    }

    override fun onDisable(view: View?) {
        view?.isEnabled = false
    }
}
