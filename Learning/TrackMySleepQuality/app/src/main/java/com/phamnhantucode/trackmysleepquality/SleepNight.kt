package com.phamnhantucode.trackmysleepquality

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("daily_sleep_quality_table")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var nightId: Long = 0L,
    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo("end_time_milli")
    var endTimeMilli: Long = startTimeMilli,
    @ColumnInfo("sleep_quality")
    var sleepQuality: Int = -1
)
