package berlin.intero.welcome_back_2020.ui.accelerometer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import berlin.intero.welcome_back_2020.R
import berlin.intero.welcome_back_2020.observables.AccelerometerObservable

class AccelerometerFragment : Fragment() {

    private lateinit var accelerometerViewModel: AccelerometerViewModel
    private lateinit var accelerometerObservable: AccelerometerObservable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        accelerometerViewModel = ViewModelProvider(this).get(AccelerometerViewModel::class.java)
        accelerometerObservable = activity?.run {
            ViewModelProviders.of(this).get(AccelerometerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        val root = inflater.inflate(R.layout.fragment_accelerometer, container, false)
        val tvAccelerometerX: TextView = root.findViewById(R.id.text_accelerometer_x)
        val tvAccelerometerY: TextView = root.findViewById(R.id.text_accelerometer_y)
        val tvAccelerometerZ: TextView = root.findViewById(R.id.text_accelerometer_z)
        val tvAccelerometerXMax: TextView = root.findViewById(R.id.text_accelerometer_x_max)
        val tvAccelerometerYMax: TextView = root.findViewById(R.id.text_accelerometer_y_max)
        val tvAccelerometerZMax: TextView = root.findViewById(R.id.text_accelerometer_z_max)
        val tvAccelerometerCurrent: TextView = root.findViewById(R.id.text_accelerometer_current)

        accelerometerObservable.raw.observe(viewLifecycleOwner, Observer {
            tvAccelerometerX.text = resources.getString(R.string.value_accelerometer_x, it[0].formatValue())
            tvAccelerometerY.text = resources.getString(R.string.value_accelerometer_y, it[1].formatValue())
            tvAccelerometerZ.text = resources.getString(R.string.value_accelerometer_z, it[2].formatValue())
        })

        accelerometerObservable.max.observe(viewLifecycleOwner, Observer {
            tvAccelerometerXMax.text = resources.getString(R.string.value_accelerometer_x_max, it[0].formatValue())
            tvAccelerometerYMax.text = resources.getString(R.string.value_accelerometer_y_max, it[1].formatValue())
            tvAccelerometerZMax.text = resources.getString(R.string.value_accelerometer_z_max, it[2].formatValue())
        })

        accelerometerObservable.current.observe(viewLifecycleOwner, Observer {
            tvAccelerometerCurrent.text = it.formatValue()
        })

        tvAccelerometerCurrent.setOnClickListener {
            accelerometerObservable.reset()
        }

        return root
    }

    private fun Float.formatValue() = String.format("%.2f", Math.abs(this))
}