package com.reyson.spotd.Class.Activity.Search

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Search : AppCompatActivity(), Interface.View {

    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private var ssh_profile: LinearLayout? = null
    private var ssh_trending: LinearLayout? = null
    private var ssh_notification: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)
        initialize(savedInstanceState)
        initializeLogic()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        ssh_profile = findViewById<LinearLayout>(R.id.ssh_profile)
        ssh_notification = findViewById<LinearLayout>(R.id.ssh_notification)
        ssh_trending = findViewById<LinearLayout>(R.id.ssh_trending)
    }

    private fun initializeLogic() {

    }
}