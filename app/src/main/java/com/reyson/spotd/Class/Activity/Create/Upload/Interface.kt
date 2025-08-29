package com.reyson.spotd.Class.Activity.Create.Upload

import android.widget.ImageView
import android.widget.TextView
import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun getFileExtension(filePath: String?): String?
        fun dialogHandling(num: Int)
    }
    interface Presenter: Interface.Presenter{

    }
}