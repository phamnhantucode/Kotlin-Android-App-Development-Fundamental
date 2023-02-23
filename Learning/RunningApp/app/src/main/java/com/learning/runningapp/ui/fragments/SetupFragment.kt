package com.learning.runningapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.learning.runningapp.R
import com.learning.runningapp.databinding.FragmentSettingsBinding
import com.learning.runningapp.databinding.FragmentSetupBinding
import com.learning.runningapp.other.Constant.KEY_FIRST_TIME_TOGGLE
import com.learning.runningapp.other.Constant.KEY_NAME
import com.learning.runningapp.other.Constant.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SetupFragment : Fragment() {
    // TODO: Rename and change types of parameters

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true


    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSetupBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstAppOpen) {
            moveToRunFragment()
        }

        binding.apply {
            tvContinue.setOnClickListener {
                val success  = writePersonalDataToSharePref()
                if (success) {
                    moveToRunFragment()
                } else {
                    Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun moveToRunFragment() {
        val navOption = NavOptions.Builder()
            .setPopUpTo(R.id.setupFragment, true)
            .build()
        findNavController().navigate(
            SetupFragmentDirections.actionSetupFragmentToRunFragment(),
            navOption
        )
    }

    private fun writePersonalDataToSharePref(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}