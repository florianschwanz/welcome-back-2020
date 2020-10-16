package berlin.intero.welcome_back_2020.ui.accelerometer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccelerometerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is accelerometer Fragment"
    }
    val text: LiveData<String> = _text
}