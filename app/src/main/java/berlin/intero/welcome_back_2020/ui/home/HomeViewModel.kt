package berlin.intero.welcome_back_2020.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * View model for home fragment
 */
class HomeViewModel : ViewModel() {

    /** Live data providing text */
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome back 2020"
    }

    /** Live data providing text */
    val text: LiveData<String> = _text
}