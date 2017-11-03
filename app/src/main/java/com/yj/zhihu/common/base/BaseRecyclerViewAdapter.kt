package com.yj.zhihu.common.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


/**
 * @author yuanjian 17/11/1.
 */
abstract class BaseRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(protected var context: Context,
                                                                        protected var mData: List<T>) : RecyclerView.Adapter<VH>() {

    var callback: ((view: View, item: T, position: Int, type: Int) -> Unit)? = null

    companion object {
        private val HEADER_TYPE = 100
        private val BOTTOM_TYPE = 101
    }

    protected var mHeaderView: View? = null
    protected var mFooterView: View? = null
    private var showHeader: Boolean = false
    private var showFooter: Boolean = false


    override fun onBindViewHolder(holder: VH, position: Int) {
        if (position == 0 && showHeader) {
            bindHeaderItemView(holder, position)
            return
        }

        if (position == itemCount - 1 && showFooter) {
            bindFooterItemView(holder, position)
            return
        }

        val type = getItemViewType(position)
        if (type != HEADER_TYPE && type != BOTTOM_TYPE) {
            val item = getItem(position)
            bindDataToItemView(holder, item, getRealPos(position), type)
            bindItemViewClickListener(holder, item, getRealPos(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        if (viewType == HEADER_TYPE && mHeaderView != null) return BaseViewHolder(mHeaderView!!) as VH
        if (viewType == BOTTOM_TYPE && mFooterView != null) return BaseViewHolder(mFooterView!!) as VH
        return onCreateCommonViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        if (showHeader && position == 0) {
            return HEADER_TYPE;
        }
        if (showFooter && position == getItemCount() - 1) {
            return BOTTOM_TYPE;
        }
        return getCommonItemViewType(position);
    }

    override fun getItemCount(): Int {
        return if (showHeader && showFooter) {
            mData.size + 2
        } else if (showHeader) {
            mData.size + 1
        } else if (showFooter) {
            mData.size + 1
        } else {
            mData.size
        }
    }

    protected open fun bindHeaderItemView(holder: VH, position: Int) {

    }

    protected open fun bindFooterItemView(holder: VH, position: Int) {

    }

    protected open fun getCommonItemViewType(position: Int): Int {
        return 0
    }

    protected abstract fun onCreateCommonViewHolder(parent: ViewGroup?, viewType: Int): VH

    /**
     * 从当前位置获取数据对象
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T {
        return mData[getRealPos(position)]
    }

    protected fun getRealPos(position: Int): Int {
        return if (showHeader) position - 1 else position
    }

    /**
     * 为ItemView绑定数据
     *
     * @param holder holder
     * @param item   item
     */
    protected abstract fun bindDataToItemView(holder: VH, item: T, position: Int, viewType: Int)

    /**
     * 绑定点击事件
     *
     * @param vh       viewholder
     * @param item     item
     * @param position
     */
    protected fun bindItemViewClickListener(vh: VH, item: T, position: Int) {
        vh.itemView.setOnClickListener({ callback?.invoke(it, item, position, getItemViewType(position)) })
    }


    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}