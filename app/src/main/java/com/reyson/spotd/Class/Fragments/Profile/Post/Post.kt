package com.reyson.spotd.Class.Fragments.Profile.Post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.reyson.spotd.Class.Components.Adapter.PostsAdapter
import com.reyson.spotd.Class.Components.Grid.GridSpacing
import com.reyson.spotd.Class.Fragments.Profile.Post.Model.Request
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.Posts
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Post : Fragment() {
    // private val posts: ArrayList<Posts> = ArrayList()
    private lateinit var viewModel: ViewModel
    private var fpp_recyclerview: RecyclerView? = null
    private var fpp_swiperefresher1: SwipeRefreshLayout? = null
    private var fpp_layout_1: LinearLayout? = null
    private var fpp_layout_2: LinearLayout? = null
    private var fpp_layout_3: LinearLayout? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_post, container, false)
        initialize(v, savedInstanceState)
        return v
    }

    private fun initialize(v: View, savedInstanceState: Bundle?) {
        fpp_swiperefresher1 = v.findViewById(R.id.fpp_swiperefresher1)
        fpp_recyclerview = v.findViewById(R.id.fpp_recyclerview)
        fpp_layout_1 = v.findViewById(R.id.fpp_layout_1)
        fpp_layout_2 = v.findViewById(R.id.fpp_layout_2)
        fpp_layout_3 = v.findViewById(R.id.fpp_layout_3)
        initViewModel()
        val request = Request()
        request.apply {
            uid = "10516012124013980"
            // uid = loadString(requireActivity(),KEY.UID,"")
        }
        viewModel.userFetchProfilePost(request)
        fpp_swiperefresher1?.setOnRefreshListener(OnRefreshListener {
            // presenter.fetchPosts(userUid)
            fpp_swiperefresher1?.isRefreshing = false
        })
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getFetchProfilePostObserver().observe(requireActivity(), Observer { response ->
            response?.posts?.let { posts ->
                Log.i("resultsss", posts.toString())
                Log.i("resultsss11111", posts.toString())
                val processedPosts: ArrayList<Posts> = posts
                loadPost(processedPosts)
                // Load the ArrayList<Posts> into the RecyclerView adapter here
                // Example: adapter.submitList(posts)
            }
        })
    }
    private fun loadPost(posts: ArrayList<Posts>) {
        val spanCount = 3 // Number of columns in the grid

        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing) // Spacing in pixels

        val includeEdge = false // Whether to include spacing at the edges

        val adapter = PostsAdapter(requireActivity(), posts)
        fpp_recyclerview!!.adapter = adapter
        fpp_recyclerview!!.layoutManager = GridLayoutManager(requireActivity(), spanCount)
        fpp_recyclerview!!.addItemDecoration(
            GridSpacing(
                requireActivity(),
                spanCount,
                spacing,
                includeEdge
            )
        )
    }
    companion object {
        fun newInstance(): Post {
            return Post()
        }
    }
}
