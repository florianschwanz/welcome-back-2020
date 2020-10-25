package berlin.intero.welcome_back_2020.viewmodel.accelerometer

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Accelerometer view model
 */
open class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {

    /** Accelerometer live data */
    val accelerometerData = AccelerometerLiveData(application)
}