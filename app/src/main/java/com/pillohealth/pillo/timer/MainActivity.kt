package com.pillohealth.pillo.timer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dariobrux.kotimer.KoTimer
import com.dariobrux.kotimer.interfaces.OnTimerListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnTimerListener, View.OnClickListener {

    private var animator: ValueAnimator? = null

    private var koTimer: KoTimer = KoTimer()
    private var timerDuration = 10_000L
    private var delay = 2_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart?.setOnClickListener(this)
        btnStop?.setOnClickListener(this)
        btnPause?.setOnClickListener(this)

        koTimer.setDuration(timerDuration)
        koTimer.setIsDaemon(false)
        koTimer.setStartDelay(delay)
        koTimer.setOnRunListener(this)
    }

    private fun start() {
        koTimer.start()
    }

    private fun pause() {
        koTimer.pause()
    }

    private fun stop() {
        koTimer.stop()
    }

    override fun onTimerRun(milliseconds: Long) {

        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val minutes =
            TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(hours)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(
                hours
            )

        val text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        runOnUiThread {
            txt?.text = text
            progress.progressTintList = ColorStateList.valueOf(Color.RED)
            progress?.progress = ((milliseconds * 100) / timerDuration).toInt()
        }
    }

    override fun onTimerStarted() {
//        runOnUiThread {
//            progress.progressTintList = ColorStateList.valueOf(Color.RED)
//            animator = ValueAnimator.ofFloat((animator?.animatedValue as? Float) ?: 0f, 1f).apply {
//                this.duration = timerDuration
//                this.addUpdateListener {
//                    val toInt = ((it.animatedValue as Float) * 100).toInt()
//                    progress?.progress = toInt
//                }
//                this.start()
//            }
//        }
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
