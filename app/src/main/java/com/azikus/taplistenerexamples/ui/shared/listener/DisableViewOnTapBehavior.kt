package com.azikus.taplistenerexamples.ui.shared.listener

import android.view.View

class DisableViewOnTapBehavior : OnTapBehavior {
    override fun onEnabled(view: View?) {
        view?.isEnabled = true
    }

    override fun onDisabled(view: View?) {
        view?.isEnabled = false
    }
}
