package com.yj.zhihu

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.yj.zhihu.business.detail.NewsDetailActivity
import com.yj.zhihu.business.news.MainPageContract
import com.yj.zhihu.business.news.MainPagePresenter
import com.yj.zhihu.business.news.adpter.NewsRecyclerListAdapter
import com.yj.zhihu.common.base.BaseActivity
import com.yj.zhihu.common.extensions.hide
import com.yj.zhihu.common.extensions.show
import com.yj.zhihu.common.utils.Consts
import com.yj.zhihu.data.NewsItem
import io.reactivex.ObservableTransformer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MainPageContract.View {

    private var presenter: MainPageContract.Presenter = MainPagePresenter(this)
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        initRecyclerView()
        presenter.loadTopData()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initRecyclerView() {
        refresh_layout.setOnRefreshListener {
            presenter.refresh()
        }
        recycler_view.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayoutManager.VERTICAL }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                if (dy > 0 && layoutManager.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1 && !isLoading) {
                    presenter.loadNextDay()
                    isLoading = true
                }
            }
        })
    }

    override fun <T> viewAvoidStateLoss(): ObservableTransformer<T, T> {
        return bindDestroy()
    }

    override fun setState(state: Int) {
        when (state) {
            Consts.STATE_OK -> {
                recycler_view.show()
                progress.hide()
            }
            Consts.STATE_LOADING -> {
                recycler_view.hide()
                progress.show()
            }
        }
    }

    override fun showTopView(list: List<Any>) {
        isLoading = false
        recycler_view.adapter = NewsRecyclerListAdapter(this, list).apply {
            callback = { _, item, _, type ->
                if (type == NewsRecyclerListAdapter.ITEM_TYPE) {
                    startActivity(NewsDetailActivity.buildIntent((item as NewsItem).id))
                }
            }
        }
    }

    override fun addList(startPosition: Int, size: Int) {
        isLoading = false
        recycler_view.adapter.notifyItemRangeInserted(startPosition, size)
    }

    override fun stopRefresh() {
        refresh_layout.isRefreshing = false
    }
}
