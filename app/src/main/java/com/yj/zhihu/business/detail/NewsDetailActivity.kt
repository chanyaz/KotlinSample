package com.yj.zhihu.business.detail

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.MenuItem
import android.view.ViewConfiguration
import com.yj.zhihu.R
import com.yj.zhihu.common.base.BaseActivity
import com.yj.zhihu.common.extensions.hide
import com.yj.zhihu.common.extensions.show
import com.yj.zhihu.common.retrofit.YjRetrofit
import com.yj.zhihu.common.utils.ImageUtils
import com.yj.zhihu.common.utils.UriBuilder
import com.yj.zhihu.data.NewsDetail
import kotlinx.android.synthetic.main.yj_activity_news_detail.*
import org.jetbrains.anko.toast

/**
 * @author yuanjian 17/11/9.
 */
class NewsDetailActivity : BaseActivity() {

    private var actionBarHeight = 0
    private var state = STATE_EXPANDED

    companion object {
        private val STATE_EXPANDED = 1
        private val STATE_COLLAPSE = 2

        fun buildIntent(id: Long): Intent {
            return UriBuilder("news/detail").appendParam("id", id).toIntent()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yj_activity_news_detail)
        initToolBar()
        val data = intent?.data ?: return
        val id = data.getQueryParameter("id").toLong()
        actionBarHeight = getActionBarHeight()

        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offset = Math.abs(verticalOffset)
            if (appBarLayout.totalScrollRange - actionBarHeight <= offset) {
                if (state == STATE_COLLAPSE) {
                    state = STATE_EXPANDED
                    toolbar.alpha = 0f
                    toolbar.hide()
                }
            } else {
                state = STATE_COLLAPSE
                toolbar.alpha = 1 - offset / (appBarLayout.totalScrollRange - actionBarHeight).toFloat()
                toolbar.show()
            }
        }
        scroll_view.setOnScrollChangeListener { _: NestedScrollView?, _, scrollY, _, oldScrollY ->
            if ((state == STATE_EXPANDED) && Math.abs(oldScrollY - scrollY) > ViewConfiguration.get(this).scaledTouchSlop / 2) {
                if (oldScrollY > scrollY) {
                    toolbar.show()
                    toolbar.alpha = 1f
                } else {
                    toolbar.hide()
                }
            }
        }

        YjRetrofit.getNewsDetail(id)
                .compose(bindDestroy())
                .subscribe({
                    image_layout.show()
                    image_source.text = it.image_source
                    image_desc.text = it.title
                    ImageUtils.loadImage(this, it.getShowImage(), image)
                    loadWeb(it)
                }, {
                    it.printStackTrace()
                })

        // todo no handle error,maybe crash
        YjRetrofit.getNewsExtra(id)
                .compose(bindDestroy())
                .subscribe {
                    like_count.text = it.popularity.toString()
                    comment_count.text = it.comments.toString()
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        share.setOnClickListener { toast("share") }
        favorite.setOnClickListener { toast("favorite") }
        comment_layout.setOnClickListener { toast("comment") }
        like_layout.setOnClickListener { toast("like") }
    }

    private fun loadWeb(newsDetail: NewsDetail) {
        val webData = "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset=\"UTF-8\"><link rel=\"stylesheet\" href=\"detail.css\"></head>" +
                "<body>" + newsDetail.body.substring(newsDetail.body.indexOf("<div class=\"question\">")) + "</body>" +
                "</html>"
        webView.loadDataWithBaseURL("file:///android_asset/", webData, "text/html", "UTF-8", null)
    }

    private fun getActionBarHeight(): Int {
        val styledAttributes = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val size = styledAttributes.getDimension(0, 0F)
        styledAttributes.recycle()
        return size.toInt()
    }
}