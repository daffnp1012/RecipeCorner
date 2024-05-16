package com.dnpstudio.recipecorner.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dnpstudio.recipecorner.preference.Preferences

object GlobalState {
    var isDarkMode by mutableStateOf(Preferences.isDarkMode)
}