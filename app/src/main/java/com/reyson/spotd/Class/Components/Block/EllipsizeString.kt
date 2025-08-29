package com.reyson.spotd.Class.Components.Block

import android.text.SpannableString
import android.text.TextUtils

object EllipsizeString {
    fun ellipsizeString(
        text: SpannableString,
        maxLength: Int,
        viewMoreText: String
    ): SpannableString {
        if (TextUtils.isEmpty(text)) {
            return text
        }
        if (text.length <= maxLength) {
            return text
        }
        val originalString = text.toString()
        val truncatedString =
            originalString.substring(0, maxLength - viewMoreText.length)
        val ellipsizedString = "$truncatedString...$viewMoreText"
        // Copy any spans from the original SpannableString to the new SpannableString if needed
        // Example: spannableEllipsizedString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, truncatedString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return SpannableString(ellipsizedString)
    }
}