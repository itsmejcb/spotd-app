package com.reyson.spotd.Class.Activity.SignUp.Username

import android.widget.EditText
import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun signUpUsername()
        fun dialogHandling(num: Int)
        fun loading(boolean: Boolean)
    }
    interface Presenter: Interface.Presenter{

        fun createUsername(key: String)
    }
}