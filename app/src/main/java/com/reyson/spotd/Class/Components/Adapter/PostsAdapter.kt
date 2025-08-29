package com.reyson.spotd.Class.Components.Adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.reyson.spotd.Class.Components.Block.BlurredBitmap
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Model.PhotosData
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.net.URL
import java.util.Calendar

class PostsAdapter(private val context: Context, private val posts: ArrayList<Posts>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val profile: String? = null
    private val cal = Calendar.getInstance()
    private val i = Intent()
    private val viewPool = RecycledViewPool()
    private var retryCount = 0
    private val photosData: PhotosData
    private val helper: Helper = Helper(context)
    init {
        inflater = LayoutInflater.from(context)
        photosData = PhotosData.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = inflater.inflate(R.layout.custom_item_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        Log.i("post push key", post.push_key)
        val url = "${Access.URL_PULL_ZONE_POST}${post.uid}/${post.image}"
        val cen = "sfw"
        val count = post.total_count.toInt()
        if (count > 1) {
            holder.post_count.visibility = View.VISIBLE
        } else {
            holder.post_count.visibility = View.GONE
        }
        holder.image_view.setOnClickListener { v: View? ->
            photosData.uid = post.uid
            photosData.push_key = post.push_key
        }
        if (cen == "nsfw") {
            holder.censored_linear.visibility = View.VISIBLE
            // val thread = Thread {
            //     try {
            //         val urls = "${Access.URL_PULL_ZONE_POST}${post.uid}/${post.image}"
            //         val url = URL(urls)
            //         val myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            //
            //         // Apply the blur effect to the Bitmap
            //         val bl = BlurredBitmap()
            //         val a = bl.fastBlur(myBitmap, 1.0f, 75)
            //
            //         // Update the ImageView on the UI thread
            //         holder.image_view.post {
            //             holder.image_view.setImageBitmap(a)
            //             // holder.srfpl_post_blur.setImageBitmap(a);
            //             // holder.srfpl_post_blur.setVisibility(View.GONE);
            //             // Now that the image is set, you can make the views visible
            //             // holder.srfpl_card.setVisibility(View.VISIBLE);
            //             holder.image_progress.visibility = View.GONE
            //         }
            //     } catch (e: Exception) {
            //         e.printStackTrace()
            //     }
            // }
            // thread.start()
            helper.loadBlur("${post.uid}/${post.push_key}/${post.image}",holder.image_view,holder.card_view,holder.image_progress)
        } else {
            holder.censored_linear.visibility = View.GONE
            val xx = ContextCompat.getDrawable(context, R.drawable.img_logo)
            xx?.let {
                helper.loadImage("${post.uid}/${post.push_key}/${post.image}",holder.image_view,
                    it, holder.image_progress)
            }
            // Picasso.get()
            //     .load(url)
            //     .networkPolicy(NetworkPolicy.NO_CACHE)
            //     .into(object : Target {
            //         override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
            //             // Image loaded successfully, you can do something here
            //             holder.image_view.setImageBitmap(bitmap)
            //             holder.image_progress.visibility = View.GONE
            //             holder.image_view.visibility = View.VISIBLE // Show the image view
            //             retryCount = 0 // Reset the retry count on successful load
            //         }
            //
            //         override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
            //             // Image loading failed, handle the error here
            //             if (retryCount < MAX_RETRY_COUNT) {
            //                 // Retry loading the image
            //                 retryCount++
            //                 Picasso.get()
            //                     .load(url)
            //                     .networkPolicy(NetworkPolicy.NO_CACHE)
            //                     .into(this) // 'this' refers to the current Target
            //             } else {
            //                 // Maximum retries reached, show an error image
            //                 holder.image_view.setImageResource(R.drawable.img_logo) // Set your error image resource
            //                 holder.image_progress.visibility = View.GONE
            //                 holder.image_view.visibility = View.VISIBLE // Show the image view
            //                 retryCount = 0 // Reset the retry count
            //             }
            //         }
            //
            //         override fun onPrepareLoad(placeHolderDrawable: Drawable) {
            //             // Image is being prepared to load, you can show a placeholder here
            //             holder.image_view.visibility = View.GONE
            //             holder.image_progress.visibility = View.VISIBLE
            //         }
            //     })
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image_view: ImageView
        var image_progress: ProgressBar
        var card_view: CardView
        var linear_test: ImageView? = null
        var censored_linear: LinearLayout
        var post_count: LinearLayout

        init {
            image_view = v.findViewById(R.id.image_view)
            card_view = v.findViewById(R.id.card_view)
            image_progress = v.findViewById(R.id.image_progress)
            censored_linear = v.findViewById(R.id.srfl_censored_linear)
            post_count = v.findViewById(R.id.post_count)
            // linear_test = v.findViewById(R.id.image_view1);
        }

    }

    companion object {
        private const val MAX_RETRY_COUNT = 3 // Maximum number of retry attempts
    }
}