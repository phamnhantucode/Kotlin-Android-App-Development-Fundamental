package com.learning.runningapp.other

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.learning.runningapp.R
import com.learning.runningapp.db.Run
import java.text.SimpleDateFormat
import java.util.*


class CustomMarkerView(
    val runs: List<Run>,
    c: Context,
    layoutId: Int
): MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val curRunId = e.x.toInt()
        val run = runs[curRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }

        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        findViewById<TextView>(R.id.tvDate).setText(dateFormat.format(calendar.time))
        findViewById<TextView>(R.id.tvAvgSpeed).setText("${run.avgSpeedInKMH}km/h")
        findViewById<TextView>(R.id.tvDuration).setText("${TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)}")
        findViewById<TextView>(R.id.tvDistance).setText("${run.distanceInMeters}m")
        findViewById<TextView>(R.id.tvCaloriesBurned).setText("${run.caloriesBurned}kcal")
    }

}