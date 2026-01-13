package com.example.moodtracker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination (
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label : Int,
    val contentDescription : String
    )
{
    HOME("home", R.drawable.house, R.string.home, ""),
    FOCUS("focus", R.drawable.timer, R.string.Focus, ""),
    STATS("stats", R.drawable.chart_bar, R.string.Stats, "")
}

