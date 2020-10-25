package berlin.intero.welcome_back_2020.viewmodel.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Location view model
 */
class LocationViewModel(application: Application) : AndroidViewModel(application) {

    /** Location live data */
    val locationData = LocationLiveData(application)
}