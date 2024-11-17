package com.example.broadcastreceiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val channelId = "Broadcast"
    private val notifId = 90

    // Companion object untuk menyimpan binding dan variabel global
    companion object {
        lateinit var binding: ActivityMainBinding
        var numberLike = 0
        var numberDislike = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNotificationManager()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun setupNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifChannel = NotificationChannel(
                channelId,
                "Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifManager.createNotificationChannel(notifChannel)
        }
    }
    private fun setupListeners() {
        binding.imgLike.setOnClickListener {
            updateLikeCount()
        }
        binding.imgDislike.setOnClickListener {
            updateDislikeCount()
        }
        binding.btnSendNotification.setOnClickListener {
            sendNotification()
        }
    }

    /**
     * Menambah jumlah 'like' dan memperbarui UI.
     */
    private fun updateLikeCount() {
        numberLike++
        binding.tvLikeCount.text = numberLike.toString()
    }

    /**
     * Menambah jumlah 'dislike' dan memperbarui UI.
     */
    private fun updateDislikeCount() {
        numberDislike++
        binding.tvDislikeCount.text = numberDislike.toString()
    }

    /**
     * Membuat dan mengirim notifikasi.
     */
    private fun sendNotification() {
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val likeIntent = createPendingIntent(NotifReceiverLike::class.java, 0)
        val dislikeIntent = createPendingIntent(NotifReceiverDislike::class.java, 1)

        val notification = buildNotification(likeIntent, dislikeIntent)
        notifManager.notify(notifId, notification)
    }

    /**
     * Membuat PendingIntent untuk aksi pada notifikasi.
     */
    private fun createPendingIntent(receiverClass: Class<*>, requestCode: Int): PendingIntent {
        val intent = Intent(this, receiverClass)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }
        return PendingIntent.getBroadcast(this, requestCode, intent, flags)
    }

    /**
     * Membuat NotificationCompat.Builder.
     */
    private fun buildNotification(
        likeIntent: PendingIntent,
        dislikeIntent: PendingIntent
    ): Notification {
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.emoji)
        val bigPicture = BitmapFactory.decodeResource(resources, R.drawable.anjay)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.emoji)
            .setLargeIcon(largeIcon)
            .setContentTitle("Counter")
            .setContentText("Govannnnnnnnnnn gamtenk")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPicture))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, "Like", likeIntent)
            .addAction(1, "Dislike", dislikeIntent)
            .build()
    }
}
