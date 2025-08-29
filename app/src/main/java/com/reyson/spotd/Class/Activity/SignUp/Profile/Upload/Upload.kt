package com.reyson.spotd.Class.Activity.SignUp.Profile.Upload

// import com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Request
// import java.io.IOException
// import okhttp3.MediaType.Companion.toMediaTypeOrNull
// import okhttp3.OkHttpClient
// import okhttp3.RequestBody

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reyson.spotd.Class.Activity.Home.Home
import com.reyson.spotd.Class.Activity.SignUp.Profile.Select.Select
import com.reyson.spotd.Class.Components.Block.Dialog
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Model.Image
import com.reyson.spotd.Data.Api.Access
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.Model.GlobalData
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils.loadString
import com.reyson.spotd.R
import com.reyson.spotd.ViewModel.ViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Calendar


class Upload : AppCompatActivity(), Interface.View {

    private var ssp_profile: ImageView? = null
    private var ssp_uplaod: LinearLayout? = null
    private var ssp_label_1: TextView? = null
    private var ssp_username: TextView? = null
    private var ssp_full_name: TextView? = null
    private var ssp_skip: TextView? = null
    private var ssp_progressBar: ProgressBar? = null
    private lateinit var presenter: Presenter
    private lateinit var helper: Helper
    private lateinit var dialog: Dialog
    private var alertDialog: AlertDialog? = null
    private lateinit var viewModel: ViewModel
    private val cal = Calendar.getInstance()
    private val ms = cal.timeInMillis.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_profile_picture)
        initialize(savedInstanceState)
        initializeLogic()
        initViewModel()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this, this)
        helper = Helper(this)
        dialog = Dialog(this)
        ssp_profile = findViewById(R.id.ssp_profile)
        ssp_uplaod = findViewById(R.id.ssp_uplaod)
        ssp_username = findViewById(R.id.ssp_username)
        ssp_full_name = findViewById(R.id.ssp_full_name)
        ssp_label_1 = findViewById(R.id.ssp_label_1)
        ssp_skip = findViewById(R.id.ssp_skip)
        ssp_progressBar = findViewById(R.id.ssp_progressBar)
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getCreateProfilePictureObserver().observe(
            this,
            Observer<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response?> { userResponse ->
                userResponse?.let {
                    val status = it.status
                    Log.i("Status", "Received status: $status")
                    when (status) {
                        // TODO code status here
                        "16201" -> {
                            uploadImage()
                        }
                        // for unauthorized token
                        "16401" -> {
                            dialogHandling(3)
                        }
                        // for Method Not Allowed
                        "16405" -> {
                            dialogHandling(0)
                        }
                        // for Not Implemented
                        "16503" -> {
                            dialogHandling(3)
                        }
                        // error occurred
                        else -> {
                            dialogHandling(0)
                        }
                    }
                }
            })
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

    private fun initializeLogic() {
        if (loadString(this, KEY.USERNAME, "").isNotEmpty() && loadString(
                this,
                KEY.FULL_NAME,
                ""
            ).isNotEmpty()
        ) {
            ssp_username?.text = loadString(this, KEY.USERNAME, "")
            ssp_full_name?.text = loadString(this, KEY.FULL_NAME, "")
        } else {
            ssp_username?.text = "null"
            ssp_full_name?.text = "null"
        }
        if (loadProfile() != null) {
            ssp_skip?.visibility = View.GONE
            try {
                Picasso.get()
                    .load("file://" + loadProfile())
                    .transform(CropCircleTransformation())
                    .placeholder(R.drawable.img_logo)
                    .into(ssp_profile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            ssp_skip?.visibility = View.VISIBLE
            Picasso.get().load(R.drawable.img_logo).placeholder(R.drawable.img_logo)
                .into(ssp_profile)
        }
        ssp_profile?.setOnClickListener {
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
        ssp_uplaod?.setOnClickListener {
            ssp_progressBar?.visibility = View.VISIBLE
            ssp_label_1?.visibility = View.GONE
            val request = com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Request()
            request.apply {
                uid = loadString(this@Upload, KEY.UID, "")
                filename = ms
                extension = getFileExtension(loadProfile())
            }
            viewModel.userCreateProfilePicture(request)
            Log.i(
                "data", "uid: " + loadString(this, KEY.UID, "") + "\n" +
                        "extension: " + ms + "\n" +
                        "filename: " + ms + loadString(this, KEY.UID, "") + getFileExtension(
                    loadProfile()
                )
            )
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
                helper.intent(Select::class.java, R.anim.left_in, R.anim.right_out)
            } else {
                // Handle permission denied
            }
        }
    }

    override fun getFileExtension(filePath: String?): String? {
        return if (filePath != null && filePath.lastIndexOf(".") != -1) {
            "." + filePath.substring(filePath.lastIndexOf(".") + 1)
        } else {
            "" // No file extension found
        }
    }

    fun uploadImage() {
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val file = File(loadProfile())
            val fileBytes = file.readBytes()
            val requestBody =
                fileBytes.toRequestBody("application/octet-stream".toMediaType())
            val url: String =
                (Access.URL_BUCKET_PROFILE +
                        loadString(
                            this@Upload,
                            KEY.UID,
                            ""
                        ) +
                        "/" +
                        ms +
                        loadString(
                            this@Upload,
                            KEY.UID,
                            ""
                        )) + getFileExtension(loadProfile())
            val request = Request.Builder()
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
                    helper.intent(Home::class.java, R.anim.left_in, R.anim.right_out)
                } else {
                    // Handle the failure
                    dialogHandling(0)
                }
            } catch (e: IOException) {
                // Handle exceptions or errors
                Log.e("UploadError", "Exception: ${e.message}")

            }
        }
    }

    override fun loading(boolean: Boolean) {
        helper.loading(ssp_label_1!!, ssp_progressBar!!, boolean)
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

            2 -> dialog.showDialog(
                "Warning",
                "Sorry, we encountered an issue while uploading your photo. Please make sure the file you are attempting to upload meets the following requirements:\n" +
                        "\"• Supported Formats: We only accept JPEG and PNG files.\n" +
                        "\"• File Size: The file size should not exceed 5MB.",
                "Ok",
                ""
            )

            3 -> dialog.showDialog(
                "Warning",
                "Unauthorized",
                "Ok",
                ""
            )

            4 -> dialog.showDialog(
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