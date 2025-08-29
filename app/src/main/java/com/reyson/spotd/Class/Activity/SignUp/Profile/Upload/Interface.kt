package com.reyson.spotd.Class.Activity.SignUp.Profile.Upload

import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun getFileExtension(filePath: String?): String?
        fun dialogHandling(num: Int)
        fun loading(boolean: Boolean)
    }
    interface Presenter: Interface.Presenter{

    }
}