package com.example.eventbuspractise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventbuspractise.Events.ActivityActivityMessage
import com.example.eventbuspractise.databinding.Activity1Binding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class Activity1 : BaseActivity() {
    private lateinit var binding: Activity1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity1Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        clicklisteners()

    }

    private fun clicklisteners() {
        binding.showSecondActivity.setOnClickListener {
            showSecondActivity()
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun getMessage(activityActivityMessage: ActivityActivityMessage) {
        binding.message2.text = getString(R.string.message_received) + " " + activityActivityMessage.message
        Toast.makeText(applicationContext, getString(R.string.message_second_activity) + " " + activityActivityMessage.message, Toast.LENGTH_SHORT).show()
    }

    private fun showSecondActivity() {
        // Post an Sticky event before starting an activity to show the message,
        // sent by the MainActivity, in SecondActivity.
        val activityActivityMessageEvent = ActivityActivityMessage(binding.activity1Data.text.toString())
        GlobalBus.getBus().postSticky(activityActivityMessageEvent)
        // Start SecondActivity.
        startActivity(Intent(this, MainActivity::class.java))
    }



}