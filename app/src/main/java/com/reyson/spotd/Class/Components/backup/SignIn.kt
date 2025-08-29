package com.reyson.spotd.Class.Components.backup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reyson.spotd.R

class SignIn:AppCompatActivity(),Interface.View {

    private lateinit var presenter: Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initialize(savedInstanceState)
        initializeLogic()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        presenter = Presenter(this,this)

    }

    private fun initializeLogic() {


    }
}