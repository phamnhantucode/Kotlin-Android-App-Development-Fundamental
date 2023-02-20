package com.learning.runningapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.PolylineOptions
import com.learning.runningapp.Polyline
import com.learning.runningapp.Polylines
import com.learning.runningapp.R
import com.learning.runningapp.databinding.FragmentTrackingBinding
import com.learning.runningapp.other.Constant
import com.learning.runningapp.other.TrackingUtility
import com.learning.runningapp.services.TrackingService
import com.learning.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrackingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTrackingBinding? = null

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var map: GoogleMap? = null
    val binding get() = _binding!!

    private var currentTimeInMillis = 0L
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (isTracking) {
            binding.apply {
                btnToggleRun.setText(R.string.pause)
                btnFinishRun.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                btnToggleRun.setText(R.string.start)
                btnFinishRun.visibility = View.GONE
            }
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandService(Constant.ACTION_PAUSE)
        } else {
            sendCommandService(Constant.ACTION_START_OR_RESUME)
        }
    }

    private fun subscribesToObserver() {
        TrackingService.pathPoints.observe(viewLifecycleOwner, {
            pathPoints = it
            addLastestPolyline()
            moveCameraToUser()
        })

        TrackingService.isTracking.observe(viewLifecycleOwner, {
            updateTracking(it)
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, {
            currentTimeInMillis = it
            binding.tvTimer.text = TrackingUtility.getFormattedStopWatchTime(currentTimeInMillis, false)
        })
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory
                    .newLatLngZoom(
                        pathPoints.last().last(),
                        Constant.MAP_ZOOM_VIEW
                    )
            )
        }
    }

    private fun addAllPolylines() {
        for (pathPoint in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(Constant.POLYLINE_COLOR)
                .width(Constant.POLYLINE_WIDTH)
                .addAll(pathPoint)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLastestPolyline() {
        if (isTracking) {
            if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
                val preLastLatLng = pathPoints.last()[pathPoints.last().size-2]
                val lastLatLng = pathPoints.last().last()
                val polylineOptions = PolylineOptions()
                    .color(Constant.POLYLINE_COLOR)
                    .width(Constant.POLYLINE_WIDTH)
                    .add(preLastLatLng)
                    .add(lastLatLng)
                map?.addPolyline(polylineOptions)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun sendCommandService(action: String) {
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tracking, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            map = it
            addAllPolylines()
        }
        subscribesToObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToggleRun.setOnClickListener {
            toggleRun()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrackingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrackingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}