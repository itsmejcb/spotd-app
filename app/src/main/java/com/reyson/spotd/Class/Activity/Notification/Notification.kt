package com.reyson.spotd.Class.Activity.Notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.reyson.spotd.Class.Activity.Home.Home
import com.reyson.spotd.Class.Activity.Notification.Model.Request
import com.reyson.spotd.Class.Activity.Profile.Profile
import com.reyson.spotd.Class.Activity.Search.Search
import com.reyson.spotd.Class.Components.Adapter.NotifyParentAdapter
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.GroupedNotification
import com.reyson.spotd.Data.Model.Notify
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Notification : AppCompatActivity(), Interface.View {

    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private var sln_recyclerview: RecyclerView? = null
    private var sln_empty: LinearLayout? = null
    private var sln_progressBar: ProgressBar? = null
    private var sln_profile: LinearLayout? = null
    private var sln_trending: LinearLayout? = null
    private var sln_home: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
        fetchNotification()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getFetchNotificationObserver().observe(this@Notification, Observer { response ->
            response?.notify?.let { posts ->
                Log.i("resultsss", posts.toString())
                if (posts.size > 0) {
                    loading(1)
                } else {
                    loading(3)
                }
                // LoadPost(processedPosts)
                // Load the ArrayList<Posts> into the RecyclerView adapter here
                // Example: adapter.submitList(posts)
            }
        })
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        sln_profile = findViewById(R.id.sln_profile)
        sln_trending = findViewById(R.id.sln_trending)
        sln_home = findViewById(R.id.sln_home)
        sln_recyclerview = findViewById(R.id.sln_recyclerview)
        sln_empty = findViewById(R.id.sln_empty)
        sln_progressBar = findViewById(R.id.sln_progressBar)

        sln_home?.setOnClickListener {
            helper.intent(Home::class.java, 0, 0)
        }

        sln_profile?.setOnClickListener {
            helper.intent(Profile::class.java, 0, 0)
        }
        sln_trending?.setOnClickListener {
            helper.intent(Search::class.java, 0, 0)
        }
    }

    private fun initializeLogic() {

    }

    private fun fetchNotification() {
        loading(2)
        val request = Request()
        request.apply {
            // uid = "10516012124013980"
            uid = SharedPreferencesUtils.loadString(this@Notification, KEY.UID, "")
        }
        viewModel.userFetchNotification(request)
    }

    private fun groupDataByTimestamp(data: ArrayList<Notify>): java.util.ArrayList<GroupedNotification> {
        val groupedDataList: java.util.ArrayList<GroupedNotification> =
            java.util.ArrayList<GroupedNotification>()
        val todayData: ArrayList<Notify> =
            ArrayList<Notify>()
        val yesterdayData: ArrayList<Notify> =
            ArrayList<Notify>()
        val sevenDaysAgoData: ArrayList<Notify> =
            ArrayList<Notify>()
        val currentTimeMillis = System.currentTimeMillis()
        for (item in data) {
            val itemMs: Long = item.getMs().toLong()
            if (currentTimeMillis - itemMs < 24 * 60 * 60 * 1000) {
                todayData.add(item)
            } else if (currentTimeMillis - itemMs < 2 * 24 * 60 * 60 * 1000) {
                yesterdayData.add(item)
            } else if (currentTimeMillis - itemMs < 7 * 24 * 60 * 60 * 1000) {
                sevenDaysAgoData.add(item)
            }
        }
        if (!todayData.isEmpty()) {
            groupedDataList.add(GroupedNotification("Today", todayData))
        }
        if (!yesterdayData.isEmpty()) {
            groupedDataList.add(GroupedNotification("Yesterday", yesterdayData))
        }
        if (!sevenDaysAgoData.isEmpty()) {
            groupedDataList.add(GroupedNotification("7 Days Ago", sevenDaysAgoData))
        }
        return groupedDataList
    }

    fun loading(num: Int) {
        when (num) {
            1 -> {
                sln_recyclerview?.visibility = View.VISIBLE
                sln_empty?.visibility = View.GONE
                sln_progressBar?.visibility = View.GONE
            }

            2 -> {
                sln_recyclerview?.visibility = View.GONE
                sln_progressBar?.visibility = View.VISIBLE
                sln_empty?.visibility = View.GONE
            }

            else -> {
                sln_recyclerview?.visibility = View.VISIBLE
                sln_progressBar?.visibility = View.GONE
                sln_empty?.visibility = View.GONE
            }
        }
    }

    fun loadNotify(notifications: ArrayList<Notify>) {
        val groupedDataList: ArrayList<GroupedNotification> =
            groupDataByTimestamp(notifications)
        val adapter = NotifyParentAdapter(this@Notification, groupedDataList)
        sln_recyclerview!!.adapter = adapter
        val layoutManager =
            LinearLayoutManager(this@Notification, LinearLayoutManager.VERTICAL, false)
        sln_recyclerview!!.layoutManager = layoutManager
        sln_recyclerview!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        val animation = AnimationUtils.loadAnimation(this@Notification, R.anim.fade_in)
        // Remove the current OnFlingListener before setting a new one
        if (sln_recyclerview!!.onFlingListener != null) {
            sln_recyclerview!!.onFlingListener = null
        }

        // Create a new instance of OnFlingListener and set it on the RecyclerView
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(sln_recyclerview)
    }

}