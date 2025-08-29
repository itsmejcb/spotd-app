package com.reyson.spotd.Class.Activity.Create.Upload

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Data.Api.KEY
import com.reyson.spotd.Data.SharedPreference.SharedPreferencesUtils
import com.reyson.spotd.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.io.File

class Presenter(view: Interface.View, context: Context) : Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    init {
        helper = Helper(context)
    }

    fun uploadProfile(path: String) {
        val size: Int = (File(path).length() / 1024).toInt()
        val fileExtension = view.getFileExtension(path)
        if (size > 5000) {
            view.dialogHandling(1)
            return
        }
        if (fileExtension != ".jpeg" && fileExtension != ".jpg" && fileExtension != ".png" && fileExtension != ".JPEG" && fileExtension != ".JPG" && fileExtension != ".PNG") {
            // if (!fileExtension.equals("jpeg") && !fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("JPEG") && !fileExtension.equals("JPG") && !fileExtension.equals("PNG")) {
            view.dialogHandling(2)
            return
        }
    }
}