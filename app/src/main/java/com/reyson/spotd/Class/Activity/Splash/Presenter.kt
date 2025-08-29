package com.reyson.spotd.Class.Activity.Splash

import android.content.Context
import com.reyson.spotd.Class.Components.Block.Helper

class Presenter (view: Interface.View, context: Context): Interface.Presenter {
    private val view: Interface.View = view
    private val context: Context = context
    private var helper: Helper
    init {
        helper = Helper(context)
    }


}