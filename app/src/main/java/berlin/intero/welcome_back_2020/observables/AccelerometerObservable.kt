package berlin.intero.welcome_back_2020.observables

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class AccelerometerObservable : ViewModel() {
    val raw = MutableLiveData<FloatArray>()
    val max = MutableLiveData<FloatArray>()
    val current = MutableLiveData<Float>()

    init {
        raw.value = FloatArray(3)
        max.value = FloatArray(3)
    }

    fun data(item: FloatArray) {
        raw.value = item

        max.value = raw.value!!
            .mapIndexed { index, value ->
                if (value > max.value!![index]) {
                    value
                } else {
                    max.value!![index]
                }
            }
            .toFloatArray()

        current.value = raw.value!!.max()
    }

    fun reset() {
        raw.value = FloatArray(3)
        max.value = FloatArray(3)
        current.value = 0.0f
    }
}