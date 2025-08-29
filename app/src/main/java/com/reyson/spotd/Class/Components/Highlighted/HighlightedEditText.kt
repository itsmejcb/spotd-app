package com.reyson.spotd.Class.Components.Highlighted

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import java.util.regex.Pattern

class HighlightedEditText(private val context: Context) {
    fun applyHighlights(editText: EditText) {
        editText.movementMethod = LinkMovementMethod.getInstance()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable) {
                applyHighlightsToText(s, editText)
            }
        })
    }

    private fun applyHighlightsToText(text: Editable, editText: EditText) {
        val spannableText = SpannableStringBuilder(text)
        applyURLHighlights(spannableText)
        applyHashtagHighlights(spannableText)

        // Set the modified text back to the EditText
        editText.text = spannableText
        editText.setSelection(spannableText.length)
    }

    private fun applyURLHighlights(text: SpannableStringBuilder) {
        val urlPattern = Pattern.compile("https?://\\S+")
        val urlMatcher = urlPattern.matcher(text)
        while (urlMatcher.find()) {
            val start = urlMatcher.start()
            val end = urlMatcher.end()
            val url = text.subSequence(start, end).toString()
            val clickableSpan = createClickableSpanForURL(url)
            text.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun applyHashtagHighlights(text: SpannableStringBuilder) {
        val hashtagPattern = Pattern.compile("#\\w+")
        val hashtagMatcher = hashtagPattern.matcher(text)
        while (hashtagMatcher.find()) {
            val start = hashtagMatcher.start()
            val end = hashtagMatcher.end()
            val clickableSpan = createClickableSpanForHashtag()
            text.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun createClickableSpanForURL(url: String): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle URL click (e.g., open in a web browser)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#2196F3")
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }

    private fun createClickableSpanForHashtag(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle hashtag click (e.g., navigate to a specific page)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#2196F3")
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }
}