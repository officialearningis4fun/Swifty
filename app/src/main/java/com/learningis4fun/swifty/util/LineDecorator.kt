package com.learningis4fun.swifty.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import androidx.core.content.res.getDrawableOrThrow
import androidx.recyclerview.widget.DividerItemDecoration
import com.learningis4fun.swifty.R


class LineDecorator(val context: Context) {

    fun getLineDecoration(): DividerItemDecoration {
        val a: TypedArray = context.obtainStyledAttributes(intArrayOf(R.attr.dividerHorizontal))
        val divider = a.getDrawableOrThrow(0)
        val insetDrawable = InsetDrawable(divider, 0, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    /*fun getLineDecorationWithLeftPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.recycler_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getLineDecorationWithRightPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.recycler_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, 0, 0, inset, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getLineImageDecorationWithLeftPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.image_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getLineDecorationWithLeftAndRightPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.recycler_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, inset, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getDraftsListFragmentLineDecorationWithLeftPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.recycler_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getDraftsListFRagmentLineDecorationWithLeftPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.recycler_view_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }

    fun getContextMenuLineDecorationWithLeftPadding(): DividerItemDecoration? {
        val ATTRS = intArrayOf(R.attr.listDivider)
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset: Int =
            context.getResources().getDimensionPixelSize(R.dimen.context_menu_margin_divider)
        val insetDrawable = InsetDrawable(divider, inset, 0, 0, 0)
        a.recycle()
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(insetDrawable)
        return itemDecoration
    }*/
}