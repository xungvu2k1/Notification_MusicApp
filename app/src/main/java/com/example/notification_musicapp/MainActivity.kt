package com.example.notification_musicapp

import android.Manifest
import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var NOTIFICATION_ID1: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnSendNotification : Button = findViewById(R.id.btn_send_notification)
        btnSendNotification.setOnClickListener{
            sendMediaNotification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendMediaNotification() {
        // convert image to bitmap
        var bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.thimau)
        // create and config notification
        var mediaSessionCompat : MediaSessionCompat = MediaSessionCompat(this, "tag")
        val notification : Notification = NotificationCompat.Builder(this, GooglePlayMusic.getInstance().CHANNEL_ID1)
            .setSmallIcon(R.drawable.ic_small_music)
            .setSubText("Mot ngay buon")
            .setContentTitle("Tile of Song: Thi Mau")
            .setContentText("Hoa Minzy")
            .setLargeIcon(bitmap)// lấy vào dạng bitmap
            // Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.ic_back, "Previous", null) // #0
            .addAction(R.drawable.ic_pause, "Pause", null) // #1
            .addAction(R.drawable.ic_next, "Next", null) // #2
            // Apply the media style template.
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1 /* #1: pause button \*/) // khi notification ở dạng thu gọn, ta sẽ nhìn thấy button của action này. Ở đây, truyền 1 => hiện action có index 1
                .setMediaSession(mediaSessionCompat.getSessionToken()))//Attaches a MediaSessionCompat.Token to this Notification to provide additional playback information and control to the SystemUI.
            .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
        notificationManagerCompat.notify(NOTIFICATION_ID1 // đang fix cứng id của notification. Nếu click vào button của notification nhiều lần
                                                          // các notification bị đè lên nhau, nếu muốn mỗi lần click thì hệ thống gửi các notification khác nhau => tạo id khác nhau
            , notification)
    }
}