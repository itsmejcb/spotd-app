package com.reyson.spotd.Class.Components.Block;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightedEditText {

    private Context context;

    public HighlightedEditText(Context context) {
        this.context = context;
    }

    public void applyHighlights(EditText editText) {
        editText.setMovementMethod(LinkMovementMethod.getInstance());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                applyHighlightsToText(s, editText);
            }
        });
    }

    private void applyHighlightsToText(Editable text, EditText editText) {
        SpannableStringBuilder spannableText = new SpannableStringBuilder(text);
        applyURLHighlights(spannableText);
        applyHashtagHighlights(spannableText);

        // Set the modified text back to the EditText
        editText.setText(spannableText);
        editText.setSelection(spannableText.length());
    }

    private void applyURLHighlights(SpannableStringBuilder text) {
        Pattern urlPattern = Pattern.compile("https?://\\S+");
        Matcher urlMatcher = urlPattern.matcher(text);

        while (urlMatcher.find()) {
            int start = urlMatcher.start();
            int end = urlMatcher.end();
            String url = text.subSequence(start, end).toString();

            ClickableSpan clickableSpan = createClickableSpanForURL(url);
            text.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void applyHashtagHighlights(SpannableStringBuilder text) {
        Pattern hashtagPattern = Pattern.compile("#\\w+");
        Matcher hashtagMatcher = hashtagPattern.matcher(text);

        while (hashtagMatcher.find()) {
            int start = hashtagMatcher.start();
            int end = hashtagMatcher.end();

            ClickableSpan clickableSpan = createClickableSpanForHashtag();
            text.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private ClickableSpan createClickableSpanForURL(String url) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle URL click (e.g., open in a web browser)
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#2196F3"));
                ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            }
        };
    }

    private ClickableSpan createClickableSpanForHashtag() {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle hashtag click (e.g., navigate to a specific page)
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#2196F3"));
                ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            }
        };
    }
}
