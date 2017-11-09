package com.yj.zhihu.common.base

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author yuanjian 17/11/6.
 */
class BaseRecyclerViewHolder(private val convertView: View) : RecyclerView.ViewHolder(convertView) {
    fun <T : View> findView(@IdRes viewId: Int): T {
        // todo
        return convertView.findViewById(viewId)
    }
}