package com.example.sound_sample

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sound_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.shining_star)

        binding.startButton.setOnClickListener {
            mediaPlayer?.start()
        }

        binding.pauseButton.setOnClickListener {
            mediaPlayer?.pause()
        }
    }
}