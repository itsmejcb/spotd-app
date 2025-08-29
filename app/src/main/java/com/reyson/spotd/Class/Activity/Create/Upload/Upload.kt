package com.reyson.spotd.Class.Activity.Create.Upload

import android.Manifest
import android.content.pm.PackageManager
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
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.reyson.spotd.Class.Activity.Create.Select.Select
import com.reyson.spotd.Class.Activity.Create.Setting.Setting
import com.reyson.spotd.Class.Activity.Create.Upload.Model.Request
import com.reyson.spotd.Class.Activity.Home.Home
import com.reyson.spotd.Class.Activity.SignIn.SignIn
import com.reyson.spotd.Class.Components.Adapter.ImageAdapter
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Model.Image
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.GlobalData
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadImageArray
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.Calendar
import java.util.regex.Pattern

class Upload : AppCompatActivity(), Interface.View {
    private var object_clicked = ""
    private var imageAdapter: ImageAdapter? = null
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private var scp_recyclerview: RecyclerView? = null
    private var scp_post: TextView? = null
    private var scp_profile: ImageView? = null
    private var scp_add: ImageView? = null
    private var scp_back: ImageView? = null
    private var scp_setting: ImageView? = null
    private var scp_caption: EditText? = null
    private var scp_username: TextView? = null
    private var scp_length: TextView? = null
    private var scp_full_name: TextView? = null
    private var scp_create_post: TextView? = null
    private var imgs = ArrayList<Image>()
    private val characterLimit = 500
    private var imageLabeler: ImageLabeler? = null
    private var alertDialog: AlertDialog? = null
    private lateinit var viewModel: ViewModel
    private val cal = Calendar.getInstance()
    private val ms = cal.timeInMillis.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        initialize(savedInstanceState)
        event(savedInstanceState)
        initializeLogic()
        initViewModel()
        imageLabeler = ImageLabeling.getClient(
            ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.7f)
                .build()
        )

    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        scp_recyclerview = findViewById<RecyclerView>(R.id.scp_recyclerview)
        scp_post = findViewById<TextView>(R.id.scp_post)
        scp_profile = findViewById<ImageView>(R.id.scp_profile)
        scp_caption = findViewById<EditText>(R.id.scp_caption)
        scp_length = findViewById<TextView>(R.id.scp_length)
        scp_add = findViewById<ImageView>(R.id.scp_add)
        scp_setting = findViewById<ImageView>(R.id.scp_setting)
        scp_username = findViewById<TextView>(R.id.scp_username)
        scp_create_post = findViewById<TextView>(R.id.scp_create_post)
        scp_full_name = findViewById<TextView>(R.id.scp_full_name)
        scp_back = findViewById<ImageView>(R.id.scp_back)
    }

    private fun event(savedInstanceState: Bundle?) {
        scp_setting?.setOnClickListener {
            helper.intent(Setting::class.java, R.anim.right_in, R.anim.left_out)
        }
        scp_caption?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // Not needed for character limit enforcement
            }

            override fun afterTextChanged(editable: Editable) {
                val currentLength = editable.length
                scp_length?.text = "$currentLength/$characterLimit"
                // Check if the length of the text exceeds the character limit
                if (editable.length > characterLimit) {
                    // If it exceeds the limit, truncate the text
                    scp_caption?.setText(editable.subSequence(0, characterLimit))
                    scp_caption?.setSelection(characterLimit) // Move cursor to the end
                }
            }
        })

        scp_post?.setOnClickListener(View.OnClickListener {
            val arrayList = imgs
            if (containsNSFW(arrayList)) {
                val dialogView: View = LayoutInflater.from(this)
                    .inflate(R.layout.custom_dialog_message, null)
                val dialogMessageTextView =
                    dialogView.findViewById<TextView>(R.id.sdm_message)
                val message =
                    "The terms of service of the social media platform in question, as well as any applicable laws and regulations, will usually govern punishments for publishing sexual content. Depending on the seriousness of the offense and the rules of the platform, these penalties may differ. Learn more "
                textviewMh(dialogMessageTextView, message)
                val builder =
                    AlertDialog.Builder(this)
                builder.setTitle("Warning")
                    .setView(dialogView)
                    .setPositiveButton(
                        "OK"
                    ) { dialog, which ->
                        // Handle OK button click
                        dialog.dismiss() // Close the dialog
                    }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialog, which ->
                        // Handle Cancel button click
                        dialog.dismiss() // Close the dialog
                    }
                alertDialog = builder.create()
                alertDialog!!.show()
                Log.i(
                    "ArrayList Status",
                    "This ArrayList contains NSFW images."
                )
            } else {
                Log.i(
                    "ArrayList Status",
                    "This ArrayList contains only SFW images."
                )

                for (image in imgs) {

                    Log.i(
                        "ArrayList data",
                        image.image_name.toString() + "\n"
                                + image.path.toString() + "\n"
                                + image.size
                    )
                }
                val request = Request()
                request.apply {
                    val imageNamesBuilder1 = StringBuilder()
                    val imageNamesBuilder2 = StringBuilder()
                    for (image in imgs) {
                        if (imageNamesBuilder1.isNotEmpty()) {
                            imageNamesBuilder1.append(",")
                        }
                        if (imageNamesBuilder2.isNotEmpty()) {
                            imageNamesBuilder2.append(",")
                        }
                        imageNamesBuilder1.append(image.image_name)
                        imageNamesBuilder2.append(image.status)

                    }
                    uid = SharedPreferencesUtils.loadString(this@Upload, KEY.UID, "")
                    caption = scp_caption?.text.toString()
                    imageName = imageNamesBuilder1.toString()
                    imageStatus = imageNamesBuilder2.toString()
                    timestamp = ms
                    status = SharedPreferencesUtils.loadString(this@Upload, KEY.SET_POST_STATUS, "")
                    commentStats = SharedPreferencesUtils.loadString(this@Upload, KEY.SET_COMMENT_STATUS, "")
                }
                viewModel.userCreatePost(request)
                Log.d("ResponseDebug", request.toString())

                // presenter.PostContent(scp_caption.getText().toString(), ms, imgs)
            }
        })

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getCreatePostObserver().observe(
            this,
            Observer<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response?> { userResponse ->
                val responseBody = userResponse.toString()
                // Log.d("ResponseDebug", responseBody)
                userResponse?.let { it ->
                    val status = it.status
                    val pushKey = it.push_key
                    Log.i("Status", "Received status: $status")
                    val responseBody = userResponse.toString()

                    // Log the response body for debugging
                    // Log.d("ResponseDebug", responseBody)
                    if (status != null) {
                        when (status) {
                            "16201" -> {
                                uploadImage(pushKey.toString())
                            }
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
                    }
                }
            })
    }

    private fun containsNSFW(arrayList: java.util.ArrayList<Image>): Boolean {
        for (image in arrayList) {
            if (image.status == "nsfw") {
                return true // If any image is NSFW, return true
            }
        }
        return false // If no image is NSFW, return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
        finish()
        imgs.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun getFileExtension(filePath: String?): String? {
        return if (filePath != null && filePath.lastIndexOf(".") != -1) {
            "." + filePath.substring(filePath.lastIndexOf(".") + 1)
        } else {
            "" // No file extension found
        }
    }

    fun loadProfile(): String? {
        var lastImagePath: String? = null
        val receivedDataList: ArrayList<Image> = GlobalData.getInstance().getDataList()
        // Iterate through the loadedImageList and use the data
        for (image in receivedDataList) {
            val imageUrl = image.path
            lastImagePath = imageUrl
        }
        return lastImagePath
    }

    fun loadImage(view: RecyclerView?, images: ArrayList<Image>?) {
        imageAdapter = ImageAdapter(this@Upload, images!!)
        view?.adapter = imageAdapter
        val layoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)
        view?.layoutManager = layoutManager
        view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val previousVisibleItem = intArrayOf(0)
            var swipeCount = intArrayOf(0)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = intArrayOf(layoutManager.childCount)
                val totalItemCount = intArrayOf(layoutManager.itemCount)
                val firstVisibleItem = intArrayOf(layoutManager.findFirstVisibleItemPosition())
                if (visibleItemCount[0] > previousVisibleItem[0]) {
                    swipeCount[0]++
                    Log.d("Swipe count", "Swipe count FORWARD: " + swipeCount[0])
                }
                previousVisibleItem[0] = visibleItemCount[0]
            }
        })
        val animation: Animation = AnimationUtils.loadAnimation(view?.context, R.anim.fade_in)
        // Remove the current OnFlingListener before setting a new one
        if (view?.onFlingListener != null) {
            view?.onFlingListener = null
        }

        // Create a new instance of OnFlingListener and set it on the RecyclerView
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view)
    }

    private fun initializeLogic() {
        imgs = loadImageArray(applicationContext, "list_photos")

        if (SharedPreferencesUtils.loadString(this@Upload, KEY.SET_POST_STATUS, "").isEmpty()
            ) {
            // set post as public
            SharedPreferencesUtils.saveString(this@Upload, KEY.SET_POST_STATUS, "1")
        }
        if (SharedPreferencesUtils.loadString(this@Upload, KEY.SET_COMMENT_STATUS, "").isEmpty()
            ) {
            // set comment section as public
            SharedPreferencesUtils.saveString(this@Upload, KEY.SET_COMMENT_STATUS, "0")
        }
        scp_username?.text = SharedPreferencesUtils.loadString(this, KEY.PROFILE_PICTURE, "")
        scp_full_name?.text = SharedPreferencesUtils.loadString(this, KEY.PROFILE_PICTURE, "")

        val profileImageURL = SharedPreferencesUtils.loadString(this, KEY.PROFILE_PICTURE, "")
        if (profileImageURL.isNotEmpty()) {
            try {
                Picasso.get()
                    .load(profileImageURL)
                    .transform(CropCircleTransformation())
                    .placeholder(R.drawable.img_logo)
                    .into(scp_profile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Picasso.get().load(R.drawable.img_logo).placeholder(R.drawable.img_logo)
                .into(scp_profile)
        }

        scp_add?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this@Upload,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
                return@setOnClickListener
            } else {
                helper.intent(Select::class.java, R.anim.right_in, R.anim.left_out)

            }
        }
        if (imgs.size > 0) {
            loadImage(scp_recyclerview, imgs)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                helper.intent(Select::class.java, R.anim.right_in, R.anim.left_out)
            } else {
                // Handle permission denied

            }
        }
    }

    fun textviewMh(txt: TextView, value: String?) {
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

    fun edittext_mh(_txt: TextView) {
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


            inner class ColorScheme(val pattern: Pattern, val color: Int) {
            }
        })
    }

    fun uploadImage(key: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var imagename: String
            for (imageMap in imgs) {
                val path = imageMap.path
                val filename = imageMap.image_name
                val extension = imageMap.extension
                val lastDotIndex = filename!!.lastIndexOf('.')
                if (lastDotIndex >= 0) {
                    imagename = filename!!.substring(
                        0,
                        lastDotIndex
                    ) + filename!!.substring(lastDotIndex + 1)
                }
                try {
                    if (path != null) {
                        val file = File(path)
                        if (file.exists()) {
                            val inputStream: InputStream = FileInputStream(file)

                            // Convert the input stream to a byte array
                            val byteBuffer = ByteArrayOutputStream()
                            val buffer = ByteArray(1024)
                            var len: Int
                            while (inputStream.read(buffer).also { len = it } != -1) {
                                byteBuffer.write(buffer, 0, len)
                            }
                            val imageData = byteBuffer.toByteArray()
                            val client = OkHttpClient()
                            val file = File(loadProfile())
                            val fileBytes = file.readBytes()
                            val requestBody =
                                fileBytes.toRequestBody("application/octet-stream".toMediaType())
                            val url: String =
                                Access.URL_BUCKET_POST +
                                        SharedPreferencesUtils.loadString(
                                            this@Upload,
                                            KEY.UID,
                                            ""
                                        ) +
                                        "/" + "$key" + "/" +
                                        filename
                            val request = okhttp3.Request.Builder()
                                .url(url)
                                .put(requestBody)
                                .header("AccessKey", Access.FULL_ACCESS)
                                .header("content-type", "image/jpeg")
                                .build()

                            // Inside your network call
                            try {
                                val response = client.newCall(request).execute()
                                if (response.isSuccessful) {
                                    Log.i("Upload Status", "Code: $response")
                                    // Image uploaded successfully - handle success here
                                    helper.intent(
                                        Home::class.java,
                                        R.anim.left_in,
                                        R.anim.right_out
                                    )
                                } else {
                                    // Handle the failure
                                    dialogHandling(0)
                                }
                            } catch (e: IOException) {
                                // Handle exceptions or errors
                                Log.e("UploadError", "Exception: ${e.message}")

                            }
                        } else {
                            Log.e("UploadImage", "File does not exist: $path")
                        }
                    } else {
                        Log.e("UploadImage", "Image path is null")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun dialogHandling(num: Int) {
        when (num) {

            1 -> dialog.showDialog(
                "Warning",
                "Your file is to large.\n" +
                        " Maximum 5mb per upload.",
                "Ok",
                ""
            )

            1 -> dialog.showDialog(
                "Warning",
                "Sorry, we encountered an issue while uploading your photo. Please make sure the file you are attempting to upload meets the following requirements:\n" +
                        "\"• Supported Formats: We only accept JPEG and PNG files.\n" +
                        "\"• File Size: The file size should not exceed 5MB.",
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