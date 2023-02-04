package com.kalugin1912.todoit.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MarginItemDecoration(private val margin: Int) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = margin / 2
        outRect.right = margin / 2
        outRect.top = margin / 2
        outRect.bottom = margin / 2
    }
}