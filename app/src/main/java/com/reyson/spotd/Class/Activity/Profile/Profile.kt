package com.reyson.spotd.Class.Activity.Profile

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
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY.Companion.UID
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class Profile : AppCompatActivity(), Interface.View {

    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private var spa_app_bar: AppBarLayout? = null
    private var spa_coordinator: CoordinatorLayout? = null
    private var spa_collapsingtoolbar: CollapsingToolbarLayout? = null
    private var spa_username: TextView? = null
    private var spa_full_name: TextView? = null
    private var spa_profile: ImageView? = null
    private var spa_setting: ImageView? = null
    private var spa_back: ImageView? = null
    private var spa_bio_label: TextView? = null
    private var bio_linear: LinearLayout? = null
    private var user1_linear: LinearLayout? = null
    private var user2_linear: LinearLayout? = null
    private var spa_edit_profile_linear: LinearLayout? = null
    private var spa_follow: LinearLayout? = null
    private var spa_more_linear_1: LinearLayout? = null
    private var spa_total_post_label: TextView? = null
    private var spa_total_follower_label: TextView? = null
    private var spa_total_following_label: TextView? = null
    private var spa_follow_label: TextView? = null
    private var spa_viewpager: ViewPager? = null
    private var spa_tabLayout: TabLayout? = null
    private val spa_swiperefresher: SwipeRefreshLayout? = null
    private var spa_progressBar: ProgressBar? = null
    private var retryCount = 0
    private val userUid: String? = null
    private var num = 0
    private val isClickableSpanClicked = false
    private val objectClicked = ""
    private var isFollow = false
    private var isFollower = false
    private var cond = false
    private val MAX_RETRY_COUNT = 3
    private lateinit var highlightedTextView: HighlightedTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
        fetchProfile()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        highlightedTextView = HighlightedTextView(this)
        spa_app_bar = findViewById(R.id.spa_app_bar)
        spa_viewpager = findViewById(R.id.spa_viewpager)
        spa_tabLayout = findViewById(R.id.spa_tabLayout)
        spa_coordinator = findViewById(R.id.spa_coordinator)
        spa_collapsingtoolbar = findViewById(R.id.spa_collapsing_tool_bar)
        spa_username = findViewById(R.id.spa_username)
        spa_full_name = findViewById(R.id.spa_full_name)
        spa_profile = findViewById(R.id.spa_profile)
        bio_linear = findViewById(R.id.bio_linear)
        spa_follow = findViewById(R.id.spa_follow)
        spa_bio_label = findViewById(R.id.spa_bio_label)
        spa_total_post_label = findViewById(R.id.spa_total_post_label)
        spa_total_follower_label = findViewById(R.id.spa_total_follower_label)
        spa_total_following_label = findViewById(R.id.spa_total_following_label)
        spa_follow_label = findViewById(R.id.spa_follow_label)
        spa_more_linear_1 = findViewById(R.id.spa_more_linear_1)
        user1_linear = findViewById(R.id.user1_linear)
        user2_linear = findViewById(R.id.user2_linear)
        spa_back = findViewById(R.id.spa_back)
        spa_setting = findViewById(R.id.spa_setting)
        spa_progressBar = findViewById(R.id.spa_progressBar)
        spa_edit_profile_linear = findViewById(R.id.spa_edit_profile_linear)

    }

    private fun fetchProfile() {
        showLayout(true)
        val request = Request()
        request.apply {
            uid = "10516012124013980"
            user = "10516012124013980"
            // uid = SharedPreferencesUtils.loadString(this@Profile, KEY.UID, "")
            // user = SharedPreferencesUtils.loadString(this@Profile, KEY.USER_UID, "")
        }
        viewModel.userFetchProfile(request)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getFetchProfileObserver().observe(this, Observer<Response?> { userResponse ->
            userResponse?.let {
                val status = it.status
                val uid = it.uid
                val username = it.username
                val full_name = it.full_name
                val profile_picture = it.profile_picture
                val verified = it.verified
                val bio = it.bio
                val bio_status = it.bio_status
                val total_post = it.total_post
                val total_following = it.total_following
                val total_follower = it.total_follower
                val is_following = it.is_following
                val is_follower = it.is_follower
                if (status != null){
                //     todo error occurred dialog
                    return@let
                }
                showLayout(false)
                loadComponents(
                    uid.toString(),
                    username,
                    full_name,
                    profile_picture,
                    bio.toString(),
                    bio_status.toString(),
                    total_post,
                    total_following,
                    total_follower,
                    verified,
                    is_following.toString(),
                    is_follower.toString()
                )

                Log.i("Status for profile", "Received status: $uid")
                Log.i("Status for profile", "Received status: $profile_picture")

            }
        })
    }

    private fun initializeLogic() {
        spa_tabLayout!!.addTab(spa_tabLayout!!.newTab().setText("Posts"))
        val frag = ProfileFragmentAdapter(applicationContext, supportFragmentManager)
        frag.setTabCount(1)
        spa_tabLayout!!.setupWithViewPager(spa_viewpager)
        spa_viewpager!!.adapter = frag
    }

    fun loadComponents(
        uid: String,
        username: String?,
        fullname: String?,
        profile: String?,
        bio: String,
        bio_status: String,
        total_post: String?,
        total_following: String?,
        total_follower: String?,
        verify: String?,
        is_following: String,
        is_follower: String
    ) {
        try {
            if (uid == loadString(this, UID, "")) {
                user1_linear!!.visibility = View.GONE
                user2_linear!!.visibility = View.VISIBLE
            } else {
                user1_linear!!.visibility = View.VISIBLE
                user2_linear!!.visibility = View.GONE
            }
            if (!TextUtils.isEmpty(username)) {
                spa_username!!.text = username
            }
            if (!TextUtils.isEmpty(fullname)) {
                spa_full_name!!.text = fullname
            }
            if (!TextUtils.isEmpty(profile)) {
                val xx = ContextCompat.getDrawable(this, R.drawable.img_logo)
                xx?.let { spa_profile?.let { it1 -> helper.loadProfile("$uid/$profile", it1, it) } }

            } else {
                Picasso.get()
                    .load(R.drawable.img_logo)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(CropCircleTransformation())
                    .into(spa_profile) // 'this' refers to the current Target
            }
            if (!TextUtils.isEmpty(bio)) {
                val bs = bio_status.toInt()
                Log.i("data bio", bio)
                var yourString = bio
                yourString = yourString.replace("\\n", "\n")
                val input = yourString
                val cleanedText = removeLeadingSpaces(input)
                Log.i("TAG", cleanedText)
                spa_bio_label?.let {
                    highlightedTextView.setTextWithHighlights(
                        it,
                        cleanedText,
                        20,
                        true
                    )
                }
                highlightedTextView.setDescription(
                    spa_bio_label,
                    username,
                    fullname,
                    cleanedText,
                    profile
                )
                val b_stats = bs == 1 // Change the condition to check if bs is equal to 1
                bio_linear!!.visibility = if (b_stats) View.VISIBLE else View.GONE
            } else {
                bio_linear!!.visibility = View.GONE
            }
            if (!TextUtils.isEmpty(total_post)) {
                spa_total_post_label!!.text = total_post
            } else {
                spa_total_post_label!!.text = "0"
            }
            if (!TextUtils.isEmpty(total_following)) {
                spa_total_following_label!!.text = total_following
            } else {
                spa_total_following_label!!.text = "0"
            }
            if (!TextUtils.isEmpty(total_follower)) {
                spa_total_follower_label!!.text = total_follower
            } else {
                spa_total_follower_label!!.text = "0"
            }
            val following = is_following.toInt()
            val follower = is_follower.toInt()
            if (following == 1) {
                if (follower == 1) {
                    // Both following and follower are 1
                    isFollow = true
                    isFollower = true
                    cond = true
                    spa_follow_label!!.text =
                        "Unfollow" // You can set the text as "Unfollow" or as needed
                    spa_follow!!.background = resources.getDrawable(R.drawable.background_linearlayout)
                } else {
                    // Only following is 1
                    isFollow = true
                    isFollower = false
                    cond = false
                    spa_follow_label!!.text =
                        "Unfollow" // You can set the text as "Unfollow" or as needed
                    spa_follow!!.background = resources.getDrawable(R.drawable.background_linearlayout)
                }
            } else if (follower == 1) {
                // Only follower is 1
                isFollow = false
                isFollower = true
                cond = true
                num = 2
                spa_follow_label!!.text =
                    "Follow Back" // You can set the text as "Follow Back" or as needed
                spa_follow!!.background = resources.getDrawable(R.drawable.background_linearlayout)
            } else {
                // Both following and follower are 0, or other cases
                isFollow = false
                isFollower = false
                cond = false
                num = 1
                spa_follow!!.background = resources.getDrawable(R.drawable.background_button_with_rounded)
                spa_follow_label!!.text = "Follow" // You can set the text as "Follow" or as needed
            }

            // int following = Integer.parseInt(is_following);
            // int follower = Integer.parseInt(is_follower);
            // if (following == 1) {
            //     if (following != 0) {
            //         int stats = Integer.parseInt(String.valueOf(following));
            //         switch (stats) {
            //             case 1:
            //                 isFollow = true;
            //                 spa_follow_label.setText("Unfollow");
            //                 // spa_follow.setBackground(getResources().getDrawable(R.drawable.bg_layout_2));
            //                 break;
            //             default:
            //                 isFollow = false;
            //                 cond = false;
            //                 spa_follow_label.setText("Follow");
            //                 break;
            //         }
            //     } else {
            //         // Handle the case when "following" is null or empty
            //         spa_follow.setBackground(getResources().getDrawable(R.drawable.bg_layout_btn));
            //         isFollow = false;
            //         spa_follow_label.setText("Follow");
            //     }
            // } else {
            //     if (follower != 0) {
            //         int stats = Integer.parseInt(String.valueOf(follower));
            //         switch (stats) {
            //             case 1:
            //                 isFollower = true;
            //                 cond = true;
            //                 spa_follow_label.setText("follow back");
            //                 spa_follow.setBackground(getResources().getDrawable(R.drawable.bg_layout_2));
            //                 break;
            //             default:
            //                 spa_follow.setBackground(getResources().getDrawable(R.drawable.bg_layout_btn));
            //                 isFollower = false;
            //                 cond = false;
            //                 spa_follow_label.setText("Follow");
            //                 break;
            //         }
            //     } else {
            //         // Handle the case when "follower" is null or empty
            //         spa_follow.setBackground(getResources().getDrawable(R.drawable.bg_layout_btn));
            //         isFollower = false;
            //         spa_follow_label.setText("Follow");
            //     }
            // }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun removeLeadingSpaces(input: String): String {
        val lines = input.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val result = StringBuilder()
        for (line in lines) {
            // Trim leading and trailing spaces on each line
            val trimmedLine = line.trim { it <= ' ' }

            // Append the trimmed line to the result with a newline character
            result.append(trimmedLine).append("\n")
        }

        // Remove the trailing newline character if needed
        if (result.length > 0) {
            result.setLength(result.length - 1)
        }
        return result.toString()
    }

    fun showLayout(con: Boolean) {
        if (spa_app_bar == null || spa_viewpager == null || spa_progressBar == null) {
            // Add null checks to ensure the views are not null
            return
        }
        if (con) {
            spa_app_bar!!.visibility = View.GONE
            spa_viewpager!!.visibility = View.GONE
            spa_progressBar!!.visibility = View.VISIBLE
        } else {
            spa_app_bar!!.visibility = View.VISIBLE
            spa_viewpager!!.visibility = View.VISIBLE
            spa_progressBar!!.visibility = View.GONE
        }
    }
}