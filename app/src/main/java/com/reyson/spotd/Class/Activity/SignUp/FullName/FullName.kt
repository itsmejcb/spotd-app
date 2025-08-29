package com.reyson.spotd.Class.Activity.SignUp.FullName

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Request
import com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response
import com.reyson.spotd.Class.Activity.SignUp.Username.Username
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel

class FullName : AppCompatActivity(), Interface.View {
    private var ssf_first_name: EditText? = null
    private var ssf_middle_name: EditText? = null
    private var ssf_last_name: EditText? = null
    private var ssf_create_fullname: LinearLayout? = null
    private var ssf_progressBar: ProgressBar? = null
    private var ssf_label: TextView? = null
    private var fullName: String? = null
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_full_name)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        ssf_first_name = findViewById(R.id.ssf_first_name)
        ssf_middle_name = findViewById(R.id.ssf_middle_name)
        ssf_last_name = findViewById(R.id.ssf_last_name)
        ssf_create_fullname = findViewById(R.id.ssf_create_fullname)
        ssf_progressBar = findViewById(R.id.ssf_progressBar)
        ssf_label = findViewById(R.id.ssf_label)

    }

    private fun initializeLogic() {
        ssf_create_fullname?.setOnClickListener {
            ssf_first_name?.text.toString().let { it1 ->
                ssf_middle_name?.text.toString().let { it2 ->
                    ssf_last_name?.text.toString().let { it3 ->
                        presenter.createFullname(it1, it2, it3)
                        val formattedFirstName = capitalizeWords(it1)
                        val formattedMiddleName = capitalizeWords(it2)
                        val formattedLastName = capitalizeWords(it3)
                        fullName = formattedFirstName + " " + (if (it2.isEmpty()) "" else "$formattedMiddleName ") + formattedLastName
                    }
                }
            }
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getCreateFullNameObserver().observe(this, Observer<Response?> { userResponse ->
            userResponse?.let {
                val status = it.status
                if (status != null) {
                    loading(false)
                    when (status) {
                        // TODO code status here
                        "16201" -> {
                            SharedPreferencesUtils.saveString(this@FullName, KEY.FULL_NAME, fullName)
                            helper.intent(Username::class.java,R.anim.right_in,R.anim.left_out)
                        }
                        // for unauthorized token
                        "16401" -> {
                            dialogHandling(8)
                        }
                        // for Method Not Allowed
                        "16405" -> {
                            dialogHandling(0)
                        }
                        // for Not Implemented
                        "16503" -> {
                            dialogHandling(9)
                        }
                        // error occurred
                        else -> {
                            dialogHandling(0)
                        }
                    }
                }
                // Handle the status here, for instance, show a Toast or navigate to another activity based on the status received
                Log.i("Status for fullname", "Received status: $status")
            }
        })
    }

    override fun signUpFullname() {
        val request = Request()
        request.apply {
            uid = SharedPreferencesUtils.loadString(this@FullName, KEY.UID, "")
            first_name = ssf_first_name?.text.toString()
            middle_name = ssf_middle_name?.text.toString()
            last_name = ssf_last_name?.text.toString()
        }
        viewModel.userCreateFullName(request)
    }

    private fun capitalizeWords(input: String): String? {
        val tokens = input.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val capitalizedString = StringBuilder()
        for (token in tokens) {
            if (!token.isEmpty()) {
                val capLetter = token[0].uppercaseChar()
                val restOfWord = token.substring(1)
                val capitalizedToken = capLetter.toString() + restOfWord
                capitalizedString.append(" ").append(capitalizedToken)
            }
        }
        return capitalizedString.toString().trim { it <= ' ' }
    }

    override fun loading(boolean: Boolean){
        helper.loading(ssf_label!!,ssf_progressBar!!,boolean)
    }

    override fun dialogHandling(num: Int) {
        when (num) {

            1 -> dialog.showDialog(
                "Warning",
                "Your first name is required",
                "Ok",
                ""
            )

            2 -> dialog.showDialog(
                "Warning",
                "Your last name is required",
                "Ok",
                ""
            )

            3 -> dialog.showDialog(
                "Warning",
                "Your first name contains illegal characters.",
                "Ok",
                ""
            )

            4 -> dialog.showDialog(
                "Warning",
                "Your middle name contains illegal characters.",
                "Ok",
                ""
            )

            5 -> dialog.showDialog(
                "Warning",
                "Your last name contains illegal characters.",
                "Ok",
                ""
            )

            6 -> dialog.showDialog(
                "Oops",
                "Your first name contains harmful content",
                "Ok",
                ""
            )

            7 -> dialog.showDialog(
                "Oops",
                "Your middle name contains harmful content",
                "Ok",
                ""
            )

            8 -> dialog.showDialog(
                "Warning",
                "Unauthorized",
                "Ok",
                ""
            )
            9 -> dialog.showDialog(
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