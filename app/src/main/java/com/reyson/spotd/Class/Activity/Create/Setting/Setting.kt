package com.reyson.spotd.Class.Activity.Create.Setting

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY.Companion.SET_COMMENT_STATUS
import com.reyson.spotd.Data.Api.KEY.Companion.SET_POST_STATUS
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R

class Setting : AppCompatActivity(), Interface.View {
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private var linear1: LinearLayout? = null
    private var linear2: LinearLayout? = null
    private var linear3: LinearLayout? = null
    private var rb_public: RadioButton? = null
    private var rb_semi_public: RadioButton? = null
    private var rb_private: RadioButton? = null
    private var switch1: Switch? = null
    private var scps_back: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post_setting)
        initialize(savedInstanceState)
        event(savedInstanceState)
        initializeLogic()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    private fun initialize(savedInstanceState: Bundle?) {
        linear1 = findViewById(R.id.linear1)
        linear2 = findViewById(R.id.linear2)
        linear3 = findViewById(R.id.linear3)
        rb_public = findViewById(R.id.radio_public)
        rb_semi_public = findViewById(R.id.radio_semi_public)
        rb_private = findViewById(R.id.radio_private)
        switch1 = findViewById(R.id.sub_main_switch_comment)
        scps_back = findViewById(R.id.scps_back)

    }

    private fun event(savedInstanceState: Bundle?) {
        scps_back?.setOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }

        linear1!!.setOnClickListener {
            rb_public!!.isChecked = true
            rb_semi_public!!.isChecked = false
            rb_private!!.isChecked = false
            saveString(this@Setting, SET_POST_STATUS, "0")
        }

        linear2!!.setOnClickListener {
            rb_public!!.isChecked = false
            rb_semi_public!!.isChecked = true
            rb_private!!.isChecked = false
            saveString(this@Setting, SET_POST_STATUS, "1")
        }

        linear3!!.setOnClickListener {
            rb_public!!.isChecked = false
            rb_semi_public!!.isChecked = false
            rb_private!!.isChecked = true
            saveString(this@Setting, SET_POST_STATUS, "2")
        }

        rb_public!!.setOnClickListener {
            rb_public!!.isChecked = true
            rb_semi_public!!.isChecked = false
            rb_private!!.isChecked = false
            saveString(this@Setting, SET_POST_STATUS, "0")
        }

        rb_semi_public!!.setOnClickListener {
            rb_public!!.isChecked = false
            rb_semi_public!!.isChecked = true
            rb_private!!.isChecked = false
            saveString(this@Setting, SET_POST_STATUS, "1")
        }

        rb_private!!.setOnClickListener {
            rb_public!!.isChecked = false
            rb_semi_public!!.isChecked = false
            rb_private!!.isChecked = true
            saveString(this@Setting, SET_POST_STATUS, "2")
        }

        // if (switch1?.isChecked == true){
        //     saveString(this@Setting, SET_COMMENT_STATUS, "1")
        // }else{
        //     saveString(this@Setting, SET_COMMENT_STATUS, "0")
        // }
        switch1?.setOnCheckedChangeListener { _, isChecked ->
            val value = if (isChecked) 1 else 2
            // Save the value to cache or SharedPreferences
            saveString(this@Setting, SET_COMMENT_STATUS, value.toString())
        }
    }

    private fun initializeLogic() {
        when (loadString(this@Setting, SET_POST_STATUS, "")) {
            "0" -> {
                rb_public?.isChecked = true
                rb_semi_public?.isChecked = false
                rb_private?.isChecked = false
                saveString(this@Setting, SET_POST_STATUS, "0")
            }
            "1" -> {
                rb_public?.isChecked = false
                rb_semi_public?.isChecked = true
                rb_private?.isChecked = false
                saveString(this@Setting, SET_POST_STATUS, "1")
            }
            "2" -> {
                rb_public?.isChecked = false
                rb_semi_public?.isChecked = false
                rb_private?.isChecked = true
                saveString(this@Setting, SET_POST_STATUS, "2")
            }
            else -> {
                rb_public?.isChecked = true
                rb_semi_public?.isChecked = false
                rb_private?.isChecked = false
                saveString(this@Setting, SET_POST_STATUS, "1")
            }
        }

        switch1?.isChecked = (loadString(this@Setting, SET_COMMENT_STATUS, "").equals("1"))

        // when () {
        //     "0" -> {
        //         switch1?.isChecked = false
        //         // saveString(this@Setting, SET_COMMENT_STATUS, "0")
        //     }
        //     "1" -> {
        //         switch1?.isChecked = true
        //         // saveString(this@Setting, SET_COMMENT_STATUS, "1")
        //     }
        //     else -> {
        //         // switch1?.isChecked = false
        //         // saveString(this@Setting, SET_COMMENT_STATUS, "0")
        //     }
        // }


    }
}