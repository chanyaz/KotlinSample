//package com.yj.zhihu.common.views
//
//import android.content.Context
//import android.support.v4.view.ViewPager
//import android.util.AttributeSet
//import android.view.MotionEvent
//
///**
// * @author yuanjian 17/11/3.
// */
//class RecyclerViewPager : ViewPager {
//    constructor(context: Context) : super(context) {
//
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//
//    }
//
//    /****
//     * 滑动距离及坐标 归还父控件焦点
//     ****/
//    private float xDistance, yDistance, xLast, yLast;
//    /**
//     * 是否是左右滑动
//     **/
//    private boolean mIsBeingDragged = true;
//
//
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        swit
//        return super.dispatchTouchEvent(ev)
//    }
//
//    /**
//     *重写这个方法纯属是为了告诉Recyclerview，什么时候不要拦截viewpager的滑动
//     *事件
//     **/
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            xDistance = yDistance = 0f;
//            xLast = ev.getX();
//            yLast = ev.getY();
//            break;
//            case MotionEvent.ACTION_MOVE:
//            final float curX = ev.getX();
//            final float curY = ev.getY();
//            xDistance += Math.abs(curX - xLast);
//            yDistance += Math.abs(curY - yLast);
//            xLast = curX;
//            yLast = curY;
//            if (!mIsBeingDragged) {
//                if (yDistance < xDistance * 0.5) {//小于30度都左右滑
//                    mIsBeingDragged = true;
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                } else {
//                    mIsBeingDragged = false;
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//            }
//            break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            mIsBeingDragged = false;
//            break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }}
//
//}