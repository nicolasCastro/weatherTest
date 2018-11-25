package com.ncastro.weatherastrpay.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncastro.weatherastrpay.R


class ItemDecoration(context: Context, private val paint: Paint = Paint()): RecyclerView.ItemDecoration() {

    init {
        paint.color = ContextCompat.getColor(context, R.color.divider)
        paint.strokeWidth = context.resources.getDimension(R.dimen.divider)
    }
    private val dividerHeight: Int = 0
    private var layoutOrientation = -1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.layoutManager is LinearLayoutManager && layoutOrientation === -1) {
            layoutOrientation = (parent.layoutManager as LinearLayoutManager).orientation
        }

        if (layoutOrientation === LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, dividerHeight, 0)
        } else {
            outRect.set(0, 0, 0, dividerHeight)
        }

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if (layoutOrientation === LinearLayoutManager.HORIZONTAL) {
            horizontalDivider(c, parent)
        } else {
            verticalDivider(c, parent)
        }
    }

    private fun horizontalDivider(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val itemCount = parent.childCount
        for (i in 0 until itemCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            c.drawLine(left.toFloat(), top.toFloat(), left.toFloat(), bottom.toFloat(), paint)
        }
    }

    private fun verticalDivider(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            c.drawLine(left.toFloat(), top.toFloat(), right.toFloat(), top.toFloat(), paint)
        }
    }
}