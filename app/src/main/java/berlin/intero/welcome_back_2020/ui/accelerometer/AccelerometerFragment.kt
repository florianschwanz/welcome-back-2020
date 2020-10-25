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

/**
 * Displays accelerometer fragment
 */
class AccelerometerFragment : Fragment() {

    /** View model */
    private lateinit var accelerometerViewModel: AccelerometerViewModel

    /** Text view displaying x-acceleration */
    private lateinit var tvAccelerometerX: TextView
    /** Text view displaying y-acceleration */
    private lateinit var tvAccelerometerY: TextView
    /** Text view displaying z-acceleration */
    private lateinit var tvAccelerometerZ: TextView
    /** Text view displaying current acceleration */
    private lateinit var tvAccelerometerCurrent: TextView

    //
    // Lifecycle hooks
    //

    /**
     * Handles create-view lifecycle phase
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.accelerometerViewModel =
            ViewModelProvider(this).get(AccelerometerViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_accelerometer, container, false)
        tvAccelerometerX = root.findViewById(R.id.text_accelerometer_x)
        tvAccelerometerY = root.findViewById(R.id.text_accelerometer_y)
        tvAccelerometerZ = root.findViewById(R.id.text_accelerometer_z)
        tvAccelerometerCurrent = root.findViewById(R.id.text_accelerometer_current)

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

    //
    // Helpers
    //

    /**
     * Formats double values to have
     */
    private fun Double.formatValue() = String.format("%.2f", Math.abs(this))
}