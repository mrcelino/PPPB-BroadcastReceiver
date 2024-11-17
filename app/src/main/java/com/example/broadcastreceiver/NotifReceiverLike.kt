package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotifReceiverLike : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val objmainlike = MainActivity()
        if(MainActivity.numberLike!=null){
            MainActivity.numberLike++
            MainActivity.binding.tvLikeCount.text = MainActivity.numberLike.toString()

        }


    }
}