package com.dnpstudio.recipecorner.preference

import com.chibatching.kotpref.KotprefModel

object LocalUser: KotprefModel() {
    var username by stringPref()
    var imageUrl by nullableStringPref()
}