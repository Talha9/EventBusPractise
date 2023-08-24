package com.example.eventbuspractise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventbuspractise.Events.ActivityActivityMessage
import com.example.eventbuspractise.Events.ActivityFragmentMessage
import com.example.eventbuspractise.Events.FragmentActivityMessage
import com.example.eventbuspractise.GlobalBus.getBus
import com.example.eventbuspractise.databinding.ActivityMainBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        addFragment()
        clickListeners()
        Toast.makeText(this, "R1", Toast.LENGTH_SHORT).show()

    }

    private fun clickListeners() {
        binding.sendMessageToFragment.setOnClickListener {
            sendMessageToFragment()
        }
        binding.showSecondActivity.setOnClickListener {
            showSecondActivity()
        }
    }

    @Subscribe
    fun getMessage(fragmentActivityMessage: FragmentActivityMessage) {
        binding.message.text =
            getString(R.string.message_received) + " " + fragmentActivityMessage.message
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun getActivityMessage(activityActivityMessage: ActivityActivityMessage) {
        binding.message2.text =
            getString(R.string.message_received) + " " + activityActivityMessage.message
    }

    private fun showSecondActivity() {
        // Post an Sticky event before starting an activity to show the message,
        // sent by the MainActivity, in SecondActivity.
        val activityActivityMessageEvent =
            ActivityActivityMessage(binding.activity1Data.text.toString())
        getBus().postSticky(activityActivityMessageEvent)
        // Start SecondActivity.
        startActivity(Intent(this, Activity1::class.java))
    }


    private fun sendMessageToFragment() {
        val activityFragmentMessageEvent =
            ActivityFragmentMessage(binding.activityData.text.toString())
        getBus().post(activityFragmentMessageEvent)
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, Fragment1())
            .commit()
    }


}