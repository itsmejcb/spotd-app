package com.reyson.spotd.Class.Fragments.Home.Foryou

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.reyson.spotd.Class.Components.Adapter.ForyouAdapter
import com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Request
import com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import java.util.ArrayList

class Foryou : Fragment() {
    private val posts: ArrayList<Posts> = ArrayList()
    private lateinit var ffy_recyclerview: RecyclerView
    private lateinit var ffy_transition: LinearLayout
    private lateinit var viewModel: ViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_foryou, container, false)
        initialize(v, savedInstanceState)
        return v
    }

    private fun initialize(v: View, savedInstanceState: Bundle?) {
        ffy_recyclerview = v.findViewById(R.id.ffy_recyclerview)
        ffy_transition = v.findViewById(R.id.ffy_transition)
        ffy_transition.isVisible = false
        initViewModel()
        val request = Request()
        request.apply {
            // uid = "10516012124013980"
            uid = loadString(requireActivity(),KEY.UID,"")
        }
        viewModel.userFetchPost(request)
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getFetchForyouObserver().observe(requireActivity(), Observer<Response?> { response ->
            response?.foryou?.let { posts ->
                Log.i("resultsss", posts.toString())
                Log.i("resultsss", posts.toString())
                val processedPosts: ArrayList<Posts> = posts
                LoadPost(processedPosts)
                // Load the ArrayList<Posts> into the RecyclerView adapter here
                // Example: adapter.submitList(posts)
            }
        })
    }
    private fun LoadPost(posts: ArrayList<Posts>) {
        val adapter = ForyouAdapter(requireActivity(), posts)
        ffy_recyclerview.adapter = adapter
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        ffy_recyclerview.layoutManager = layoutManager
        ffy_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val previousVisibleItem = intArrayOf(0)
            private val swipeCount = intArrayOf(0)
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
        val animation: Animation = AnimationUtils.loadAnimation(ffy_recyclerview.context,
            R.anim.fade_in
        )
        // Remove the current OnFlingListener before setting a new one
        if (ffy_recyclerview.onFlingListener != null) {
            ffy_recyclerview.onFlingListener = null
        }
        // Create a new instance of OnFlingListener and set it on the RecyclerView
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(ffy_recyclerview)
    }
    companion object {
        fun newInstance(): Foryou {
            return Foryou()
        }
    }
}
