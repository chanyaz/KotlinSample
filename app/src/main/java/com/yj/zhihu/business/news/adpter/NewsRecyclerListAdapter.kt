package com.yj.zhihu.business.news.adpter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.yj.zhihu.R
import com.yj.zhihu.business.news.views.NewsBanner
import com.yj.zhihu.common.base.BaseRecyclerViewAdapter
import com.yj.zhihu.common.utils.ImageUtils
import com.yj.zhihu.common.utils.Ln
import com.yj.zhihu.data.NewsItem


/**
 * @author yuanjian 17/11/1.
 */
class NewsRecyclerListAdapter(context: Context, data: List<Any>) : BaseRecyclerViewAdapter<Any, RecyclerView.ViewHolder>(context, data) {

    companion object {
        private val TITLE_TYPE = 10
        val ITEM_TYPE = 11
        private val BANNER_TYPE = 12

        class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentView: TextView = itemView.findViewById(R.id.content)
            val imageView: ImageView = itemView.findViewById(R.id.image)
        }

        class NewsTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
        }

        class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val banner: NewsBanner = itemView.findViewById(R.id.banner)
        }
    }


    override fun getCommonItemViewType(position: Int): Int {
        return when {
            getItem(position) is NewsItem -> ITEM_TYPE
            getItem(position) is String -> TITLE_TYPE
            else -> BANNER_TYPE
        }
    }

    override fun onCreateCommonViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_TYPE -> NewsTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.yj_layout_news_title, parent, false))
            ITEM_TYPE -> NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.yj_layout_news_item, parent, false))
            else -> {
                return BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.yj_layout_news_banner, parent, false))
            }
        }
    }

    override fun bindDataToItemView(holder: RecyclerView.ViewHolder, item: Any, position: Int, viewType: Int) {
        if (viewType == TITLE_TYPE) {
            (holder as NewsTitleViewHolder).title.text = item as String
        } else if (viewType == ITEM_TYPE) {
            (holder as NewsViewHolder).contentView.text = (item as NewsItem).title
            ImageUtils.loadImage(context, item.getShowFirstFromImages(), holder.imageView)
        } else {
            val banner = (holder as BannerViewHolder).banner
            if (!banner.hasData()) {
                banner.setData(BannerPagerAdapter(context, item as List<NewsItem>, {
                    Ln.e("click---====${it.title}")
                }), 0)
            }
        }
    }

}