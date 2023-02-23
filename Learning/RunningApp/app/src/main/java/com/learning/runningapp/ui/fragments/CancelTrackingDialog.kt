package com.learning.runningapp.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.learning.runningapp.R

class CancelTrackingDialog: DialogFragment() {

    private var yesListener: (() -> Unit)? = null
    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cancel th Run?")
            .setMessage("Are you sure to cancel the current run and delete all its data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton(R.string.yes,{ _, _ ->
                yesListener?.let { it() }
            })
            .setNegativeButton(R.string.no, { dialogInterface, _ ->
                dialogInterface.cancel()
            })
            .create()
    }
}