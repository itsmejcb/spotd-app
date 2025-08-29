package com.reyson.spotd.Class.Activity.Posts.Image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.reyson.spotd.Class.Activity.Posts.Image.Model.Request
import com.reyson.spotd.Class.Activity.Posts.Image.Model.Response
import com.reyson.spotd.Class.Components.Adapter.ViewPostAdapter
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class Image : AppCompatActivity(), Interface.View {
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private var spv_recyclerview: RecyclerView? = null
    private var spv_name: TextView? = null
    private var spv_username: TextView? = null
    private var spv_profile: ImageView? = null
    private var spv_progressBar: ProgressBar? = null
    private var spv_load_componenets: LinearLayout? = null
    private var retryCount = 0
    private val MAX_RETRY_COUNT = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)
        initialize(savedInstanceState)
        initViewModel()
        initializeLogic()
        val request = Request()
        request.apply {
            uid = "10516012124013980"
            // uid = loadString(this@Image,KEY.UID,"")
            push_key = "739253070419620042"
        }
        viewModel.userFetchPost(request)
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getFetchPostObserver().observe(this@Image, Observer<Response?> { response ->
            response?.posts?.let { posts ->
                Log.i("resultsss", posts.toString())
                Log.i("resultsss", posts.toString())
                // val processedPosts: ArrayList<Posts> = posts
                // LoadPost(processedPosts)
                // Load the ArrayList<Posts> into the RecyclerView adapter here
                // Example: adapter.submitList(posts)
            }
        })
    }
    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        spv_recyclerview = findViewById(R.id.spv_recyclerview)
        spv_name = findViewById(R.id.spv_name)
        spv_username = findViewById(R.id.spv_username)
        spv_progressBar = findViewById(R.id.spv_progressBar)
        spv_load_componenets = findViewById(R.id.spv_load_componenets)
        spv_profile = findViewById(R.id.spv_profile)

    }
    fun loadData(username: String?, name: String?, profile: String) {
        if (!TextUtils.isEmpty(username)) {
            spv_username!!.text = username
        }
        if (!TextUtils.isEmpty(name)) {
            spv_name!!.text = name
        }
        if (!TextUtils.isEmpty(profile)) {
            val xx = ContextCompat.getDrawable(this, R.drawable.img_logo)
            xx?.let { helper.loadProfile(profile,spv_profile!!, it) }
        } else {
            Picasso.get()
                .load(R.drawable.img_logo)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .transform(CropCircleTransformation())
                .into(spv_profile) // 'this' refers to the current Target
        }
    }
    fun loadPost(posts: ArrayList<Posts>) {
        val adapter = ViewPostAdapter(this, posts)
        spv_recyclerview!!.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        spv_recyclerview!!.layoutManager = layoutManager
        spv_recyclerview!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val previousVisibleItem = intArrayOf(0)
            var swipeCount = intArrayOf(0)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = intArrayOf(layoutManager.childCount)
                val totalItemCount = intArrayOf(layoutManager.itemCount)
                val firstVisibleItem = intArrayOf(layoutManager.findFirstVisibleItemPosition())
                if (visibleItemCount[0] > previousVisibleItem[0]) {
                    swipeCount[0]++
                    Log.d("Swipe count", "Swipe count FORWARD: " + swipeCount[0])
                }
                previousVisibleItem[0] = visibleItemCount[0]
            }
        })
        val animation = AnimationUtils.loadAnimation(spv_recyclerview!!.context, R.anim.fade_in)
        // Remove the current OnFlingListener before setting a new one
        if (spv_recyclerview!!.onFlingListener != null) {
            spv_recyclerview!!.onFlingListener = null
        }

        // Create a new instance of OnFlingListener and set it on the RecyclerView
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(spv_recyclerview)
    }

    private fun initializeLogic() {


    }
    fun showLayout(cond: Boolean) {
        if (cond) {
            spv_progressBar!!.visibility = View.VISIBLE
            spv_load_componenets!!.visibility = View.GONE
        } else {
            spv_progressBar!!.visibility = View.GONE
            spv_load_componenets!!.visibility = View.VISIBLE
        }
    }
}