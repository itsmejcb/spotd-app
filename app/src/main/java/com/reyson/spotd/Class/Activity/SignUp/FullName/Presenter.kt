package com.reyson.spotd.Class.Activity.SignUp.FullName

import android.content.Context
import android.text.TextUtils
import com.reyson.spotd.Class.Components.Block.Helper
import com.reyson.spotd.Class.Components.Block.Validator

class Presenter(view: Interface.View, context: Context) : Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    private var validator: Validator

    init {
        helper = Helper(context)
        validator = Validator(context)
        validator.loadHarmfulWords()
    }

    override fun createFullname(key1: String, key2: String, key3: String) {
        var illegalStringDetected = false
        view.loading(true)

        if (TextUtils.isEmpty(key1)) {
            view.loading(false)
            view.dialogHandling(1)
            return
        }
        if (TextUtils.isEmpty(key3)) {
            view.loading(false)
            view.dialogHandling(2)
            return
        }
        if (!helper.validate(key1, 2)) {
            view.loading(false)
            view.dialogHandling(3)
            return
        }
        if (key2.isNotEmpty() && !helper.validate(key2, 2)) {
            view.loading(false)
            view.dialogHandling(4)
            return
        }
        if (!helper.validate(key3, 2)) {
            view.loading(false)
            view.dialogHandling(5)
            return
        }
        if (validator.isTextHarmful(key1)) {
            view.loading(false)
            view.dialogHandling(6)
            return
        }
        if (validator.isTextHarmful(key2)) {
            view.loading(false)
            view.dialogHandling(7)
            return
        }
        if (validator.isTextHarmful(key3)) {
            // view.loading(false)
            view.dialogHandling(8)
            illegalStringDetected = true
        }

        if (!illegalStringDetected) {
            view.signUpFullname()
        }
    }
}