package com.dariobrux.kotimer.interfaces

abstract class OnTimerListenerAdapter : OnTimerListener {

    override fun onTimerRun(milliseconds: Long) {}

    override fun onTimerStarted() {}

    override fun onTimerPaused(remainingMillis: Long) {}

    override fun onTimerStopped() {}

    override fun onTimerEnded() {}
}