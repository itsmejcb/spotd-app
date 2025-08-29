package com.reyson.spotd.Class.Activity.SignUp.Username

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reyson.spotd.Class.Activity.SignIn.SignIn
import com.reyson.spotd.Class.Activity.SignUp.FullName.FullName
import com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Upload
import com.reyson.spotd.Class.Activity.SignUp.Username.Model.Request
import com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class Username : AppCompatActivity(), Interface.View {
    private var ssu_username: EditText? = null
    private var ssu_create_username: LinearLayout? = null
    private var ssu_label: TextView? = null
    private var ssu_progressBar: ProgressBar? = null
    private val usernameLimit = 30
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_username)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        ssu_username = findViewById(R.id.ssu_username)
        ssu_create_username = findViewById(R.id.ssu_create_username)
        ssu_label = findViewById(R.id.ssu_label)
        ssu_progressBar = findViewById(R.id.ssu_progressBar)

    }

    private fun initializeLogic() {
        ssu_create_username?.setOnClickListener {
            ssu_username?.text.toString().let { it1 ->
                presenter.createUsername(it1)
            }
        }
        ssu_username?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun afterTextChanged(editable: Editable) {
                val currentLength = editable.length
                // scp_length?.text = "$currentLength/$usernameLimit"
                // Check if the length of the text exceeds the character limit
                if (editable.length > usernameLimit) {
                    // If it exceeds the limit, truncate the text
                    ssu_username?.setText(editable.subSequence(0, usernameLimit))
                    ssu_username?.setSelection(usernameLimit) // Move cursor to the end
                }
            }
        })

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getCreateUsernameObserver().observe(this, Observer<Response?> { userResponse ->
            userResponse?.let {
                val status = it.status
                Log.d("Status in username", "Received status: $status")
                if (status != null) {
                    loading(false)
                    when (status) {
                        // TODO code status here
                        "16201" -> {
                            SharedPreferencesUtils.saveString(
                                this@Username,
                                KEY.USERNAME,
                                ssu_username?.text.toString()
                            )
                            helper.intent(Upload::class.java, R.anim.right_in, R.anim.left_out)
                        }
                        // for unauthorized token
                        "16401" -> {
                            dialogHandling(5)
                        }
                        // for Method Not Allowed
                        "16405" -> {
                            dialogHandling(0)
                        }
                        // for Not Implemented
                        "16503" -> {
                            dialogHandling(6)
                        }

                        else -> {
                            dialogHandling(0)
                        }
                    }
                }
                // Handle the status here, for instance, show a Toast or navigate to another activity based on the status received
            }
        })
    }

    override fun signUpUsername() {
        val request = Request()
        request.apply {
            uid = SharedPreferencesUtils.loadString(this@Username, KEY.UID, "")
            username = ssu_username?.text.toString()
        }
        viewModel.userCreateUserName(request)
    }

    override fun loading(boolean: Boolean) {
        helper.loading(ssu_label!!, ssu_progressBar!!, boolean)
    }

    override fun dialogHandling(num: Int) {
        when (num) {

            1 -> dialog.showDialog(
                "Warning",
                "Your username is required",
                "Ok",
                ""
            )

            2 -> dialog.showDialog(
                "Warning",
                "Username must be at least 2-20 characters long.",
                "Ok",
                ""
            )

            3 -> dialog.showDialog(
                "Warning",
                "Your username contains illegal characters.",
                "Ok",
                ""
            )

            4 -> dialog.showDialog(
                "Warning",
                "Your username contains harmful content.",
                "Ok",
                ""
            )

            5 -> dialog.showDialog(
                "Warning",
                "Unauthorized",
                "Ok",
                ""
            )

            6 -> dialog.showDialog(
                "Warning",
                "Service Unavailable",
                "Ok",
                ""
            )

            else -> dialog.showDialog(
                "Warning",
                "An error occurred. Please try again.",
                "Ok",
                "Learn more"
            )
        }
    }
}