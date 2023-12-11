package com.example.notification_musicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class GooglePlayMusic : Application(){
    var CHANNEL_ID1 : String = "channel1"
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //config channel 1
            // Create the NotificationChannel.
            val name1 = getString(R.string.channel_name1)
            val descriptionText1 = getString(R.string.channel_description1)
            val importance1 = NotificationManager.IMPORTANCE_HIGH
            val mChannel1 = NotificationChannel(CHANNEL_ID1, name1, importance1)
            mChannel1.description = descriptionText1

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel1)
        }
    }
    companion object {
        private var instance: GooglePlayMusic? = null

        fun getInstance(): GooglePlayMusic {
            if (instance == null){
                instance = GooglePlayMusic()
            }
            return instance!!
        }
    }
}