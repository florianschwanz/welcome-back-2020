package berlin.intero.welcome_back_2020.viewmodel.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData

/**
 * Accelerometer live data
 */
class AccelerometerLiveData(context: Context) : LiveData<AccelerometerModel>(),
    SensorEventListener {

    /** Gravity acting on the device */
    private var gravity = AccelerometerModel(0.0, 0.0, 0.0)
    /** Acceleration acting on the device */
    private var acceleration = AccelerometerModel(0.0, 0.0, 0.0)

    /** Sensor manager */
    private var sensorManager: SensorManager
    /** Accelerometer sensor */
    private var accelerometerSensor: Sensor
    /** Gravity sensor */
    private var gravitySensor: Sensor

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    /**
     * Handles inactivity
     */
    override fun onInactive() {
        super.onInactive()
        sensorManager.unregisterListener(this)
    }

    /**
     * Handles activity
     */
    override fun onActive() {
        super.onActive()
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    /**
     * Handles sensor value change
     */
    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                // In this example, alpha is calculated as t / (t + dT),
                // where t is the low-pass filter's time-constant and
                // dT is the event delivery rate.

                val alpha: Float = 0.8f

                // Isolate the force of gravity with the low-pass filter.
                gravity.x = alpha * gravity.x + (1 - alpha) * event.values[0]
                gravity.y = alpha * gravity.y + (1 - alpha) * event.values[1]
                gravity.z = alpha * gravity.z + (1 - alpha) * event.values[2]

                // Remove the gravity contribution with the high-pass filter.
                acceleration.x = event.values[0] - gravity.x
                acceleration.y = event.values[1] - gravity.y
                acceleration.z = event.values[2] - gravity.z

                value = acceleration
            }
            Sensor.TYPE_GRAVITY -> {
                gravity = AccelerometerModel(
                    event.values[0].toDouble(),
                    event.values[1].toDouble(),
                    event.values[2].toDouble()
                )
            }
        }
    }

    /**
     * Handles accuracy change
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}