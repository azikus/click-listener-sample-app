package com.azikus.clicklistenerexamples.ui.shared

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import com.azikus.clicklistenerexamples.R
import com.google.android.material.appbar.AppBarLayout

class BottomCornersAppBarLayout : AppBarLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private val bounds: Rect = Rect()
    private val clipPath: Path = Path()

    var radius: Float = 0f

    private fun init(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BottomCornersAppBarLayout, defStyleAttr, 0)
        radius = a.getDimension(R.styleable.BottomCornersAppBarLayout_radius, 0f)
        a.recycle()
    }

    override fun draw(canvas: Canvas) {
        canvas.getClipBounds(bounds)
        clipPath.addRoundRect(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat(),
            floatArrayOf(
                0f, 0f,
                0f, 0f,
                radius, radius,
                radius, radius
            ),
            Path.Direction.CW
        )
        canvas.clipPath(clipPath)
        super.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        outlineProvider = CustomOutline(w, h, radius)
    }

    private class CustomOutline(
        val width: Int,
        val height: Int,
        val radius: Float
    ) : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline) {
            outline.setRoundRect(0, 0, width, height, radius)
        }
    }

}
