package com.reyson.spotd.Class.Activity.SignUp.Auth.Sent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reyson.spotd.R

class Sent:AppCompatActivity(),Interface.View {

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