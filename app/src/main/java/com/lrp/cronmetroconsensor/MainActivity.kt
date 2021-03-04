package com.lrp.cronmetroconsensor

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var chronometerView : Chronometer
    lateinit var startButton : Button
    lateinit var stopButton : Button
    lateinit var resetButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupChronometer()
    }

    private fun setupChronometer() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            chronometerView.isCountDown = false
        }
    }

    private fun setupViews() {
        chronometerView = findViewById(R.id.chronometer_view)
        startButton = findViewById(R.id.start_button)
        stopButton = findViewById(R.id.stop_button)
        resetButton = findViewById(R.id.reset_button)

        startButton.setOnClickListener {
            chronometerView.base = SystemClock.elapsedRealtime()
            chronometerView.start()
        }

        stopButton.setOnClickListener {
            chronometerView.stop()
        }
    }


}