package berlin.intero.welcome_back_2020.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import berlin.intero.welcome_back_2020.R
import berlin.intero.welcome_back_2020.utils.GpsUtils
import berlin.intero.welcome_back_2020.viewmodel.location.LocationViewModel

/**
 * Displays location fragment
 */
class LocationFragment : Fragment() {

    /** View model */
    private lateinit var locationViewModel: LocationViewModel

    /** Text view displaying latitude */
    private lateinit var tvLocationLatitude: TextView
    /** Text view displaying longitude */
    private lateinit var tvLocationLongitude: TextView
    /** Text view displaying current location */
    private lateinit var tvLocationCurrent: TextView

    /** Whether GPS is enabled or not */
    private var isGPSEnabled = false

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
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_location, container, false)
        tvLocationLatitude = root.findViewById(R.id.text_location_latitude)
        tvLocationLongitude = root.findViewById(R.id.text_location_longitude)
        tvLocationCurrent = root.findViewById(R.id.text_location_current)

        return root
    }

    /**
     * Handles activity-result lifecycle phase
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    /**
     * Handles attach lifecycle phase
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        GpsUtils(context).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                this@LocationFragment.isGPSEnabled = isGPSEnable
            }
        })
    }

    /**
     * Handles start lifecycle phase
     */
    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    /**
     * Handles request-permission-result lifecycle phase
     */
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    //
    //
    //

    /**
     * Invokes location action
     */
    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> tvLocationCurrent.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> tvLocationCurrent.text =
                getString(R.string.permission_request)

            else -> activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_REQUEST
                )
            }
        }
    }

    /**
     * Starts location updates
     */
    private fun startLocationUpdate() {
        locationViewModel.locationData.observe(viewLifecycleOwner, Observer {
            tvLocationLatitude.text =
                resources.getString(R.string.value_location_latitude, it.latitude.toString())
            tvLocationLongitude.text =
                resources.getString(R.string.value_location_latitude, it.longitude.toString())
            tvLocationCurrent.text = resources.getString(
                R.string.value_location,
                it.latitude.formatValue(),
                it.longitude.formatValue()
            )
        })
    }

    /**
     * Checks if permissions are granted
     */
    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    /**
     * Checks whether permission request should be shown
     */
    private fun shouldShowRequestPermissionRationale() =
        activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } ?: false

    //
    // Helpers
    //

    /**
     * Formats double values to have
     */
    private fun Double.formatValue() = String.format("%.2f", Math.abs(this))
}

/** ID to identify location requests */
const val LOCATION_REQUEST = 100
/** ID to identify GPS requests */
const val GPS_REQUEST = 101