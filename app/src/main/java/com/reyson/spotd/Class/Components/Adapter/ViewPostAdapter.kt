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
import com.facebook.shimmer.ShimmerFrameLayout
import com.reyson.spotd.Class.Activity.Hashtag.Hashtag
import com.reyson.spotd.Class.Activity.Posts.Comment.Comment
import com.reyson.spotd.Class.Activity.Profile.Profile
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.HighlightedTextView
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY.Companion.POST_PUSH_KEY
import com.reyson.spotd.Data.Model.PostData
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.removeString
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.Calendar
import java.util.regex.Pattern

class ViewPostAdapter(private val context: Context, posts: ArrayList<Posts>) :
    RecyclerView.Adapter<ViewPostAdapter.ViewHolder>() {
    private val posts: ArrayList<Posts> = posts
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var profile: String? = null
    private var cal = Calendar.getInstance()
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    private val highlightedTextView: HighlightedTextView = HighlightedTextView(context)
    private var isClickableSpanClicked = false
    private var objectClicked = ""
    private val postData: PostData = PostData.getInstance()
    private var helper : Helper = Helper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = inflater.inflate(R.layout.activity_post_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Posts = posts[position]
        holder.newFeedInit()
        if (post != null) {
            holder.loadUsername(post.username)
            holder.loadFullname(post.full_name)
            holder.loadProfile(post.profile_picture)
            holder.intentProfile(post.uid)
            highlightedTextView.setTextWithHighlights(
                holder.ffy_context,
                post.caption,
                50,
                true
            )
            highlightedTextView.setDescription(
                holder.ffy_context,
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
            holder.intentComment(post.push_key)
            // holder.functionLikes(post.push_key, post.getTotal_likes(), post.getIsLiked())
            // TODO fix and implements some features in newfeed
            // more function such as report and many more in more functions
            val childAdapter = ViewPostChildAdapter(context, post.images)
            childAdapter.setPath(post.uid)
            childAdapter.setPushKey(post.push_key)
            holder.ffy_childRecyclerView.adapter = childAdapter
            val layoutManager = LinearLayoutManager(
                holder.ffy_childRecyclerView.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            holder.ffy_childRecyclerView.layoutManager = layoutManager
            holder.ffy_childRecyclerView.setRecycledViewPool(viewPool)
            holder.ffy_transition.visibility = View.VISIBLE
            holder.ffy_shimmer_layout.visibility = View.GONE
            holder.ffy_status.visibility = View.GONE
            holder.ffy_shimmer_layout.stopShimmer()
        } else {
            holder.ffy_shimmer_layout.visibility = View.VISIBLE
            holder.ffy_transition.visibility = View.GONE
        }
    }

    // override fun getItemCount(): Int {
    //     return posts.size
    // }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ffy_childRecyclerView: RecyclerView
        var ffy_username: TextView
        var ffy_full_name: TextView
        var ffy_profile: ImageView
        var ffy_context: TextView
        var ffy_status: TextView
        var ffy_time: TextView
        var ffy_like_label: TextView
        var ffy_comment_label: TextView
        var ffy_share_label: TextView
        var ffy_like: ImageView
        var ffy_comment: ImageView
        var ffy_share: ImageView
        var ffy_more: ImageView
        var ffy_shimmer_layout: ShimmerFrameLayout
        var ffy_transition: LinearLayout
        var ffy_like_layout: LinearLayout
        var ffy_comment_layout: LinearLayout
        var ffy_shared_layout: LinearLayout

        init {
            ffy_shimmer_layout = v.findViewById(R.id.ffy_shimmer_layout)
            ffy_transition = v.findViewById(R.id.ffy_transition)
            ffy_username = v.findViewById(R.id.ffy_username)
            ffy_full_name = v.findViewById(R.id.ffy_full_name)
            ffy_profile = v.findViewById(R.id.ffy_profile)
            ffy_context = v.findViewById(R.id.ffy_context)
            ffy_time = v.findViewById(R.id.ffy_time)
            ffy_status = v.findViewById(R.id.ffy_status)
            ffy_like = v.findViewById(R.id.ffy_like)
            ffy_like_label = v.findViewById(R.id.ffy_like_label)
            ffy_comment = v.findViewById(R.id.ffy_comment)
            ffy_comment_label = v.findViewById(R.id.ffy_comment_label)
            ffy_share = v.findViewById(R.id.ffy_share)
            ffy_share_label = v.findViewById(R.id.ffy_share_label)
            ffy_more = v.findViewById(R.id.ffy_more)
            ffy_like_layout = v.findViewById(R.id.ffy_like_layout)
            ffy_comment_layout = v.findViewById(R.id.ffy_comment_layout)
            ffy_shared_layout = v.findViewById(R.id.ffy_shared_layout)
            ffy_childRecyclerView = v.findViewById(R.id.ffy_recyclerview)
        }

        fun newFeedInit() {
            ffy_shared_layout.visibility = View.GONE
        }

        fun loadUsername(str: String?) {
            if (str != null) {
                ffy_username.text = str
                ffy_username.visibility = View.VISIBLE
            } else {
                ffy_username.visibility = View.GONE
            }
        }

        fun loadFullname(str: String?) {
            if (str != null) {
                ffy_full_name.text = str
                ffy_full_name.visibility = View.VISIBLE
            } else {
                ffy_full_name.visibility = View.GONE
            }
        }

        fun intentProfile(uid: String?) {
            ffy_profile.setOnClickListener { v: View? ->
                postData.uid = uid
                // intent(Profile::class.java, R.anim.right_in, R.anim.left_out)
            }
        }

        fun loadProfile(str: String?) {
            if (str != null) {
                ffy_profile.visibility = View.VISIBLE
                profile = Access.URL_PULL_ZONE_PROFILE + str
                Picasso.get()
                    .load(profile)
                    .transform(CropCircleTransformation())
                    .placeholder(R.drawable.img_logo)
                    .into(ffy_profile)
            } else {
                Picasso.get()
                    .load(R.drawable.img_logo)
                    .transform(CropCircleTransformation())
                    .placeholder(R.drawable.img_logo)
                    .into(ffy_profile)
            }
        }

        fun loadMS(str: String?) {
            if (str != null) {
                currentTime(str.toDouble(), ffy_time)
                ffy_time.visibility = View.VISIBLE
            } else {
                ffy_time.visibility = View.GONE
            }
        }

        fun loadStatus(str: String?) {
            if (str != null) {
                val stats = str.toInt()
                ffy_status.visibility = View.VISIBLE
                when (stats) {
                    1 -> ffy_status.text = "Public "
                    else -> ffy_status.text = "Only me "
                }
            } else {
                ffy_status.visibility = View.GONE
            }
        }

        fun loadTotalLikes(str: String?) {
            if (str != null) {
                val num1 = str.toDouble().toInt()
                ffy_like_label.visibility = View.VISIBLE
                if (num1 > 0) {
                    countNumber(ffy_like_label, str, "Likes")
                } else {
                    ffy_like_label.text = "Like"
                }
            } else {
                ffy_like_label.visibility = View.GONE
            }
        }

        fun loadTotalComments(str: String?) {
            if (str != null) {
                val num2 = str.toDouble().toInt()
                ffy_comment_label.visibility = View.VISIBLE
                if (num2 > 1) {
                    countNumber(ffy_comment_label, str, "Comments")
                } else {
                    ffy_comment_label.text = "Comment"
                }
            } else {
                ffy_comment_label.visibility = View.GONE
            }
        }

        fun loadTotalShares(str: String?) {
            if (str != null) {
                val num3 = str.toDouble().toInt()
                ffy_share_label.visibility = View.VISIBLE
                if (num3 > 0) {
                    countNumber(ffy_share_label, str, "Shares")
                } else {
                    ffy_share_label.text = "Share"
                }
            } else {
                ffy_share_label.visibility = View.GONE
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

        fun intentComment(push_key: String?) {
            ffy_comment.setOnClickListener { _ : View? ->
                removeString(context, POST_PUSH_KEY)
                saveString(context, POST_PUSH_KEY, push_key)
                // intent(Comment::class.java, R.anim.right_in, R.anim.left_out)
            }
        }
        //
        // fun functionLikes(push_key: String?, total_likes: String?, is_like: String) {
        //     try {
        //         val isLiked =
        //             booleanArrayOf(false) // Use an array to keep the reference to a mutable boolean
        //         val currentLikes = intArrayOf(total_likes!!.toInt())
        //         val stats = is_like.toInt()
        //         when (stats) {
        //             1 -> {
        //                 ffy_like.setColorFilter(ContextCompat.getColor(context, R.color.red))
        //                 isLiked[0] = true
        //             }
        //
        //             else -> {
        //                 ffy_like.setColorFilter(
        //                     ContextCompat.getColor(
        //                         context,
        //                         R.color.charlestonGreen
        //                     )
        //                 )
        //                 isLiked[0] = false
        //             }
        //         }
        //         if (total_likes != null) {
        //             val num1 = total_likes.toDouble().toInt()
        //             ffy_like_label.setVisibility(View.VISIBLE)
        //             if (num1 > 0) {
        //                 countNumber(ffy_like_label, total_likes, "Likes")
        //             } else {
        //                 ffy_like_label.setText("Like")
        //             }
        //         } else {
        //             ffy_like_label.setVisibility(View.GONE)
        //         }
        //         ffy_like.setOnClickListener { view: View? ->
        //             try {
        //                 cal = Calendar.getInstance()
        //                 val ms = cal.timeInMillis.toString()
        //                 if (isLiked[0]) {
        //                     val data = "uid=" + URLEncoder.encode(
        //                         loadString(
        //                             context, KEYs.UID, ""
        //                         ), "UTF-8"
        //                     ) +
        //                             "&push_key=" + URLEncoder.encode(push_key, "UTF-8")
        //                     object : AXON(context, URLs.URL_POST_UNLIKE, data) {
        //                         protected fun onPostExecute(result: String?) {
        //                             // Handle the result if needed
        //                             Log.i("result", result!!)
        //                         }
        //                     }.execute()
        //                     isLiked[0] =
        //                         false // Assuming post object has a method to set liked status
        //                     ffy_like.colorFilter = null
        //                     currentLikes[0]--
        //                     if (currentLikes[0] == 0) {
        //                         ffy_like_label.setText("Likes")
        //                     } else {
        //                         countNumber(ffy_like_label, currentLikes[0].toString(), "Likes")
        //                     }
        //                     Log.d("LikeStatus", "Post unliked.")
        //                 } else {
        //                     val data = "uid=" + URLEncoder.encode(
        //                         loadString(
        //                             context, KEYs.UID, ""
        //                         ), "UTF-8"
        //                     ) +
        //                             "&push_key=" + URLEncoder.encode(push_key, "UTF-8") +
        //                             "&stats=" + URLEncoder.encode(LIKE, "UTF-8") +
        //                             "&ms=" + URLEncoder.encode(ms, "UTF-8")
        //                     object : AXON(context, URLs.URL_POST_LIKE, data) {
        //                         protected fun onPostExecute(result: String?) {
        //                             // Handle the result if needed
        //                             Log.i("result", result!!)
        //                         }
        //                     }.execute()
        //                     isLiked[0] =
        //                         true // Assuming post object has a method to set liked status
        //                     ffy_like.setColorFilter(
        //                         ContextCompat.getColor(
        //                             context,
        //                             R.color.red_color
        //                         )
        //                     )
        //                     currentLikes[0]++
        //                     countNumber(ffy_like_label, currentLikes[0].toString(), "Likes")
        //                     Log.d("LikeStatus", "Post liked.")
        //                 }
        //             } catch (e: UnsupportedEncodingException) {
        //                 throw RuntimeException(e)
        //             }
        //         }
        //     } catch (e: Exception) {
        //     }
        // }

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
                val span: ProfileSpan = ProfileSpan()
                ssb.setSpan(span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                // patternFound = true;
            }
            if (textView.maxLines > 2) {
                val elipsizeString = com.reyson.spotd.Class.Components.Block.EllipsizeString
                val ellipsizedString: SpannableString =
                    elipsizeString.ellipsizeString(ssb, 50, "view more")
                textView.text = ellipsizedString
            } else {
                textView.text = ssb
            }
            textView.setOnClickListener(View.OnClickListener {
                if (!isClickableSpanClicked) {
                    helper.description(context, username, fullname, contexts, profile)
                }
                isClickableSpanClicked = false
            })
        }

        private inner class ProfileSpan : ClickableSpan() {
            override fun onClick(view: View) {
                if (view is TextView) {
                    val tv: TextView = view
                    val sp: Spannable = tv.text as Spannable
                    val start: Int = sp.getSpanStart(this)
                    val end: Int = sp.getSpanEnd(this)
                    objectClicked = sp.subSequence(start, end).toString()
                    if (objectClicked.contains("#")) {
                        Log.i("Log", objectClicked)
                        // saveString(context, HASHTAG, objectClicked)
                        // intent(Hashtag::class.java, R.anim.right_in, R.anim.left_out)
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