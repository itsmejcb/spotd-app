package com.reyson.spotd.Class.Activity.SignUp.Username

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.reyson.spotd.Class.Components.Block.Helper

class Presenter (view: Interface.View, context: Context): Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    init {
        helper = Helper(context)
    }
    override fun createUsername(key: String){
        var illegalStringDetected = false
        view.loading(true)
        // check if not empty
        if (TextUtils.isEmpty(key)) {
            view.loading(false)
            view.dialogHandling(1)
            return
        }
        Log.d("status in username", helper.isValid(key).toString())
        // check if valid length
        if (!helper.isValid(key)){
            view.loading(false)
            // show a dialog that string length is < 2 or > 20
            view.dialogHandling(2)
            Log.d("status in username", "1")

            return
        }
        // check if not contain invalid words
        if (!helper.validate(key,2)){
            view.loading(false)
            view.dialogHandling(2)
            illegalStringDetected = true
            Log.d("status in username", "2")
        }

        if (!illegalStringDetected) {
            view.signUpUsername()
        }
    }

}