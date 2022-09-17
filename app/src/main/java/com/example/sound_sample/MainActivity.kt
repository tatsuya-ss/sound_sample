package com.example.sound_sample

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.sound_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intent = Intent(this, MusicService::class.java)

        binding.startButton.setOnClickListener {
            startService(intent)
        }

        binding.pauseButton.setOnClickListener {
            stopService(intent)
        }
    }
}

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("Tatsuya", "onCreate: ")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val context = applicationContext
        val channelId = "default"
        val title = "アプリタイトル"

        val pendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val notification = Notification.Builder(context, channelId)
            .setContentTitle(title) // android標準アイコンから
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentText("MediaPlay")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)

        mediaPlayer = MediaPlayer.create(this, R.raw.shining_star)
        mediaPlayer?.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("debug", "onDestroy()")
        audioStop()
        // Service終了
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun audioStop() {
        // 再生終了
        mediaPlayer?.stop()
        // リセット
        mediaPlayer?.reset()
        // リソースの解放
        mediaPlayer?.release()
    }

    companion object {
        private const val ONGOING_NOTIFICATION_ID = 1
    }
}

