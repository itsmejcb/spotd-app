package com.reyson.spotd.Class.Activity.SignIn

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reyson.spotd.Class.Activity.Home.Home
import com.reyson.spotd.Class.Activity.SignIn.Model.Request
import com.reyson.spotd.Class.Activity.SignIn.Model.Response
import com.reyson.spotd.Class.Activity.SignUp.Email.Email
import com.reyson.spotd.Class.Activity.SignUp.FullName.FullName
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import java.util.regex.Pattern

class SignIn : AppCompatActivity(), Interface.View {
    private var object_clicked: String? = null
    private var ssin_email: EditText? = null
    private var ssin_password: EditText? = null
    private var ssin_show_password: ImageView? = null
    private var ssin_btn_label: TextView? = null
    private var ssin_sign_in: LinearLayout? = null
    private var ssin_sign_up: TextView? = null
    private var ssin_progressBar: ProgressBar? = null
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private val emailLimit = 255
    private val passwordLimit = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        ssin_email = findViewById(R.id.ssin_email)
        ssin_password = findViewById(R.id.ssin_password)
        ssin_btn_label = findViewById(R.id.ssin_btn_label)
        ssin_sign_in = findViewById(R.id.ssin_sign_in)
        ssin_sign_up = findViewById(R.id.ssin_sign_up)
        ssin_progressBar = findViewById(R.id.ssin_progressBar)
        ssin_show_password = findViewById(R.id.ssin_show_password)
        if (SharedPreferencesUtils.loadString(this@SignIn,KEY.UID,"").isNotEmpty()){
            helper.intent(Home::class.java, R.anim.right_in, R.anim.left_out)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getLoginObserver().observe(this, Observer<Response?> { userResponse ->
            userResponse?.let {
                val status = it.status
                val uid = it.uid

                if (status != null) {
                    helper.loading(ssin_btn_label!!, ssin_progressBar!!, true)
                    when (status) {
                        // TODO code status here
                        "" -> {
                        }

                        else -> {
                            dialogHandling(0, "")
                        }
                    }
                    return@Observer
                }
                if (uid!!.isNotEmpty() && uid.length == 17) {
                    SharedPreferencesUtils.saveString(this@SignIn, KEY.UID, uid)
                    helper.intent(FullName::class.java, R.anim.right_in, R.anim.left_out)
                }


                // Handle the status here, for instance, show a Toast or navigate to another activity based on the status received
                Log.i("Status", "Received status: $status")
                // val displayName: String = it.data?.userData?.displayName ?: ""
                // val id: String = (it.data?.userData?.id ?: 0).toString()
                Log.i("data", "working")
            }
        })
    }

    private fun initializeLogic() {
        // sign in to server
        ssin_sign_up?.setOnClickListener {
            helper.intent(Email::class.java, R.anim.right_in, R.anim.left_out)
        }
        ssin_sign_in?.setOnClickListener {
            ssin_email?.text.toString().let { it1 ->
                ssin_password?.text.toString().let { it2 ->
                    helper.loading(ssin_btn_label!!, ssin_progressBar!!, true)
                    presenter.signIn(it1, it2)
                }
            }
        }
        // show password
        ssin_show_password?.setOnClickListener {
            ssin_password?.let { pass ->
                ssin_show_password?.let { eye ->
                    presenter.showPassword(pass, eye)
                }
            }
        }
        // show password eye visibility
        ssin_email?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
                // presenter.onPasswordTextChanged(param1)
            }

            override fun beforeTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
            }

            override fun afterTextChanged(param: Editable) {
                val currentLength: Int = param.length
                if (currentLength > emailLimit) {
                    ssin_password?.setText(param.subSequence(0, emailLimit))
                    ssin_password?.setSelection(emailLimit)
                }
            }
        })

        ssin_password?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
                presenter.onPasswordTextChanged(param1)
            }

            override fun beforeTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
            }

            override fun afterTextChanged(param: Editable) {
                val currentLength: Int = param.length
                if (currentLength > passwordLimit) {
                    ssin_password?.setText(param.subSequence(0, passwordLimit))
                    ssin_password?.setSelection(passwordLimit)
                }
            }
        })
    }

    override fun signIn() {
        val request = Request()
        request.apply {
            email = ssin_email?.text.toString()
            password = ssin_password?.text.toString()
        }
        viewModel.userLogin(request)
    }

    override fun showPasswordVisibility(visible: Boolean) {
        ssin_show_password?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun span(txt: TextView, value: String?) {
        txt.movementMethod = LinkMovementMethod.getInstance()
        updateSpan(value.toString(), txt)
    }

    private fun updateSpan(str: String, txt: TextView) {
        val ssb = SpannableString(str)
        val pattern = Pattern.compile("(?<![^\\s])(([Learn more]{10})(\\.?)+)(?![^\\s,])")
        val matcher = pattern.matcher(str)
        while (matcher.find()) {
            val span: ProfileSpan =
                ProfileSpan()
            ssb.setSpan(span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        txt.text = ssb
    }

    inner class ProfileSpan : ClickableSpan() {

        override fun onClick(view: View) {
            if (view is TextView) {
                if (view.text is Spannable) {
                    val sp = view.text as Spannable
                    val start = sp.getSpanStart(this)
                    val end = sp.getSpanEnd(this)
                    object_clicked = sp.subSequence(start, end).toString()
                    if (object_clicked == "Learn more" && view.getId() == R.id.sdm_message) {
                        alertDialog.dismiss()
                        // description(
                        //     this,
                        //     "Punishment",
                        //     "developer team",
                        //     "learn_more1",
                        //     ""
                        // )
                    }
                }
            }
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
            ds.color = Color.parseColor("#FFD700")
            ds.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
    }

    override fun dialogHandling(num: Int, ms: String?) {
        when (num) {

            1 -> dialog.showDialog(
                "Warning",
                "Your email and password is required.\n" +
                        "Please try again.",
                "Ok",
                ""
            )

            2 -> dialog.showDialog(
                "Warning",
                "Your email is required.",
                "Ok",
                ""
            )

            3 -> dialog.showDialog(
                "Warning",
                "Your password is required.",
                "Ok",
                ""
            )

            4 -> dialog.showDialog(
                "Warning",
                "Your password should be 8 characters long.",
                "Ok",
                ""
            )

            5 -> dialog.showDialog(
                "Warning",
                "The password entered is incorrect. Please ensure that you enter the correct password to access your account.",
                "Ok",
                ""
            )

            6 -> dialog.showDialog(
                "Warning",
                "Your email \"${ssin_email?.text.toString()}\" contains illegal characters. Please try again.",
                "Ok",
                ""
            )

            7 -> dialog.showDialog(
                "Warning",
                "The account you attempted to access violates our content policy. Posting or accessing adult content is prohibited.\n" +
                        "You are banned from using the app for 15 days. Your ban will be lifted on ${ms.toString()}",
                "Ok",
                "Learn more"
            )


            8 -> dialog.showDialog(
                "Oops",
                "There is no account with that email address, \"${ssin_email?.text.toString()}\". If you don't already have one, you can sign up for Spotd right away.",
                "Sign Up",
                "Cancel"
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