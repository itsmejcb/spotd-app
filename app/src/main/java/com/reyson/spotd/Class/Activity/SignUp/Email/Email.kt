package com.reyson.spotd.Class.Activity.SignUp.Email

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
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
import com.reyson.spotd.Class.Activity.SignIn.SignIn
import com.reyson.spotd.Class.Activity.SignUp.Email.Model.Request
import com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response
import com.reyson.spotd.Class.Activity.SignUp.FullName.FullName
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.Validator
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.CountryCode.CountryCodeViewModel
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.saveString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import java.util.regex.Pattern

class Email : AppCompatActivity(), Interface.View {

    private var terms = ""
    private var data = ""
    private var cookies = ""
    private var object_clicked = ""
    private var CountryCode = ""
    private val emailAddressLimit = 150
    private val passwordLimit = 30
    private val confirmPasswordLimit = 30
    private var sse_email: EditText? = null
    private var sse_password: EditText? = null
    private var sse_confirm_password: EditText? = null
    private var sse_back: ImageView? = null
    private var sse_show_password_1: ImageView? = null
    private var sse_show_password_2: ImageView? = null
    private var sse_create_account: TextView? = null
    private var sse_create_email: LinearLayout? = null
    private var sse_prenup: TextView? = null
    private var sse_sign_in: TextView? = null
    private var sse_progressBar: ProgressBar? = null
    private var sse_btn_label: TextView? = null
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var alertDialog: AlertDialog
    private lateinit var dialog: Dialog
    private lateinit var viewModel: ViewModel
    private lateinit var countryCodeViewModel: CountryCodeViewModel
    private lateinit var validator: Validator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_email)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
        // initCodeViewModel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
        finish()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        sse_back = findViewById(R.id.sse_back)
        sse_email = findViewById(R.id.sse_email)
        sse_password = findViewById(R.id.sse_password)
        sse_confirm_password = findViewById(R.id.sse_confirm_password)
        sse_show_password_1 = findViewById(R.id.sse_show_password_1)
        sse_show_password_2 = findViewById(R.id.sse_show_password_2)
        sse_create_account = findViewById(R.id.sse_create_account)
        sse_create_email = findViewById(R.id.sse_create_email)
        sse_prenup = findViewById(R.id.sse_prenup)
        sse_sign_in = findViewById(R.id.sse_sign_in)
        sse_progressBar = findViewById(R.id.sse_progressBar)
        sse_btn_label = findViewById(R.id.sse_btn_label)
    }

    private fun initializeLogic() {
        sse_back?.setOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            finish()
        }

        sse_email?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun afterTextChanged(editable: Editable) {
                val currentLength = editable.length
                // scp_length?.text = "$currentLength/emailAddressLimit"
                // Check if the length of the text exceeds the character limit
                if (editable.length > emailAddressLimit) {
                    // If it exceeds the limit, truncate the text
                    sse_email?.setText(editable.subSequence(0, emailAddressLimit))
                    sse_email?.setSelection(emailAddressLimit) // Move cursor to the end
                }
            }
        })

        sse_password?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun afterTextChanged(editable: Editable) {
                val currentLength = editable.length
                // scp_length?.text = "$currentLength/$passwordLimit"
                // Check if the length of the text exceeds the character limit
                if (editable.length > passwordLimit) {
                    // If it exceeds the limit, truncate the text
                    sse_password?.setText(editable.subSequence(0, passwordLimit))
                    sse_password?.setSelection(passwordLimit) // Move cursor to the end
                }
            }
        })

        sse_confirm_password?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun afterTextChanged(editable: Editable) {
                val currentLength = editable.length
                // scp_length?.text = "$currentLength/confirmPasswordLimit"
                // Check if the length of the text exceeds the character limit
                if (editable.length > confirmPasswordLimit) {
                    // If it exceeds the limit, truncate the text
                    sse_confirm_password?.setText(editable.subSequence(0, confirmPasswordLimit))
                    sse_confirm_password?.setSelection(confirmPasswordLimit) // Move cursor to the end
                }
            }
        })
        // helper.intent(FullName::class.java,R.anim.right_in,R.anim.left_out)
        sse_create_email?.setOnClickListener {
            sse_email?.let { it1 ->
                sse_password?.let { it2 ->
                    sse_confirm_password?.let { it3 ->
                        presenter.createEmail(
                            it1.text.toString(),
                            it2.text.toString(),
                            it3.text.toString()
                        )
                    }
                }
            }
        }

        sse_show_password_1?.setOnClickListener {
            sse_password?.let { pass ->
                sse_show_password_1?.let { eye ->
                    presenter.showPassword(pass, eye)
                }
            }
        }
        sse_show_password_2?.setOnClickListener {
            sse_confirm_password?.let { pass ->
                sse_show_password_2?.let { eye ->
                    presenter.showPassword(pass, eye)
                }
            }
        }

        sse_password?.addTextChangedListener(object : TextWatcher {
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

            override fun afterTextChanged(param1: Editable) {

            }
        })

        sse_confirm_password?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
                presenter.onConfirmPasswordTextChanged(param1)
            }

            override fun beforeTextChanged(
                param1: CharSequence,
                param2: Int,
                param3: Int,
                param4: Int
            ) {
            }

            override fun afterTextChanged(param1: Editable) {

            }
        })

        textviewMH(
            sse_prenup!!,
            "By creating an account, you agree to our Terms , Data Policy and Cookies Policy"
        )
        edittextMH(sse_prenup!!)
        textviewMH(sse_sign_in!!, "Already have an account? Sign In")
        edittextMH(sse_sign_in!!)
        terms = """
            **SpotD Mobile App Terms and Conditions**
            
            Last Updated: [Date]
            
            Welcome to SpotD! These Terms and Conditions outline the rules and guidelines for using the SpotD mobile application. By using the SpotD app, you agree to these terms. If you do not agree with any part of these terms, please do not use the app.
            
            **User Conduct**
            
            1. **Age Requirement**: You must be at least [minimum age] years old to use the SpotD app. If you are under the specified age, please refrain from using the app.
            
            2. **Content Guidelines**: You are solely responsible for the content you post on SpotD. You must not post, share, or distribute content that is offensive, inappropriate, harmful, or violates any laws or regulations.
            
            3. **Privacy**: You agree to abide by the SpotD Data Policy, which outlines how your personal data is collected, used, and protected within the app.
            
            **Intellectual Property**
            
            1. **Ownership**: The SpotD app and its content are owned by [Your Company Name] and are protected by intellectual property laws. You must not use, copy, or distribute any part of the app without our explicit permission.
            
            2. **User Content**: By posting content on SpotD, you grant [Your Company Name] a non-exclusive, worldwide, royalty-free license to use, reproduce, modify, and distribute the content for app-related purposes.
            
            **Limitations of Liability**
            
            1. **Use at Your Own Risk**: You use the SpotD app at your own risk. We do not guarantee that the app will be error-free, secure, or always available.
            
            2. **Third-Party Services**: The app may include links to third-party websites or services. We are not responsible for the content or practices of these third parties.
            
            **Termination**
            
            1. **Suspension and Termination**: We reserve the right to suspend or terminate your access to the SpotD app at our discretion, especially if you violate these terms.
            
            **Changes to Terms**
            
            We may update these Terms and Conditions from time to time. Any changes will be effective when posted within the SpotD app. It is your responsibility to review these terms periodically.
            
            **Governing Law**
            
            These Terms and Conditions are governed by and construed in accordance with the laws of [Your Country/State]. Any disputes arising from or related to these terms will be subject to the exclusive jurisdiction of the courts in [Your Jurisdiction].
            
            **Contact Us**
            
            If you have any questions or concerns about these Terms and Conditions or how they apply to the SpotD app, please contact us at mail@spotd.me .
            
            By using the SpotD app, you agree to abide by these Terms and Conditions.
            
            [End of Terms and Conditions]
            """.trimIndent()
        data = "Spotd Mobile App Data Policy\n" +
                "\n" +
                "Last Updated: [Date]\n" +
                "\n" +
                "Welcome to Spotd! This Data Policy outlines how we collect, use, and protect your personal data when you use the Spotd mobile application. Your privacy is important to us, and we are committed to safeguarding your information.\n" +
                "\n" +
                "Information We Collect\n" +
                "\n" +
                "User Account Data: When you create a Spotd account, we may collect your username, email address, and password.\n" +
                "\n" +
                "Profile Information: You have the option to provide additional information for your Spotd profile, such as a profile picture, display name, and any other details you choose to share.\n" +
                "\n" +
                "Usage Data: We collect data related to your interactions with the Spotd app, including the content you post, the friends you connect with, and your activity within the app.\n" +
                "\n" +
                "Device Information: We collect device-specific information, including your device type, operating system, unique device identifiers, and mobile network information.\n" +
                "\n" +
                "Location Information: With your permission, we may only collect your country code to provide location-based features within the Spotd app.\n" +
                "\n" +
                "How We Use Your Data\n" +
                "\n" +
                "Provide and Improve the App: We use your data to deliver the Spotd app's features and services, and to continually improve your app experience.\n" +
                "\n" +
                "Personalization: We may use your data to personalize your content and interactions within the app, such as showing you relevant posts and suggestions.\n" +
                "\n" +
                "Communication: We may send you notifications and updates related to your Spotd account and activities.\n" +
                "\n" +
                "Analytics: We use data analytics to understand app usage patterns and to enhance the app's functionality.\n" +
                "\n" +
                "Data Protection and Sharing\n" +
                "\n" +
                "Security: We employ industry-standard security measures to protect your data from unauthorized access, loss, or misuse.\n" +
                "\n" +
                "Third Parties: We do not sell your personal data to third parties. However, we may share certain data with service providers who assist us in app functionality, analytics, and marketing.\n" +
                "\n" +
                "Your Choices\n" +
                "\n" +
                "Account Settings: You can update your account information and preferences within the Spotd app settings.\n" +
                "\n" +
                "Data Access and Deletion: You can access to the data we hold about you or you can delete your account and associated data by deleting account for 15days.\n" +
                "\n" +
                "Children's Privacy\n" +
                "\n" +
                "The Spotd app is not intended for users under the age of 13. We do not knowingly collect or store personal data from children under this age. If you are a parent or guardian and believe we have collected data from a child, please contact us immediately.\n" +
                "\n" +
                "**Updates to this Policy**\n" +
                "\n" +
                "We may update this Data Policy to reflect changes in our practices or regulations. Any updates will be available within the Spotd app. We recommend reviewing this policy periodically to stay informed about how your data is handled.\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "If you have any questions or concerns about our Data Policy or how your data is used in the Spotd app, please contact us at mail@spotd.me .\n" +
                "\n" +
                "By using the \b Spotd app, you agree to the data practices outlined in this policy.\n" +
                "\n" + "[End of Data Policy]"
        cookies = """
            Spotd Mobile App Cookie Policy
            
            Last Updated: August 5 2023
            
            Welcome to Spotd! This Cookie Policy explains how we use cookies and similar tracking technologies in the Spotd mobile application. By using the Spotd app, you consent to the use of cookies as described in this policy.
            
            What are Cookies?
            
            Cookies are small pieces of data stored on your mobile device when you use the Spotd app. They are used to enhance your app experience and provide personalized features. Cookies can remember your preferences, track your activities within the app, and help us analyze how you interact with our content.
            
            Types of Cookies We Use
            
            Necessary Cookies: These cookies are crucial for the basic functionality of the Spotd app. They allow you to navigate the app, access secure areas, and use essential features. Without these cookies, certain services may not be available.
            
            Preference Cookies: Preference cookies remember your choices and settings within the app. They enhance your experience by providing a personalized session tailored to your preferences.
            
            Analytics Cookies: We use analytics cookies to gather information about how you use the Spotd app. This data helps us understand user behavior, improve app performance, and enhance the overall user experience. These cookies may be provided by third-party analytics services.
            
            Advertising Cookies: Advertising cookies track your interactions with the Spotd app and may be used to show you relevant advertisements. These cookies are often set by third-party advertising networks to deliver targeted content based on your interests.
            
            Managing Cookies
            
            You can manage cookie preferences within the Spotd app settings. You may choose to accept or decline cookies. Keep in mind that disabling cookies may affect certain features and functionalities of the app.
            
            Third-Party Cookies
            
            Some cookies used in the Spotd app may come from third-party services, such as analytics providers and advertisers. We do not have control over these cookies. For more information about these third-party cookies, please refer to their respective privacy policies.
            
            Updates to this Policy
            
            We may update this Cookie Policy as needed to reflect changes in technology, regulations, or our practices. Any updates will be made available within the app. We recommend reviewing this policy periodically to stay informed about how cookies are used in the Spotd app.
            
            Contact Us
            
            If you have any questions or concerns about our Cookie Policy or the use of cookies in the Spotd app, please contact us at main@spotd.me
            
            By using the Spotd app, you agree to the use of cookies as outlined in this policy.
            
            [End of Cookie Policy]
            """.trimIndent()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getCreateEmailObserver().observe(this, Observer<Response?> { userResponse ->
            userResponse?.let {
                val status = it.status
                val uid = it.uid
                Log.i("Status for Email", "Received status: $status")
                Log.i("UID for Email", "Received uid: $uid")
                loading(false)
                if (status != null) {
                    when (status) {
                        // for bad request
                        "16400" -> {
                            dialogHandling(7)
                        }
                        // for already exist
                        "16409" -> {
                            dialogHandling(7)
                        }
                        // for unauthorized token
                        "16401" -> {
                            dialogHandling(9)
                        }
                        // for Method Not Allowed
                        "16405" -> {
                            dialogHandling(0)
                        }
                        // Service Unavailable
                        "16503" -> {
                            dialogHandling(10)
                        }
                        // error occurred
                        else -> {
                            dialogHandling(0)
                        }
                    }
                    return@Observer
                }
                if (uid!!.isNotEmpty() && uid.length == 17){
                    saveString(this@Email,KEY.UID,uid)
                    helper.intent(FullName::class.java,R.anim.right_in,R.anim.left_out)
                }
            }
        })
    }

    private fun initCodeViewModel() {
        countryCodeViewModel.fetchCountryDetails()
        countryCodeViewModel = ViewModelProvider(this).get(CountryCodeViewModel::class.java)
        countryCodeViewModel.countryDetailsLiveData.observe(this, Observer { countryCode ->
            countryCode?.let {
                CountryCode = countryCode
            } ?: run {
                CountryCode = "null"
            }
        })
    }

    override fun signUpEmail() {
        val request = Request()
        request.apply {
            email = sse_email?.text.toString()
            password = sse_password?.text.toString()
            uuid = helper.genUUID()
            code = loadString(this@Email, KEY.USER_DEFAULT_COUNTRY_CODE, "")
        }
        viewModel.userCreateEmail(request)
    }

    fun textviewMH(txt: TextView, value: String?) {
        txt.movementMethod = LinkMovementMethod.getInstance()
        updateSpan(value.toString(), txt)
    }

    private fun updateSpan(str: String, txt: TextView) {
        val ssb = SpannableString(str)
        val pattern =
            Pattern.compile("(?<![^\\s])(([Terms]{5}|[Cookies Policy]{14}|[Data Policy]{11}|[Sign In]{7})(\\.?)+)(?![^\\s,])")
        val matcher = pattern.matcher(str)
        while (matcher.find()) {
            val span = ProfileSpan()
            ssb.setSpan(span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        txt.text = ssb
    }

    inner class ProfileSpan : ClickableSpan() {
        override fun onClick(view: View) {
            if (view is TextView) {
                val tv = view
                if (tv.text is Spannable) {
                    val sp = tv.text as Spannable
                    val start = sp.getSpanStart(this)
                    val end = sp.getSpanEnd(this)
                    object_clicked = sp.subSequence(start, end).toString()
                    if (object_clicked == "Sign In") {
                        helper.intent(SignIn::class.java, R.anim.left_in, R.anim.right_out)
                        // i.setClass(this@Email, LoggedInto::class.java)
                        // startActivity(i)
                        // overridePendingTransition(R.anim.left_in, R.anim.right_out)
                    }
                    if (object_clicked == "Terms") {
                        // block.description(this@Email, "Terms", "developer team", terms, "")
                    }
                    if (object_clicked == "Data Policy") {
                        // block.description(this@Email, "Data Policy", "Developer team", data, "")
                    }
                    if (object_clicked == "Cookies Policy") {
                        // block.description(
                        //     this@Email,
                        //     "Cookies Policy",
                        //     "Developer team",
                        //     cookies,
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

    fun edittextMH(_txt: TextView) {
        val regex1 = TextView(this)
        regex1.text =
            "(?<![^\\s])(([T]{1}|[C]{1}|[P]{1}|[D]{1}|[S]{1})([A-Za-z0-9_-]\\.?)+)(?![^\\s,])"
        val mentionColor = "#FFD700"
        _txt.addTextChangedListener(object : TextWatcher {
            var keywords1 =
                ColorScheme(Pattern.compile(regex1.text.toString()), Color.parseColor(mentionColor))
            val schemes = arrayOf(keywords1)
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                removeSpans(s, ForegroundColorSpan::class.java)
                for (scheme in schemes) {
                    val m = scheme.pattern.matcher(s)
                    while (m.find()) {
                        s.setSpan(
                            ForegroundColorSpan(scheme.color),
                            m.start(),
                            m.end(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        s.setSpan(
                            StyleSpan(Typeface.BOLD),
                            m.start(),
                            m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }

            private fun removeSpans(e: Editable, type: Class<*>) {
                val spans: Array<out Any?> = e.getSpans(0, e.length, type)
                for (span in spans) {
                    e.removeSpan(span)
                }
            }
            // fun removeSpans(e: Editable, type: Class<*>) {
            //     val spans = e.getSpans<Any?>(0, e.length, type) as Array<CharacterStyle?>
            //     for (span in spans) {
            //         e.removeSpan(span)
            //     }
            // }

            inner class ColorScheme(val pattern: Pattern, val color: Int) {
            }
        })
    }

    override fun showPasswordVisibility(visible: Boolean) {
        sse_show_password_1?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showConfirmPasswordVisibility(visible: Boolean) {
        sse_show_password_2?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun loading(boolean: Boolean){
        helper.loading(sse_btn_label!!,sse_progressBar!!,boolean)
    }
    override fun dialogHandling(num: Int) {
        when (num) {

            1 -> dialog.showDialog(
                "Warning",
                "Your email is required.",
                "Ok",
                ""
            )

            2 -> dialog.showDialog(
                "Warning",
                "Your password is required.",
                "Ok",
                ""
            )

            3 -> dialog.showDialog(
                "Warning",
                "Your confirm password is required.",
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
                "Your password and your confirmed password don't match. Please try again.",
                "Ok",
                ""
            )

            6 -> dialog.showDialog(
                "Warning",
                "Please enter a valid email address.",
                "Ok",
                ""
            )
            7 -> dialog.showDialog(
                "Warning",
                "Your email address are already exist \"${sse_email?.text.toString()}\". Please use another email address and please try again. ",
                "Ok",
                ""
            )

            8 -> dialog.showDialog(
                "Warning",
                "Your email \"${sse_email?.text.toString()}\" contains illegal characters. Please try again.",
                "Ok",
                ""
            )
            9 -> dialog.showDialog(
                "Warning",
                "Unauthorized",
                "Ok",
                ""
            )
            10 -> dialog.showDialog(
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