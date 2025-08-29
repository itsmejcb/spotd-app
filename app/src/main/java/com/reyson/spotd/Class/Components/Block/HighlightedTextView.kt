package com.reyson.spotd.Class.Components.Block

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.reyson.spotd.Data.Model.Hashtag
import com.reyson.spotd.Data.Model.Hashtag.Companion.instance
import com.reyson.spotd.R
import java.util.regex.Pattern

class HighlightedTextView(private val context: Context) {
    private val hashtagData: Hashtag?

    init {
        hashtagData = instance
    }

    fun setTextWithHighlights(
        textView: TextView,
        text: String,
        maxLength: Int,
        enableEllipsis: Boolean
    ) {
        var text = text
        text = text.replace("\\n", "\n")
        text = removeLeadingSpaces(text)
        textView.movementMethod = LinkMovementMethod.getInstance()
        val spannableString = createSpannableStringWithHighlights(text)
        if (enableEllipsis && text.length > maxLength) {
            val truncatedText = text.substring(0, maxLength) + " ... view more"
            val truncatedSpannableString = createSpannableStringWithHighlights(truncatedText)
            textView.text = truncatedSpannableString
        } else {
            textView.text = spannableString
        }
    }

    private fun createSpannableStringWithHighlights(text: String): SpannableString {
        // Regular expression patterns for URLs and hashtags
        val urlPattern = Pattern.compile("https?://\\S+")
        val hashtagPattern = Pattern.compile("#\\w+")

        // Matcher for URLs
        val urlMatcher = urlPattern.matcher(text)
        val spannableString = SpannableString(text)
        while (urlMatcher.find()) {
            val start = urlMatcher.start()
            val end = urlMatcher.end()
            val url = text.substring(start, end)
            val clickableSpan = createUrlClickableSpan(url)
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // Matcher for hashtags
        val hashtagMatcher = hashtagPattern.matcher(text)
        while (hashtagMatcher.find()) {
            val start = hashtagMatcher.start()
            val end = hashtagMatcher.end()
            val hashtag = text.substring(start, end)
            val clickableSpan = createHashtagClickableSpan(hashtag)
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString
    }

    private fun createUrlClickableSpan(url: String): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                openURLInChromeCustomTab(url)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#2196F3")
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }

    private fun createHashtagClickableSpan(hashtag: String): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                Log.i("word click", hashtag)
                // hashtagData.setHashtag(hashtag);
                // intent(Hashtag.class, R.anim.right_in, R.anim.left_out);
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#2196F3")
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }

    private fun openURLInChromeCustomTab(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.charlestonGreen))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    fun setDescription(
        textView: TextView?,
        username: String?,
        fullname: String?,
        text: String?,
        profile: String?
    ) {
        // textView.setOnClickListener(view ->
        // block.description(context, username, fullname, text, profile)
        // );
    }

    fun intent(targetActivityClass: Class<*>?, enterAnimationResId: Int, exitAnimationResId: Int) {
        val i = Intent(context, targetActivityClass)
        val options =
            ActivityOptions.makeCustomAnimation(context, enterAnimationResId, exitAnimationResId)
        context.startActivity(i, options.toBundle())
    }

    fun removeLeadingSpaces(input: String): String {
        val lines = input.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val result = StringBuilder()
        for (line in lines) {
            val trimmedLine = line.trim { it <= ' ' }
            result.append(trimmedLine).append("\n")
        }
        if (result.isNotEmpty()) {
            result.setLength(result.length - 1)
        }
        return result.toString()
    }
}