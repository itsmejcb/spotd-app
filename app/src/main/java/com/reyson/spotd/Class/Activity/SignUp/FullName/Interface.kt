package com.reyson.spotd.Class.Activity.SignUp.FullName

import com.reyson.spotd.Data.Interface.Interface

interface Interface {
    interface View: Interface.View{

        fun signUpFullname()
        fun dialogHandling(num: Int)
        fun loading(boolean: Boolean)
    }
    interface Presenter: Interface.Presenter{

        fun createFullname(key1: String, key2: String, key3: String)
    }
}