package com.reyson.spotd.Class.Activity.SignUp.Email

import android.content.Context
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.Validator
import com.reyson.spotd.R

class Presenter (view: Interface.View, context: Context): Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    private var validator: Validator
    private var isPasswordVisible = false

    init {
        helper = Helper(context)
        validator = Validator(context)
        validator.loadHarmfulWords()
    }

    override fun onPasswordTextChanged(charSequence: CharSequence) {
        if (charSequence.isNotEmpty()) {
            view.showPasswordVisibility(true)
        } else {
            view.showPasswordVisibility(false)
        }
    }

    override fun onConfirmPasswordTextChanged(charSequence: CharSequence) {
        if (charSequence.isNotEmpty()) {
            view.showConfirmPasswordVisibility(true)
        } else {
            view.showConfirmPasswordVisibility(false)
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

    override fun createEmail(key1: String, key2: String, key3: String) {
        var illegalStringDetected = false
        view.loading(true)

        if (TextUtils.isEmpty(key1)) {
            view.loading(false)
            view.dialogHandling(1)
            return
        }
        if (TextUtils.isEmpty(key2)) {
            view.loading(false)
            view.dialogHandling(2)
            return
        }
        if (TextUtils.isEmpty(key3)) {
            view.loading(false)
            view.dialogHandling(3)
            return
        }
        if (!helper.isValidPassword(key2)){
            view.loading(false)
            view.dialogHandling(4)
            return
        }
        if (helper.validatePassword(key2,key3)){
            view.loading(false)
            view.dialogHandling(5)
            return
        }
        if (!helper.validate(key1,1)){
            view.loading(false)
            view.dialogHandling(6)
            return
        }
        if (validator.isTextHarmful(key1)){
            view.dialogHandling(8)
            illegalStringDetected = true
        }

        if (!illegalStringDetected) {
            view.signUpEmail()
        }
    }

}