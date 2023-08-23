package com.example.eventbuspractise

import org.greenrobot.eventbus.EventBus

object GlobalBus {
    private var sBus: EventBus?=null

    public fun getBus(): EventBus {
        if (sBus == null) {
            sBus = EventBus.getDefault()
        }
        return sBus!!
    }
}