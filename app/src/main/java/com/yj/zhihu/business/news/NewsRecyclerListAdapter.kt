package com.yj.zhihu.business.news

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.yj.zhihu.R
import com.yj.zhihu.common.base.BaseRecyclerViewAdapter
import com.yj.zhihu.common.utils.ImageUtils
import com.yj.zhihu.data.NewsItem


/**
 * @author yuanjian 17/11/1.
 */
class NewsRecyclerListAdapter(context: Context, data: List<Any>) : BaseRecyclerViewAdapter<Any, RecyclerView.ViewHolder>(context, data) {

    companion object {
        private val TITLE_TYPE = 10
        private val ITEM_TYPE = 11

        class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentView: TextView = itemView.findViewById(R.id.content)
            val imageView: ImageView = itemView.findViewById(R.id.image)
        }

        class NewsTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
        }
    }


    override fun getCommonItemViewType(position: Int): Int {
        return if (getItem(position) is NewsItem) ITEM_TYPE else TITLE_TYPE
    }

    override fun onCreateCommonViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TITLE_TYPE) {
            NewsTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.yj_layout_news_title, parent, false))
        } else {
            NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.yj_layout_news_item, parent, false))
        }
    }

    override fun bindDataToItemView(holder: RecyclerView.ViewHolder, item: Any, position: Int, viewType: Int) {
        if (viewType == TITLE_TYPE) {
            (holder as NewsTitleViewHolder).title.text = item as String
        } else {
            (holder as NewsViewHolder).contentView.text = (item as NewsItem).title
            ImageUtils.loadImage(context, item.getShowFirstFromImages(), holder.imageView)
        }
    }

}