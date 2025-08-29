package com.reyson.spotd.Class.Activity.Hashtag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Hashtag : AppCompatActivity(), Interface.View {

    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_username)
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