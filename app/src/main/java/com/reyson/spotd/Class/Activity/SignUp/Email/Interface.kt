package com.reyson.spotd.Class.Activity.SignUp.Email

import android.widget.EditText
import android.widget.ImageView
import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun showPasswordVisibility(visible: Boolean)
        fun showConfirmPasswordVisibility(visible: Boolean)
        fun dialogHandling(num: Int)
        fun signUpEmail()
        fun loading(boolean: Boolean)
    }
    interface Presenter: Interface.Presenter{

        fun showPassword(ePassword: EditText, img: ImageView)
        fun onPasswordTextChanged(charSequence: CharSequence)
        fun onConfirmPasswordTextChanged(charSequence: CharSequence)
        fun createEmail(key1: String, key2: String, key3: String)
    }
}