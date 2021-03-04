package com.lrp.cronmetroconsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_PROXIMITY
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Chronometer
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener, ChronometerHolder {

    lateinit var chronometer: com.lrp.cronmetroconsensor.Chronometer
    lateinit var chronometerView : Chronometer
    lateinit var startStop : ImageButton

    private lateinit var sensorManager: SensorManager
    lateinit var proximitySensor: Sensor

    private val SENSOR_SENSITIVITY = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupChronometer()
        setupSensor()

    }

    private fun setupSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(TYPE_PROXIMITY)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    private fun setupChronometer() {
        chronometer = Chronometer(chronometerView, this)
    }

    private fun setupViews() {
        chronometerView = findViewById(R.id.chronometer_view)
        startStop = findViewById(R.id.start_stop_button)

        startStop.setOnClickListener {
            if (chronometer.status != ChronometerStatus.STOPPED) {
                chronometer.stop()
            } else {
                chronometer.start()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event!!
        if (event.sensor.getType() == TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near
                chronometer.pause()
            } else {
                //far
                chronometer.resume()
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onChronometerStarted() {
        startStop.setImageDrawable(resources.getDrawable(R.drawable.ic_stop, resources.newTheme()))
    }

    override fun onChronometerStopped() {
        startStop.setImageDrawable(resources.getDrawable(R.drawable.ic_start, resources.newTheme()))
    }

    override fun onChronometerPaused() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startStop.isEnabled = false
    }

    override fun onChronometerResumed() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startStop.isEnabled = true
    }


}