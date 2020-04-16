package com.dariobrux.kotimer

import android.util.Log
import com.dariobrux.kotimer.enums.Status
import com.dariobrux.kotimer.interfaces.OnTimerListener
import java.util.*
import kotlin.concurrent.fixedRateTimer

/**
 * Created by Dario Bruzzese
 * on 2/11/2020
 */
class KoTimer {

    private var timer: Timer? = null
    private var status: Status? = null
    private var listener: OnTimerListener? = null

    private var initialTimerDuration = 0L
    private var currentDuration = 0L
    private var startDelay = 0L
    private var isDaemon = false

    fun setDuration(duration: Long) {
        initialTimerDuration = duration
        currentDuration = duration
    }

    fun setIsDaemon(isDaemon: Boolean) {
        this.isDaemon = isDaemon
    }

    fun setStartDelay(delay: Long) {
        this.startDelay = delay
    }

    fun setOnRunListener(listener: OnTimerListener) {
        this.listener = listener
    }

    fun start() {

        if (status == Status.RUN) {
            return
        }

        // When the status is end or stop I must reinitialize the duration to initial duration.
        if (status == Status.END || status == Status.STOP) {
            currentDuration = initialTimerDuration
            status = null
        }

        val delay = when (status) {
            null -> {
                startDelay
            }
            else -> {
                0
            }
        }

        status = Status.START

        timer = fixedRateTimer("timer", isDaemon, delay, 1000) {

            currentDuration -= 1_000

            Log.d("Timer", currentDuration.toString())

            // When I arrive to -1 it means that all the milliseconds at 0 seconds are passed.
            if (currentDuration == -1_000L) {
                end()
                return@fixedRateTimer
            }

            if (status == Status.START) {
                listener?.onTimerStarted()
            }

            status = Status.RUN

            listener?.onTimerRun(currentDuration)
        }
    }

    private fun end() {
        if (status == Status.END) {
            return
        }
        status = Status.END
        recycle()
        listener?.onTimerEnded()
    }

    fun stop() {
        if (status == Status.STOP) {
            return
        }
        status = Status.STOP
        recycle()
        listener?.onTimerStopped()
    }

    fun pause() {
        if (status == Status.STOP || status == Status.PAUSE || status == Status.END) {
            return
        }
        status = Status.PAUSE
        timer?.apply {
            cancel()
            purge()
        }
        listener?.onTimerPaused(currentDuration)
    }

    private fun recycle() {
        timer?.apply {
            cancel()
            purge()
        }
        timer = null
        currentDuration = 0
    }
}