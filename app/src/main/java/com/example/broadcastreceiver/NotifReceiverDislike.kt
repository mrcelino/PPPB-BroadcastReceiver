package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotifReceiverDislike : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val objmaindislike = MainActivity()
        if(MainActivity.numberDislike!=null) {
            MainActivity.numberDislike++
            MainActivity.binding.tvDislikeCount.text = MainActivity.numberDislike.toString()
        }
    }

}