package com.lrp.cronmetroconsensor

import android.os.SystemClock
import android.widget.Chronometer

class Chronometer(private val chronometerView : Chronometer, val holder : ChronometerHolder) {

    var status = ChronometerStatus.STOPPED
    var lastElapsedTime : Long = 0

    fun start() {
        if (status == ChronometerStatus.STOPPED) {
            chronometerView.base = SystemClock.elapsedRealtime()
            lastElapsedTime = 0
            chronometerView.start()
            status = ChronometerStatus.STARTED
            holder.onChronometerStarted()
        }
    }

    fun stop() {
        chronometerView.stop()
        chronometerView.base = SystemClock.elapsedRealtime()
        lastElapsedTime = 0
        status = ChronometerStatus.STOPPED
        holder.onChronometerStopped()
    }

    fun pause() {
        if (status == ChronometerStatus.STARTED) {
            lastElapsedTime = SystemClock.elapsedRealtime() - chronometerView.base
            chronometerView.stop()
            status = ChronometerStatus.PAUSED
            holder.onChronometerPaused()
        }
    }

    fun resume() {
        if (status == ChronometerStatus.PAUSED) {
            chronometerView.base = SystemClock.elapsedRealtime() - lastElapsedTime
            chronometerView.start()
            status = ChronometerStatus.STARTED
            holder.onChronometerResumed()
        }
    }

}

enum class ChronometerStatus {
    STOPPED,
    STARTED,
    PAUSED
}

interface ChronometerHolder {

    fun onChronometerStarted()

    fun onChronometerStopped()

    fun onChronometerPaused()

    fun onChronometerResumed()

}