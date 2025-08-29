package com.reyson.spotd.Class.Activity.Home

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
// import androidx.viewpager.widget.ViewPager
import com.reyson.spotd.Class.Activity.Create.Upload.Upload
import com.reyson.spotd.Class.Activity.Notification.Notification
import com.reyson.spotd.Class.Activity.Profile.Profile
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Fragments.FragFragmentAdapter
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Home : AppCompatActivity(),Interface.View {
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private var ssh_profile: LinearLayout? = null
    private var ssh_trending: LinearLayout? = null
    private var ssh_notification: LinearLayout? = null
    private var ssh_create_post: ImageView? = null
    private var ssh_tablayout: TabLayout? = null
    private var ssh_viewpager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initialize(savedInstanceState)
        initializeLogic()
        Log.i("uid",loadString(this@Home, KEY.UID,""))
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this,this)
        helper = Helper(this)
        ssh_profile = findViewById(R.id.ssh_profile)
        ssh_notification = findViewById(R.id.ssh_notification)
        ssh_trending = findViewById(R.id.ssh_trending)
        ssh_create_post = findViewById(R.id.ssh_create_post)
        ssh_tablayout = findViewById(R.id.ssh_tablayout)
        ssh_viewpager = findViewById(R.id.ssh_viewpager)
        ssh_profile?.setOnClickListener {
            saveString(this@Home, KEY.USER_UID,loadString(this@Home, KEY.UID,""))
            helper.intent(Profile::class.java,0,0)
        }
        ssh_notification?.setOnClickListener {
            helper.intent(Notification::class.java,0,0)
        }
        ssh_create_post?.setOnClickListener {
            helper.intent(Upload::class.java,R.anim.right_in,R.anim.left_out)
            SharedPreferencesUtils.removeString(this,"list_photos")
        }
    }

    private fun initializeLogic() {
        ssh_tablayout?.addTab(ssh_tablayout!!.newTab().setText("Post"))
        val frag = FragFragmentAdapter(applicationContext, supportFragmentManager)
        frag.setTabCount(1)
        ssh_viewpager?.adapter = frag
        ssh_tablayout?.setupWithViewPager(ssh_viewpager)

    }
}