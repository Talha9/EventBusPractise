package com.example.eventbuspractise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eventbuspractise.Events.ActivityFragmentMessage
import com.example.eventbuspractise.Events.FragmentActivityMessage
import com.example.eventbuspractise.GlobalBus.getBus
import com.example.eventbuspractise.databinding.FragmentFragment1Binding
import org.greenrobot.eventbus.Subscribe


class Fragment1 : Fragment() {

    private lateinit var binding: FragmentFragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        GlobalBus.getBus().register(this)
        binding = FragmentFragment1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListeners()

    }

    private fun clickListeners() {
        binding.submit.setOnClickListener {
            val fragmentActivityMessageEvent = FragmentActivityMessage(binding.editText.text.toString())
            getBus().post(fragmentActivityMessageEvent)
        }
    }

    @Subscribe
    fun getMessage(activityFragmentMessage: ActivityFragmentMessage) {

        binding.message.text = getString(R.string.message_received) + " " + activityFragmentMessage.message
        Toast.makeText(
            activity,
            getString(R.string.message_fragment) +
                    " " + activityFragmentMessage.message,
            Toast.LENGTH_SHORT
        ).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        GlobalBus.getBus().unregister(this)
    }


}