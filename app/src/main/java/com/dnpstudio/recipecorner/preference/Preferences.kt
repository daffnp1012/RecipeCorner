package com.dnpstudio.recipecorner.preference

import com.chibatching.kotpref.KotprefModel

object Preferences: KotprefModel() {
    var id by nullableStringPref()
    var username by nullableStringPref()
    var email by stringPref("")

    var isDarkMode by booleanPref(true)
}