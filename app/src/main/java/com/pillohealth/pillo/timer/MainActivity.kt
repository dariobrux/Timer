package com.pillohealth.pillo.timer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dariobrux.kotimer.Timer
import com.dariobrux.kotimer.interfaces.OnTimerListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnTimerListener, View.OnClickListener {

    private var timer: Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart?.setOnClickListener(this)
        btnStop?.setOnClickListener(this)
        btnPause?.setOnClickListener(this)

        timer.setDuration(10_000L)
        timer.setIsDaemon(false)
        timer.setStartDelay(0L)
        timer.setOnTimerListener(this, true)
    }

    private fun start() {
        timer.start()
    }

    private fun pause() {
        timer.pause()
    }

    private fun stop() {
        timer.stop()
    }

    override fun onTimerRun(milliseconds: Long) {

        Log.d("Timer", milliseconds.toString())

        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours)

        val text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        txt?.text = text
        progress?.progressTintList = ColorStateList.valueOf(Color.RED)
        progress?.progress = ((milliseconds * 100) / 10_000L).toInt()
    }

    override fun onTimerStarted() {
        Toast.makeText(this, "Timer started", Toast.LENGTH_SHORT).show()
    }

    override fun onTimerPaused(remainingMillis: Long) {
        Toast.makeText(this, "Timer paused", Toast.LENGTH_SHORT).show()
    }

    override fun onTimerStopped() {
        Toast.makeText(this, "Timer stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onTimerEnded() {
        Toast.makeText(this, "Timer ended", Toast.LENGTH_SHORT).show()
        progress.progressTintList = ColorStateList.valueOf(Color.GREEN)
    }

    override fun onClick(view: View?) {
        when (view) {
            btnStart -> {
                start()
            }
            btnPause -> {
                pause()
            }
            btnStop -> {
                stop()
            }
        }
    }
}
