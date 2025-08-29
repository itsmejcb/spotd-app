package com.reyson.spotd.Class.Components.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.reyson.spotd.Class.Activity.Posts.Image.Image
import com.reyson.spotd.Class.Components.Block.BlurredBitmap
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.SubImage
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.net.URL

class ForYouChildAdapter(
    private val context: Context, subLists: ArrayList<SubImage>
) : RecyclerView.Adapter<ForYouChildAdapter.ViewHolder>() {
    private val subLists: ArrayList<SubImage> = subLists
    private var mPath: String? = null
    private var mKey: String? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val helper: Helper = Helper(context)
    private var retryCount = 0
    private val MAX_RETRY_COUNT = 3 // Maximum number of retry attempts

    fun setPath(mPath: String?) {
        this.mPath = mPath
    }

    fun setPushKey(mKey: String?) {
        this.mKey = mKey
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = inflater.inflate(R.layout.custom_frag_post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subList: SubImage = subLists[position]
        val cen = "sfw"
        if (cen == "nsfw") {
            holder.srfl_censored_linear.visibility = View.VISIBLE
            val urls: String =
                Access.URL_PULL_ZONE_POST + mPath + "/" + mKey + "/" + subList.image
            helper.loadBlur("$mPath/$mKey/${subList.image}",holder.srfpl_post,holder.srfpl_card,holder.srfpl_progress)
        } else {
            holder.srfl_censored_linear.visibility = View.GONE
            val xx =ContextCompat.getDrawable(context, R.drawable.img_logo)
            xx?.let { helper.loadImage("$mPath/$mKey/${subList.image}",holder.srfpl_post, it,holder.srfpl_progress ) }
        }
        holder.srfpl_post?.setOnClickListener {
            saveString(context, KEY.POST_PUSH_KEY,mPath)
            saveString(context, KEY.USER_UID,mPath)
            helper.intent(Image::class.java,R.anim.right_in,R.anim.left_out)
        }
    }

    override fun getItemCount(): Int {
        return subLists.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var srfpl_card: CardView = v.findViewById(R.id.srfpl_card)
        var srfpl_label_like: TextView = v.findViewById(R.id.srfpl_label_like)
        var srfpl_label_comment: TextView = v.findViewById(R.id.srfpl_label_comment)
        var srfpl_label_share: TextView = v.findViewById(R.id.srfpl_label_share)
        var srfpl_label_context: TextView = v.findViewById(R.id.srfpl_label_context)
        var srfpl_label_time: TextView = v.findViewById(R.id.srfpl_label_time)
        var srfpl_label_views: TextView = v.findViewById(R.id.srfpl_label_views)
        var srfpl_linear_post: LinearLayout = v.findViewById(R.id.srfpl_linear_post)
        var srfpl_linear_like: LinearLayout = v.findViewById(R.id.srfpl_linear_like)
        var srfpl_linear_comment: LinearLayout = v.findViewById(R.id.srfpl_linear_comment)
        var srfpl_linear_share: LinearLayout = v.findViewById(R.id.srfpl_linear_share)
        var srfpl_linear_more: LinearLayout = v.findViewById(R.id.srfpl_linear_more)
        var srfpl_mai_container: LinearLayout = v.findViewById(R.id.srfpl_mai_container)
        var srfl_censored_linear: LinearLayout = v.findViewById(R.id.srfl_censored_linear)
        var srfpl_progress: ProgressBar = v.findViewById(R.id.srfpl_progress)
        var linear3: LinearLayout = v.findViewById(R.id.srfpl_transy)
        var srfpl_post: ImageView = v.findViewById(R.id.srfpl_post)
        var srfpl_likes: ImageView = v.findViewById(R.id.srfpl_likes)
        var srfpl_comment: ImageView = v.findViewById(R.id.srfpl_comment)
        var srfpl_share: ImageView = v.findViewById(R.id.srfpl_share)
        var srfpl_shimmer0: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer0)
        var srfpl_shimmer1: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer1)
        var srfpl_shimmer2: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer2)
        var srfpl_shimmer3: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer3)
        var srfpl_shimmer4: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer4)
        var srfpl_shimmer5: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer5)
        var srfpl_shimmer6: ShimmerFrameLayout = v.findViewById(R.id.srfpl_shimmer6)
        var srfpl_childRecyclerView: RecyclerView? = null
    }
}

// package com.reyson.spotd.Class.Components.Adapter
//
// import android.content.Context
// import android.graphics.Bitmap
// import android.graphics.BitmapFactory
// import android.graphics.Canvas
// import android.graphics.drawable.Drawable
// import android.util.Log
// import android.view.LayoutInflater
// import android.view.View
// import android.view.ViewGroup
// import android.widget.ImageView
// import android.widget.LinearLayout
// import android.widget.ProgressBar
// import android.widget.TextView
// import androidx.cardview.widget.CardView
// import androidx.recyclerview.widget.RecyclerView
// import com.facebook.shimmer.ShimmerFrameLayout
// import com.reyson.spotd.Class.Components.Block.BlurredBitmap
// import com.reyson.spotd.Class.Components.Block.Helper
// import com.reyson.spotd.Data.Api.Access
// import com.reyson.spotd.Data.Model.SubImage
// import com.reyson.spotd.R
// import com.squareup.picasso.NetworkPolicy
// import com.squareup.picasso.Picasso
// import com.squareup.picasso.Picasso.LoadedFrom
// import com.squareup.picasso.Target
// import java.net.URL
//
// class ForYouChildAdapter(
//     private val context: Context, subLists: ArrayList<SubImage>
// ) : RecyclerView.Adapter<ForYouChildAdapter.ViewHolder?>() {
//     private val subLists: ArrayList<SubImage>
//     private var mPath: String? = null
//     private var mKey: String? = null
//     private val inflater: LayoutInflater = LayoutInflater.from(context)
//     private val helper: Helper = Helper(context)
//     private var retryCount = 0
//     private val MAX_RETRY_COUNT = 3 // Maximum number of retry attempts
//
//     init {
//         this.subLists = subLists
//     }
//
//     fun setPath(mPath: String?) {
//         this.mPath = mPath
//     }
//
//     fun setPushKey(mKey: String?) {
//         this.mKey = mKey
//     }
//
//     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//         val v: View = inflater.inflate(R.layout.custom_frag_post_layout, parent, false)
//         return ViewHolder(v)
//     }
//
//
//     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//         val subList: SubImage = subLists[position]
//         Log.i("image name", subList.image)
//         Log.i("status of pushkey", mKey!!)
//         val urls: String = Access.URL_PULL_ZONE_POST + mPath + "/" + mKey + "/" + subList.image
//         // holder.loadImageWithBackgroundColor(urls, holder.srfpl_card)
//         val cen = "sfw"
//         Log.i("status of pushkey", urls!!)
//         if (cen == "nsfw") {
//             holder.srfl_censored_linear.visibility = View.VISIBLE
//             val thread = Thread {
//                 try {
//                     val urls: String = Access.URL_PULL_ZONE_POST + mPath + "/" + mKey + "/" + subList.image
//                     val url = URL(urls)
//                     val myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//
//                     // Apply the blur effect to the Bitmap
//                     val bl = BlurredBitmap()
//                     val a = bl.fastBlur(myBitmap, 1.0f, 75)
//
//                     // Update the ImageView on the UI thread
//                     holder.srfpl_post.post {
//                         holder.srfpl_post.setImageBitmap(a)
//                         // holder.srfpl_post_blur.setImageBitmap(a);
//                         // holder.srfpl_post_blur.setVisibility(View.GONE);
//                         // Now that the image is set, you can make the views visible
//                         holder.srfpl_card.visibility = View.VISIBLE
//                         holder.srfpl_progress.visibility = View.GONE
//                     }
//                 } catch (e: Exception) {
//                     e.printStackTrace()
//                 }
//             }
//             thread.start()
//         } else {
//             holder.srfl_censored_linear.visibility = View.GONE
//             Picasso.get()
//                 .load(urls)
//                 .networkPolicy(NetworkPolicy.NO_CACHE)
//                 .into(object : Target {
//                     override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
//                         // Image loaded successfully, you can do something here
//                         holder.srfpl_post.setImageBitmap(bitmap)
//                         holder.srfpl_card.visibility = View.VISIBLE
//                         holder.srfpl_progress.visibility = View.GONE
//                         // holder.srfpl_post.setImageBitmap(bitmap);
//                         // holder.srfpl_progress.setVisibility(View.GONE);
//                         // holder.srfpl_post.setVisibility(View.VISIBLE); // Show the image view
//                         retryCount = 0 // Reset the retry count on successful load
//                     }
//
//                     override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
//                         // Image loading failed, handle the error here
//                         if (retryCount < MAX_RETRY_COUNT) {
//                             // Retry loading the image
//                             retryCount++
//                             Picasso.get()
//                                 .load(urls)
//                                 .networkPolicy(NetworkPolicy.NO_CACHE)
//                                 .into(this) // 'this' refers to the current Target
//                         } else {
//                             // Maximum retries reached, show an error image
//                             holder.srfpl_post.setImageResource(R.drawable.img_logo) // Set your error image resource
//                             holder.srfpl_progress.visibility = View.GONE
//                             holder.srfpl_card.visibility = View.VISIBLE // Show the image view
//                             retryCount = 0 // Reset the retry count
//                         }
//                     }
//
//                     override fun onPrepareLoad(placeHolderDrawable: Drawable) {
//                         // Image is being prepared to load, you can show a placeholder here
//                         holder.srfpl_card.visibility = View.GONE
//                         holder.srfpl_progress.visibility = View.VISIBLE
//                     }
//                 })
//         }
//     }
//
//     private fun convertViewToBitmap(view: View): Bitmap {
//         val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//         val canvas = Canvas(bitmap)
//         view.draw(canvas)
//         return bitmap
//     }
//
//     override fun getItemCount(): Int {
//         return subLists.size
//
//     }
//
//     inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//         var srfpl_card: CardView
//         var srfpl_label_like: TextView
//         var srfpl_label_comment: TextView
//         var srfpl_label_share: TextView
//         var srfpl_label_context: TextView
//         var srfpl_label_time: TextView
//         var srfpl_label_views: TextView
//         var srfpl_linear_post: LinearLayout
//         var srfpl_linear_like: LinearLayout
//         var srfpl_linear_comment: LinearLayout
//         var srfpl_linear_share: LinearLayout
//         var srfpl_linear_more: LinearLayout
//         var srfpl_mai_container: LinearLayout
//         var srfl_censored_linear: LinearLayout
//         var srfpl_progress: ProgressBar
//         var linear3: LinearLayout
//         var srfpl_post: ImageView
//         var srfpl_likes: ImageView
//         var srfpl_comment: ImageView
//         var srfpl_share: ImageView
//
//         // ImageView srfpl_post_blur;
//         var srfpl_shimmer0: ShimmerFrameLayout
//         var srfpl_shimmer1: ShimmerFrameLayout
//         var srfpl_shimmer2: ShimmerFrameLayout
//         var srfpl_shimmer3: ShimmerFrameLayout
//         var srfpl_shimmer4: ShimmerFrameLayout
//         var srfpl_shimmer5: ShimmerFrameLayout
//         var srfpl_shimmer6: ShimmerFrameLayout
//         var srfpl_childRecyclerView: RecyclerView? = null
//
//         init {
//             srfpl_card = v.findViewById(R.id.srfpl_card)
//             srfpl_linear_post = v.findViewById(R.id.srfpl_linear_post)
//             srfpl_likes = v.findViewById(R.id.srfpl_likes)
//             srfpl_comment = v.findViewById(R.id.srfpl_comment)
//             srfpl_share = v.findViewById(R.id.srfpl_share)
//             srfpl_linear_like = v.findViewById(R.id.srfpl_linear_like)
//             srfpl_linear_comment = v.findViewById(R.id.srfpl_linear_comment)
//             srfpl_linear_share = v.findViewById(R.id.srfpl_linear_share)
//             srfpl_linear_more = v.findViewById(R.id.srfpl_linear_more)
//             srfpl_label_like = v.findViewById(R.id.srfpl_label_like)
//             srfpl_label_comment = v.findViewById(R.id.srfpl_label_comment)
//             srfpl_label_share = v.findViewById(R.id.srfpl_label_share)
//             srfpl_label_context = v.findViewById(R.id.srfpl_label_context)
//             srfpl_label_time = v.findViewById(R.id.srfpl_label_time)
//             srfpl_label_views = v.findViewById(R.id.srfpl_label_views)
//             srfpl_mai_container = v.findViewById(R.id.srfpl_mai_container)
//             srfl_censored_linear = v.findViewById(R.id.srfl_censored_linear)
//
//             // srfpl_post_blur = v.findViewById(R.id.srfpl_post_blur);
//             linear3 = v.findViewById(R.id.srfpl_transy)
//             srfpl_post = v.findViewById(R.id.srfpl_post)
//             srfpl_shimmer0 = v.findViewById(R.id.srfpl_shimmer0)
//             srfpl_shimmer1 = v.findViewById(R.id.srfpl_shimmer1)
//             srfpl_shimmer2 = v.findViewById(R.id.srfpl_shimmer2)
//             srfpl_shimmer3 = v.findViewById(R.id.srfpl_shimmer3)
//             srfpl_shimmer4 = v.findViewById(R.id.srfpl_shimmer4)
//             srfpl_shimmer5 = v.findViewById(R.id.srfpl_shimmer5)
//             srfpl_shimmer6 = v.findViewById(R.id.srfpl_shimmer6)
//             srfpl_progress = v.findViewById(R.id.srfpl_progress)
//         }
//     }
//
//     companion object {
//         private const val MAX_RETRY_COUNT = 3 // Maximum number of retry attempts
//     }
// }
//
//
//
