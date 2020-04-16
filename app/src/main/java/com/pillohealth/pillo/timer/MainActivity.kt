package com.pillohealth.pillo.timer

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TimerManager.OnTimerListener, View.OnClickListener {

    private var timerManager : TimerManager = TimerManager()
    private var animator: ValueAnimator? = null
    private var timerDuration = 20_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart?.setOnClickListener(this)
        btnStop?.setOnClickListener(this)
        btnPause?.setOnClickListener(this)

        timerManager.setDuration(20_000)
        timerManager.setOnRunListener(this)
    }

    private fun start() {
        timerManager.start()
    }

    private fun pause() {
        timerManager.pause()
    }

    private fun stop() {
        timerManager.stop()
        timerManager.setDuration(20_000)
    }

    override fun onTimerRun(text: String) {
        runOnUiThread {
            txt?.text = text
        }
    }

    override fun onTimerStarted() {
        progress.progressTintList = ColorStateList.valueOf(Color.RED)
        animator = ValueAnimator.ofFloat((animator?.animatedValue as? Float) ?: 0f, 1f).apply {
            this.duration = timerDuration
            this.addUpdateListener {
                val toInt = ((it.animatedValue as Float) * 100).toInt()
                progress?.progress = toInt
            }
            this.start()
        }
    }

    override fun onTimerPaused(remainingMillis: Long) {
        animator?.pause()
        timerDuration = remainingMillis
    }

    override fun onTimerStopped() {
        animator?.cancel()
        animator = null
    }

    override fun onTimerEnded() {
        runOnUiThread {
            progress.progressTintList = ColorStateList.valueOf(Color.GREEN)
        }
        animator = null
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
