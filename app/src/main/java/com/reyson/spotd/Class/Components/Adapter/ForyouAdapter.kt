package com.reyson.spotd.Class.Components.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reyson.spotd.Class.Activity.Profile.Profile
import com.reyson.spotd.Class.Components.Block.EllipsizeString
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.HighlightedTextView
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY.Companion.POST_PUSH_KEY
import com.reyson.spotd.Data.Api.KEY.Companion.USER_UID
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.removeString
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R
import org.w3c.dom.Comment
import java.util.*
import java.util.regex.Pattern


class ForyouAdapter(private val context: Context, private val posts: ArrayList<Posts>) :
    RecyclerView.Adapter<ForyouAdapter.ViewHolder>() {
    private var objectClicked: String? = null
    private var isClickableSpanClicked: Boolean = false
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val viewPool = RecyclerView.RecycledViewPool()
    private val helper: Helper = Helper(context)
    private var cal: Calendar = Calendar.getInstance()
    private var highlightedTextView: HighlightedTextView = HighlightedTextView(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = inflater.inflate(R.layout.fragment_foryou, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        if (post != null) {
            holder.loadUsername(post.username)
            holder.loadFullName(post.full_name)
            Log.i("status of fullname", post.full_name)
            holder.loadProfile(post.uid +"/"+ post.profile_picture)
            val profile = post.uid +"/"+ post.profile_picture
            // holder.intentProfile(uid.get(0))
            highlightedTextView.setTextWithHighlights(holder.content, post.caption, 50, true)
            highlightedTextView.setDescription(
                holder.content,
                post.username,
                post.full_name,
                post.caption,
                profile
            )

            holder.loadMS(post.ms)
            holder.loadStatus(post.status)
            holder.loadTotalLikes(post.total_likes)
            holder.loadTotalComments(post.total_comments)
            holder.loadTotalShares(post.total_share)
            holder.intentComment(post.push_key, "")
            // holder.functionLikes(post.push_key, post.total_likes, post.isLiked)
            // TODO fix and implements some features in newfeed
            // more function such as report and many more in more functions
            val childAdapter = ForYouChildAdapter(context, post.images)
            childAdapter.setPath(post.uid)
            childAdapter.setPushKey(post.push_key)
            holder.childRecyclerView.adapter = childAdapter
            val layoutManager = LinearLayoutManager(
                holder.childRecyclerView.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            holder.childRecyclerView.layoutManager = layoutManager
            holder.childRecyclerView.setRecycledViewPool(viewPool)
            holder.transition.visibility = View.VISIBLE
            holder.status.visibility = View.GONE

        } else {
            holder.transition.visibility = View.GONE
        }
    }


    override fun getItemCount(): Int {
        return posts.size
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < posts.size) {
            posts.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var childRecyclerView: RecyclerView = v.findViewById(R.id.ffy_recyclerview)
        var username: TextView = v.findViewById(R.id.ffy_username)
        var full_name: TextView = v.findViewById(R.id.ffy_full_name)
        var profile: ImageView = v.findViewById(R.id.ffy_profile)
        var content: TextView = v.findViewById(R.id.ffy_context)
        var time: TextView = v.findViewById(R.id.ffy_time)
        var status: TextView = v.findViewById(R.id.ffy_status)
        var like: ImageView = v.findViewById(R.id.ffy_like)
        var like_label: TextView = v.findViewById(R.id.ffy_like_label)
        var comment: ImageView = v.findViewById(R.id.ffy_comment)
        var comment_label: TextView = v.findViewById(R.id.ffy_comment_label)
        var share: ImageView = v.findViewById(R.id.ffy_share)
        var share_label: TextView = v.findViewById(R.id.ffy_share_label)
        var more: ImageView = v.findViewById(R.id.ffy_more)
        var transition: LinearLayout = v.findViewById(R.id.ffy_transition)
        var like_layout: LinearLayout = v.findViewById(R.id.ffy_like_layout)
        var comment_layout: LinearLayout = v.findViewById(R.id.ffy_comment_layout)
        var shared_layout: LinearLayout = v.findViewById(R.id.ffy_shared_layout)

        fun loadUsername(key: String?) {
            username.text = key
        }

        fun loadFullName(key: String?) {
            full_name.text = key
        }

        fun intentProfile(uid: String?) {
            profile?.setOnClickListener {
                saveString(context, USER_UID, uid)
                helper.intent(
                    Profile::class.java,
                    R.anim.right_in,
                    R.anim.left_out
                )
            }
        }

        fun loadProfile(str: String) {
            val xx = ContextCompat.getDrawable(context, R.drawable.img_logo)
            xx?.let { helper.loadProfile(str, profile, it) }
        }

        fun loadMS(str: String?) {
            if (str != null) {
                currentTime(str.toDouble(), time)
                time.visibility = View.VISIBLE
            } else {
                time.visibility = View.GONE
            }
        }

        fun loadStatus(str: String?) {
            if (str != null) {
                val stats = str.toInt()
                status.visibility = View.VISIBLE
                when (stats) {
                    1 -> status.text = "Public "
                    else -> status.text = "Only me "
                }
            } else {
                status.visibility = View.GONE
            }
        }

        fun loadTotalLikes(str: String?) {
            if (str != null) {
                val num1 = str.toDouble().toInt()
                like_label.visibility = View.VISIBLE
                if (num1 > 0) {
                    countNumber(like_label, str, "Likes")
                } else {
                    like_label.text = "Like"
                }
            } else {
                like_label.visibility = View.GONE
            }
        }

        fun loadTotalComments(str: String?) {
            if (str != null) {
                val num2 = str.toDouble().toInt()
                comment_label.visibility = View.VISIBLE
                if (num2 > 1) {
                    countNumber(comment_label, str, "Comments")
                } else {
                    comment_label.text = "Comment"
                }
            } else {
                comment_label.visibility = View.GONE
            }
        }

        fun loadTotalShares(str: String?) {
            if (str != null) {
                val num3 = str.toDouble().toInt()
                share_label.visibility = View.VISIBLE
                if (num3 > 0) {
                    countNumber(share_label, str, "Shares")
                } else {
                    share_label.text = "Share"
                }
            } else {
                share_label.visibility = View.GONE
            }
        }

        fun currentTime(position: Double, textview: TextView) {
            val cal = Calendar.getInstance()
            var postTime = 0.0
            postTime = cal.timeInMillis - position
            if (postTime / 1000 < 60) {
                textview.text = "Just now"
            } else if (postTime / 1000 / 60 == 1.0) {
                textview.text = "a" + " minute ago"
            } else if (postTime / 1000 / 3600 < 1) {
                textview.text = (postTime / 1000 / 60).toLong().toString() + " minutes ago"
            } else if (postTime / 1000 / 3600 == 24.0) {
                textview.text = "an" + " hour ago"
            } else if (postTime / 1000 / 3600 < 24) {
                textview.text = (postTime / 1000 / 3600).toLong().toString() + " hours ago"
            } else if (postTime / 1000 / 3600 < 48) {
                textview.text = "a" + " day ago"
            } else {
                textview.text = (postTime / 1000 / 3600 / 24).toLong().toString() + " days ago"
            }
        }

        fun countNumber(textView: TextView, number: String, type: String) {
            if (TextUtils.isEmpty(type)) {
                val num = convertToInt(number)
                textView.text = num.toString()
                if (num < 1000) {
                    textView.text = num.toString()
                } else if (num < 10000) {
                    textView.text = formatNumber(num, 1000, "K", 3)
                } else if (num < 100000) {
                    textView.text = formatNumber(num, 1000, "K", 4)
                } else if (num < 1000000) {
                    textView.text = formatNumber(num, 1000, "K", 3)
                } else if (num < 10000000) {
                    textView.text = formatNumber(num, 1000000, "M", 3)
                } else if (num < 100000000) {
                    textView.text = formatNumber(num, 1000000, "M", 2)
                } else if (num < 1000000000) {
                    textView.text = formatNumber(num, 1000000, "M", 3)
                } else if (num < 10000000000L) {
                    textView.text = formatNumber(num, 1000000000, "B", 3)
                } else if (num < 100000000000L) {
                    textView.text = formatNumber(num, 1000000000, "B", 2)
                }
            } else if (!TextUtils.isEmpty(type)) {
                val num = convertToInt(number)
                textView.text = "$num $type"
                if (num < 1000) {
                    textView.text = "$num $type"
                } else if (num < 10000) {
                    textView.text = formatNumber(num, 1000, "K", 3) + " " + type
                } else if (num < 100000) {
                    textView.text = formatNumber(num, 1000, "K", 4) + " " + type
                } else if (num < 1000000) {
                    textView.text = formatNumber(num, 1000, "K", 3) + " " + type
                } else if (num < 10000000) {
                    textView.text = formatNumber(num, 1000000, "M", 3) + " " + type
                } else if (num < 100000000) {
                    textView.text = formatNumber(num, 1000000, "M", 2) + " " + type
                } else if (num < 1000000000) {
                    textView.text = formatNumber(num, 1000000, "M", 3) + " " + type
                } else if (num < 10000000000L) {
                    textView.text = formatNumber(num, 1000000000, "B", 3) + " " + type
                } else if (num < 100000000000L) {
                    textView.text = formatNumber(num, 1000000000, "B", 2) + " " + type
                }
            }
        }

        fun convertToInt(number: String): Int {
            return try {
                number.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }

        fun formatNumber(num: Int, divisor: Int, suffix: String, decimalPlaces: Int): String {
            var result = (num / divisor).toString()
            if (result.length > decimalPlaces) {
                result = result.substring(0, decimalPlaces)
            }
            return result + suffix
        }

        fun intentComment(push_key: String?, key: String?) {
            comment.setOnClickListener { view: View? ->
                removeString(
                    context,
                    POST_PUSH_KEY
                )
                saveString(
                    context,
                    POST_PUSH_KEY,
                    push_key
                )
                helper.intent(Comment::class.java,R.anim.right_in, R.anim.left_out)
            }
        }


        private fun textSpan(
            textView: TextView,
            contexts: String,
            username: String,
            fullname: String,
            profile: String,
            validate: Boolean
        ) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            val ssb = SpannableString(contexts)
            val pattern =
                Pattern.compile("(?<![^\\s])(([#]{1}|[https:]{6}|[http:]{5})([A-Za-z0-9_-]\\.?)+)(?![^\\s,])")
            val matcher = pattern.matcher(contexts)
            while (matcher.find()) {
                val span = ProfileSpan()
                ssb.setSpan(span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // patternFound = true;
            }
            if (textView.maxLines > 2) {
                val elipsizeString = EllipsizeString
                val ellipsizedString: SpannableString =
                    elipsizeString.ellipsizeString(ssb, 50, "view more")
                textView.text = ellipsizedString
            } else {
                textView.text = ssb
            }
            textView.setOnClickListener {
                if (!isClickableSpanClicked) {
                    // block.description(context, username, fullname, contexts, profile)
                }
                isClickableSpanClicked = false
            }
        }

        inner class ProfileSpan : ClickableSpan() {
            override fun onClick(view: View) {
                if (view is TextView) {
                    val sp = view.text as Spannable
                    val start = sp.getSpanStart(this)
                    val end = sp.getSpanEnd(this)
                    objectClicked = sp.subSequence(start, end).toString()
                    if (objectClicked!!.contains("#")) {
                        Log.i("Log", objectClicked!!)
                        // saveString(context, HASHTAG, objectClicked)
                        // intent(Hashtag::class.java, "", R.anim.right_in, R.anim.left_out)
                        isClickableSpanClicked = true
                    }
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = Color.parseColor("#2196F3")
                ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        }
    }
}