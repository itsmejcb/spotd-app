package com.reyson.spotd.Class.Activity.Posts.Comment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.reyson.spotd.Class.Activity.Profile.Model.Request
import com.reyson.spotd.Class.Activity.Profile.Model.Response
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.HighlightedTextView
import com.reyson.spotd.Class.Components.Fragments.ProfileFragmentAdapter
import com.reyson.spotd.Data.Api.KEY.Companion.UID
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class Comment : AppCompatActivity(), Interface.View {

    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initialize(savedInstanceState)
        initializeLogic()

    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)

    }

    private fun initializeLogic() {


    }

}