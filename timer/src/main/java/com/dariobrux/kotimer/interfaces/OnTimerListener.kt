package com.dariobrux.kotimer.interfaces

interface OnTimerListener {
    fun onTimerRun(milliseconds: Long)
    fun onTimerStarted()
    fun onTimerPaused(remainingMillis: Long)
    fun onTimerStopped()
    fun onTimerEnded()
}