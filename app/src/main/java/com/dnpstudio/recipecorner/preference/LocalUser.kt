package com.dnpstudio.recipecorner.preference

import com.chibatching.kotpref.KotprefModel

object LocalUser: KotprefModel() {
    var id by stringPref("")
    var username by stringPref("")
    var imageUrl by nullableStringPref()
}