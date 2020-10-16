package berlin.intero.welcome_back_2020.viewmodel.accelerometer

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class AccelerometerViewModel(application: Application) : AndroidViewModel(application) {

    val accelerometerData = AccelerometerLiveData(application)
}