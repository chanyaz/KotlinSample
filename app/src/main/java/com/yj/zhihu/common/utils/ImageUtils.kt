package com.yj.zhihu.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.yj.zhihu.R

/**
 * @author yuanjian 17/11/3.
 */
object ImageUtils {

    private fun getGlideDefaultOption(): RequestOptions {
        return RequestOptions().format(DecodeFormat.DEFAULT)
    }

    @SuppressLint("CheckResult")
    fun loadImage(context: Context, url: String?, imageView: ImageView, centerCrop: Boolean = false, @DrawableRes placeHolder: Int = R.drawable.yj_bg_place_holder) {
        val requestManager = Glide.with(imageView)
        requestManager.clear(imageView)
        imageView.setTag(R.id.yj_load_image_flag, null)
        if (url.isNullOrEmpty()) {
            imageView.setImageResource(placeHolder)
        } else {
            var option = getGlideDefaultOption()
            option = if (placeHolder != 0) {
                option.placeholder(placeHolder)
            } else {
                option.placeholder(R.drawable.yj_bg_place_holder)
            }
            if (centerCrop) option = option.centerCrop()
            // todo error drawable
            option = option.error(R.drawable.yj_bg_place_holder)
            requestManager.load(url).apply(option).into(imageView)
        }
    }

}