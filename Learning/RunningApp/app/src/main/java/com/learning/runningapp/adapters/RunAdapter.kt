package com.learning.runningapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.runningapp.R
import com.learning.runningapp.db.Run
import com.learning.runningapp.other.TrackingUtility
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter: ListAdapter<Run, RunAdapter.RunViewHolder>(
    object: DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
}) {
    inner class RunViewHolder: RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
        fun bind(run: Run) {
            itemView.apply {
                Glide.with(this).load(run.img).into(findViewById<ImageView>(R.id.ivRunImage))
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = run.timestamp
                }

                val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                findViewById<TextView>(R.id.tvDate).setText(dateFormat.format(calendar.time))
                findViewById<TextView>(R.id.tvAvgSpeed).setText("${run.avgSpeedInKMH}km/h")
                findViewById<TextView>(R.id.tvTime).setText("${TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)}")
                findViewById<TextView>(R.id.tvDistance).setText("${run.distanceInMeters}m")
                findViewById<TextView>(R.id.tvCalories).setText("${run.caloriesBurned}kcal")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false))
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        var run = currentList[position]
        holder.bind(run)
    }
}