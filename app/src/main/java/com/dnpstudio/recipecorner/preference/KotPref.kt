package com.dnpstudio.recipecorner.preference

import com.chibatching.kotpref.KotprefModel

object KotPref: KotprefModel() {
    var id by stringPref("")
    var username by stringPref("")
    var email by stringPref("")
    var imageUrl by nullableStringPref()

    var isDarkMode by booleanPref(true)
}