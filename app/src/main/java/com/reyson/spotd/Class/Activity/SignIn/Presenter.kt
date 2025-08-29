package com.reyson.spotd.Class.Activity.SignIn


import android.content.Context
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.R


class Presenter(view: Interface.View, context: Context) : Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    private var isPasswordVisible = false

    init {
        helper = Helper(context)
    }

    override fun onPasswordTextChanged(charSequence: CharSequence) {
        if (charSequence.isNotEmpty()) {
            view.showPasswordVisibility(true)
        } else {
            view.showPasswordVisibility(false)
        }
    }

    override fun showPassword(ePassword: EditText, img: ImageView) {
        isPasswordVisible = !isPasswordVisible // Toggle the password visibility state
        if (isPasswordVisible) {
            ePassword.transformationMethod = null
            img.setImageResource(R.drawable.img_eye_open)
        } else {
            ePassword.transformationMethod = PasswordTransformationMethod()
            img.setImageResource(R.drawable.img_eye_close)
        }
        ePassword.setSelection(ePassword.text.length)
    }

    override fun signIn(key1: String,key2: String) {
        var illegalStringDetected = false

        if (TextUtils.isEmpty(key1)) {
            view.dialogHandling(3, "")
            return
        }
        if (TextUtils.isEmpty(key2)) {
            view.dialogHandling(2, "")
            return
        }
        if (key2.length <= 7){
            view.dialogHandling(2, "")
            return
        }
        if (!helper.validate(key1,1)){
            view.dialogHandling(6, "")
            illegalStringDetected = true
        }

        if (!illegalStringDetected) {
            view.signIn()
        }
    }
}