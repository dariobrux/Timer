package com.pillohealth.pillo.timer

import android.os.SystemClock
import android.util.Log
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer

/**
 * @author Dario Bruzzese
 * on 2/11/2020
 */
class TimerManager {

    private var timer: Timer? = null
    private var remainingMillis = 0L
    private var status: Status? = null
    private var listener: OnTimerListener? = null
    private var elapsedTime = 0L
    private var timerDuration: Long = 0L

    private enum class Status {
        RUN,
        PAUSE,
        STOP,
        END
    }

    interface OnTimerListener {
        fun onTimerRun(text: String)
        fun onTimerStarted()
        fun onTimerPaused(remainingMillis: Long)
        fun onTimerStopped()
        fun onTimerEnded()
    }

    fun setDuration(duration: Long) {
        timerDuration = duration
    }

    fun setOnRunListener(listener: OnTimerListener) {
        this.listener = listener
    }

    fun start() {
        if (status == Status.RUN) return
        status = Status.RUN

        val startTime = SystemClock.elapsedRealtime() - elapsedTime
        Log.d("Timer", "$startTime")

        timer = fixedRateTimer("timer", true, 0, 1000) {
//            remainingMillis = timerDuration - (SystemClock.elapsedRealtime() - startTime)

            timerDuration -= 1_000

            Log.d("Timer", timerDuration.toString())

            val hours = timerDuration / (1000 * 60 * 60) % 24
            val minutes = (timerDuration / (1000 * 60)) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(timerDuration)

            val text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            listener?.onTimerRun(text)

            if (timerDuration == -1_000L) {
                end()
            }
        }

        listener?.onTimerStarted()
    }

    private fun end() {
        if (status == Status.END) return
        status = Status.END
        timer?.cancel()
        timer?.purge()
        remainingMillis = 0
        timerDuration = 0
        listener?.onTimerEnded()
    }

    fun stop() {
        if (status == Status.STOP) return
        status = Status.STOP
        timer?.cancel()
        timer?.purge()
        remainingMillis = 0
        elapsedTime = 0
        timerDuration = 0
        listener?.onTimerStopped()
    }

    fun pause() {
        if (status == Status.STOP || status == Status.PAUSE || status == Status.END) return
        status = Status.PAUSE
        timer?.cancel()
        timer?.purge()
        listener?.onTimerPaused(remainingMillis)
        elapsedTime = timerDuration - remainingMillis
    }
}