package com.learning.runningapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.learning.runningapp.Polyline
import com.learning.runningapp.Polylines
import com.learning.runningapp.R
import com.learning.runningapp.databinding.FragmentTrackingBinding
import com.learning.runningapp.db.Run
import com.learning.runningapp.other.Constant
import com.learning.runningapp.other.TrackingUtility
import com.learning.runningapp.services.TrackingService
import com.learning.runningapp.ui.MainActivity
import com.learning.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.Calendar
import java.util.Timer
import kotlin.math.round

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrackingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class TrackingFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTrackingBinding? = null

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var map: GoogleMap? = null
    val binding get() = _binding!!

    private var menu: Menu? = null
    private var currentTimeInMillis = 0L
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (isTracking) {
            binding.apply {
                btnToggleRun.setText(R.string.pause)
                menu?.getItem(0)?.isVisible = true
                btnFinishRun.visibility = View.GONE
            }
        } else {
            binding.apply {
                btnToggleRun.setText(R.string.start)
                btnFinishRun.visibility = View.VISIBLE
            }
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandService(Constant.ACTION_PAUSE)
            menu?.getItem(0)?.isVisible = true
        } else {
            sendCommandService(Constant.ACTION_START_OR_RESUME)
        }
    }

    private fun stopRun() {
        sendCommandService(Constant.ACTION_STOP)
        findNavController().navigate(R.id.action_trackingFragment_to_RunFragment)
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
            binding.tvTimer.text = TrackingUtility.getFormattedStopWatchTime(currentTimeInMillis, true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miCancelTracking -> {
                showCancelTrackingDialog()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu, menu)
        this.menu = menu
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (currentTimeInMillis > 0L) {
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    private fun showCancelTrackingDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cancel th Run?")
            .setMessage("Are you sure to cancel the current run and delete all its data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton(R.string.yes,{ _,_ ->
                endRunAndSaveToDb()
            })
            .setNegativeButton(R.string.no, {dialogInterface,_ ->
                dialogInterface.cancel()
            })
            .create()
        dialog.show()
    }


    private fun sendCommandService(action: String) {
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for (pos in polyline) {
                bounds.include(pos)
            }
        }

        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                binding.mapView.width,
                binding.mapView.height,
                (binding.mapView.height * 0.05f).toInt()
            )
        )
    }

    private fun endRunAndSaveToDb() {
        map?.snapshot { bmp ->
            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calcuclatePolylineLength(polyline).toInt()
            }

            val avgSpeed = round((distanceInMeters.toDouble() / 1000) / (currentTimeInMillis.toDouble()/1000/60/60)* 10) / 10
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = 90
            (activity as MainActivity).viewModel.insertRun(bmp, dateTimeStamp, avgSpeed.toFloat(), distanceInMeters, currentTimeInMillis, caloriesBurned)
            Snackbar.make(
                requireActivity().findViewById(R.id.rootView),
                "Run saved successfully!!",
                Snackbar.LENGTH_LONG
            ).show()
            stopRun()
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
        binding.btnFinishRun.setOnClickListener {
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()
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