package com.blueberrysolution.pinelib19.addone.convert

import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getResourceId
import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.text.Html
import androidx.core.text.HtmlCompat
import android.R
import android.content.Context
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.view.View
import android.view.ViewTreeObserver
import android.text.TextUtils
import android.text.Layout









//方案二：动态设置缩进距离的方式
fun TextView.setText(tag: View, words: String) {
    val observer = tag.viewTreeObserver
    observer.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            val spannableString = SpannableString(words)
            //这里没有获取margin的值，而是直接写死的
            val what = LeadingMarginSpan.Standard(tag.width + dip2px(tag.context, 5.0), 0)
            spannableString.setSpan(
                what,
                0,
                spannableString.length,
                SpannableString.SPAN_INCLUSIVE_INCLUSIVE
            )
            text = spannableString
            tag.viewTreeObserver.removeOnPreDrawListener(
                this
            )
            return false
        }
    })
}



fun dip2px(context: Context, dpValue: Double): Int {
    val density = context.resources.displayMetrics.density
    return (dpValue * density + 0.5).toInt()
}