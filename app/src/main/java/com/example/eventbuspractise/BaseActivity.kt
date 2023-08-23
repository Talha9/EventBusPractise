package com.example.eventbuspractise

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        GlobalBus.getBus().register(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalBus.getBus().unregister(this)
    }
}