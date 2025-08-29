package com.reyson.spotd.Class.Components.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
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
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.reyson.spotd.Class.Components.Block.BlurredBitmap
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Model.PhotosData
import com.reyson.spotd.Data.Model.SubImage
import com.reyson.spotd.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.net.URL

class ViewPostChildAdapter( // Add a Context field
    private val context: Context, private val subLists: ArrayList<SubImage>
) : RecyclerView.Adapter<ViewPostChildAdapter.ViewHolder>() {
    private var mPath: String? = null
    private var mKey: String? = null
    private val inflater: LayoutInflater
    private var retryCount = 0
    private val photosData: PhotosData

    init {
        inflater = LayoutInflater.from(context)
        photosData = PhotosData.getInstance()
    }

    fun setPath(mPath: String?) {
        this.mPath = mPath
    }

    fun setPushKey(mKey: String?) {
        this.mKey = mKey
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = inflater.inflate(R.layout.custom_frag_post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subList = subLists[position]
        Log.i("image name", subList.image)
        val urls: String = Access.URL_PULL_ZONE_POST + mPath + "/" + subList.image
        holder.loadImageWithBackgroundColor(urls, holder.srfpl_card)
        val cen = "sfw"
        holder.srfpl_post.setOnClickListener { v: View? ->
            Log.i("status of pushkey", mKey!!)
            photosData.uid = mPath
            photosData.push_key = mKey
        }
        if (cen == "nsfw") {
            holder.srfl_censored_linear.visibility = View.VISIBLE
            val thread = Thread {
                try {
                    val urls: String = Access.URL_PULL_ZONE_POST + mPath + "/" + subList.image
                    val url = URL(urls)
                    val myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                    // Apply the blur effect to the Bitmap
                    val bl = BlurredBitmap()
                    val a = bl.fastBlur(myBitmap, 1.0f, 75)

                    // Update the ImageView on the UI thread
                    holder.srfpl_post.post {
                        holder.srfpl_post.setImageBitmap(a)
                        // holder.srfpl_post_blur.setImageBitmap(a);
                        // holder.srfpl_post_blur.setVisibility(View.GONE);
                        // Now that the image is set, you can make the views visible
                        holder.srfpl_card.visibility = View.VISIBLE
                        holder.srfpl_progress.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        } else {
            holder.srfl_censored_linear.visibility = View.GONE
            Picasso.get()
                .load(urls)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                        // Image loaded successfully, you can do something here
                        holder.srfpl_post.setImageBitmap(bitmap)
                        holder.srfpl_card.visibility = View.VISIBLE
                        holder.srfpl_progress.visibility = View.GONE
                        // holder.srfpl_post.setImageBitmap(bitmap);
                        // holder.srfpl_progress.setVisibility(View.GONE);
                        // holder.srfpl_post.setVisibility(View.VISIBLE); // Show the image view
                        retryCount = 0 // Reset the retry count on successful load
                    }

                    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
                        // Image loading failed, handle the error here
                        if (retryCount < MAX_RETRY_COUNT) {
                            // Retry loading the image
                            retryCount++
                            Picasso.get()
                                .load(urls)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .into(this) // 'this' refers to the current Target
                        } else {
                            // Maximum retries reached, show an error image
                            holder.srfpl_post.setImageResource(R.drawable.img_logo) // Set your error image resource
                            holder.srfpl_progress.visibility = View.GONE
                            holder.srfpl_card.visibility = View.VISIBLE // Show the image view
                            retryCount = 0 // Reset the retry count
                        }
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                        // Image is being prepared to load, you can show a placeholder here
                        holder.srfpl_card.visibility = View.GONE
                        holder.srfpl_progress.visibility = View.VISIBLE
                    }
                })
        }
    }

    private fun convertViewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun getItemCount(): Int {
        return subLists.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var srfpl_card: CardView
        var srfpl_label_like: TextView
        var srfpl_label_comment: TextView
        var srfpl_label_share: TextView
        var srfpl_label_context: TextView
        var srfpl_label_time: TextView
        var srfpl_label_views: TextView
        var srfpl_linear_post: LinearLayout
        var srfpl_linear_like: LinearLayout
        var srfpl_linear_comment: LinearLayout
        var srfpl_linear_share: LinearLayout
        var srfpl_linear_more: LinearLayout
        var srfpl_mai_container: LinearLayout
        var srfl_censored_linear: LinearLayout
        var srfpl_progress: ProgressBar
        var linear3: LinearLayout
        var srfpl_post: ImageView
        var srfpl_likes: ImageView
        var srfpl_comment: ImageView
        var srfpl_share: ImageView

        // ImageView srfpl_post_blur;
        var srfpl_shimmer0: ShimmerFrameLayout
        var srfpl_shimmer1: ShimmerFrameLayout
        var srfpl_shimmer2: ShimmerFrameLayout
        var srfpl_shimmer3: ShimmerFrameLayout
        var srfpl_shimmer4: ShimmerFrameLayout
        var srfpl_shimmer5: ShimmerFrameLayout
        var srfpl_shimmer6: ShimmerFrameLayout
        var srfpl_childRecyclerView: RecyclerView? = null

        init {
            srfpl_card = v.findViewById(R.id.srfpl_card)
            srfpl_linear_post = v.findViewById(R.id.srfpl_linear_post)
            srfpl_likes = v.findViewById(R.id.srfpl_likes)
            srfpl_comment = v.findViewById(R.id.srfpl_comment)
            srfpl_share = v.findViewById(R.id.srfpl_share)
            srfpl_linear_like = v.findViewById(R.id.srfpl_linear_like)
            srfpl_linear_comment = v.findViewById(R.id.srfpl_linear_comment)
            srfpl_linear_share = v.findViewById(R.id.srfpl_linear_share)
            srfpl_linear_more = v.findViewById(R.id.srfpl_linear_more)
            srfpl_label_like = v.findViewById(R.id.srfpl_label_like)
            srfpl_label_comment = v.findViewById(R.id.srfpl_label_comment)
            srfpl_label_share = v.findViewById(R.id.srfpl_label_share)
            srfpl_label_context = v.findViewById(R.id.srfpl_label_context)
            srfpl_label_time = v.findViewById(R.id.srfpl_label_time)
            srfpl_label_views = v.findViewById(R.id.srfpl_label_views)
            srfpl_mai_container = v.findViewById(R.id.srfpl_mai_container)
            srfl_censored_linear = v.findViewById(R.id.srfl_censored_linear)

            // srfpl_post_blur = v.findViewById(R.id.srfpl_post_blur);
            linear3 = v.findViewById(R.id.srfpl_transy)
            srfpl_post = v.findViewById(R.id.srfpl_post)
            // srfpl_childRecyclerView = v.findViewById(R.id.srfpl_childRecyclerView);
            srfpl_shimmer0 = v.findViewById(R.id.srfpl_shimmer0)
            srfpl_shimmer1 = v.findViewById(R.id.srfpl_shimmer1)
            srfpl_shimmer2 = v.findViewById(R.id.srfpl_shimmer2)
            srfpl_shimmer3 = v.findViewById(R.id.srfpl_shimmer3)
            srfpl_shimmer4 = v.findViewById(R.id.srfpl_shimmer4)
            srfpl_shimmer5 = v.findViewById(R.id.srfpl_shimmer5)
            srfpl_shimmer6 = v.findViewById(R.id.srfpl_shimmer6)
            srfpl_progress = v.findViewById(R.id.srfpl_progress)
        }

        fun loadImageWithBackgroundColor(url: String?, cardView: CardView) {
            try {
                Picasso.get().load(url).into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                        // Use Palette to extract the dominant color from the bitmap
                        Palette.from(bitmap)
                            .generate { palette -> // Get the dominant color from the Palette
                                val bgColor = palette!!.getDominantColor(
                                    ContextCompat.getColor(
                                        context, R.color.charlestonGreen
                                    )
                                )
                                val newBgColor = Color.argb(
                                    (255 * 1),
                                    Color.red(bgColor),
                                    Color.green(bgColor),
                                    Color.blue(bgColor)
                                )

                                // Set the background color of the CardView
                                cardView.setCardBackgroundColor(newBgColor)
                            }
                    }

                    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
                        // Handle failed loading
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                        // You can perform any preparations here if needed
                    }
                })
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        fun intent(
            targetActivityClass: Class<*>?,
            enterAnimationResId: Int,
            exitAnimationResId: Int
        ) {
            val i = Intent(context, targetActivityClass)
            i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            val options = ActivityOptions.makeCustomAnimation(
                context,
                enterAnimationResId,
                exitAnimationResId
            )
            context.startActivity(i, options.toBundle())
        }
    }

    companion object {
        private const val MAX_RETRY_COUNT = 3 // Maximum number of retry attempts
    }
}