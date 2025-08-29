package com.reyson.spotd.Class.Fragments.Profile.Post

import android.content.Context
import android.text.TextUtils
import com.reyson.spotd.Class.Components.Block.Helper

class Presenter (view: Interface.View, context: Context): Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper = Helper(context)
    init {

    }


}