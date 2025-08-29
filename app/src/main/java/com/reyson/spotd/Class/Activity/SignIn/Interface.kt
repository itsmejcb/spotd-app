package com.reyson.spotd.Class.Activity.SignIn

import android.widget.EditText
import android.widget.ImageView
import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun showPasswordVisibility(visible: Boolean)
        fun signIn()
        fun dialogHandling(num: Int, ms: String?)
    }
    interface Presenter: Interface.Presenter{

        fun showPassword(ePassword: EditText, img: ImageView)
        fun onPasswordTextChanged(charSequence: CharSequence)
        fun signIn(key1: String, key2: String)
    }
}