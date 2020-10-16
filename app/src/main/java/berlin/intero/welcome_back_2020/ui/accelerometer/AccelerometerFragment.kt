package berlin.intero.welcome_back_2020.ui.accelerometer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import berlin.intero.welcome_back_2020.R
import berlin.intero.welcome_back_2020.viewmodel.accelerometer.AccelerometerViewModel

class AccelerometerFragment : Fragment() {

    private lateinit var accelerometerViewModel: AccelerometerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.accelerometerViewModel =
            ViewModelProvider(this).get(AccelerometerViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_accelerometer, container, false)
        val tvAccelerometerX: TextView = root.findViewById(R.id.text_accelerometer_x)
        val tvAccelerometerY: TextView = root.findViewById(R.id.text_accelerometer_y)
        val tvAccelerometerZ: TextView = root.findViewById(R.id.text_accelerometer_z)
        val tvAccelerometerCurrent: TextView = root.findViewById(R.id.text_accelerometer_current)

        this.accelerometerViewModel.accelerometerData.observe(viewLifecycleOwner, Observer {
            tvAccelerometerX.text =
                resources.getString(R.string.value_accelerometer_x, it.x.formatValue())
            tvAccelerometerY.text =
                resources.getString(R.string.value_accelerometer_y, it.y.formatValue())
            tvAccelerometerZ.text =
                resources.getString(R.string.value_accelerometer_z, it.z.formatValue())

            tvAccelerometerCurrent.text = maxOf(it.x, it.y, it.z).formatValue()
        })

        return root
    }

    private fun Double.formatValue() = String.format("%.2f", Math.abs(this))
}