package com.example.practice.adapter

import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.viewmodels.RestaurantTown
import org.w3c.dom.Text

class StickyHeaderDecoration: RecyclerView.ItemDecoration {
    private var currentHeader: View? = null
    private var measuredWidth: Int = 0
    private var measuredHeight: Int = 0
    private var bottomPadding: Int = 0
    private var textViewHeight: Int = 0
    private val data: RestaurantTown

    constructor(data: RestaurantTown) {
        this.data = data
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        var index: Int = when(parent.layoutManager) {
            is LinearLayoutManager -> {
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
            else -> {
                (parent.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }
        }
        var holder: RecyclerView.ViewHolder? = null
        val title = data.list[index].town

        parent.getChildAt(0)?.let {
            holder = parent.getChildViewHolder(it)
        }

        if (currentHeader == null) {
            currentHeader = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_header_layout, parent, false)
            currentHeader!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val textView = currentHeader!!.findViewById<TextView>(R.id.titleTextView)
        if (holder is RestaurantAdapter.HeaderViewHolder) {
            textView?.text = title
            doMeasure(parent)
            currentHeader?.layout(0, 0, currentHeader!!.measuredWidth, currentHeader!!.measuredHeight)
            currentHeader?.draw(c)
            bottomPadding = currentHeader!!.bottom - textView.bottom
            textViewHeight = textView.height
        } else {
            currentHeader?.measure(measuredWidth, measuredHeight)
            val nextHolder = parent.findViewHolderForAdapterPosition(index + 1)

            if (nextHolder is RestaurantAdapter.HeaderViewHolder) {
                val nextTextView = nextHolder.itemView.findViewById<TextView>(R.id.titleTextView)
                if (textView.text == nextTextView?.text) {
                    textView.text = title
                    doMeasure(parent)
                }
                val titleBottom = Math.min(nextHolder.itemView.top, currentHeader!!.measuredHeight)
                currentHeader?.layout(0, 0, currentHeader!!.measuredWidth, titleBottom)
                textView.bottom = titleBottom - bottomPadding
                textView.top = textView.bottom - textViewHeight
                currentHeader?.draw(c)
            } else {
                textView.text = title
                doMeasure(parent)
                currentHeader?.layout(
                    0,
                    0,
                    currentHeader!!.measuredWidth,
                    currentHeader!!.measuredHeight
                )
                currentHeader?.draw(c)
            }
        }
    }

    private fun doMeasure(parent: RecyclerView) {
        measuredWidth = View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.EXACTLY)
        measuredHeight = View.MeasureSpec.makeMeasureSpec(parent.measuredHeight, View.MeasureSpec.AT_MOST)
        currentHeader!!.measure(measuredWidth, measuredHeight)
    }
}