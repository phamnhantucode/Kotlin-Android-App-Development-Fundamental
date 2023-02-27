package com.learning.composecourseyt

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        (from.x + to.x) / 2f - 0,
        (from.y + to.y) / 2f - 100,
        to.x,
        to.y,
//    set abs to get
    )
}