package com.dnpstudio.recipecorner.ui.screen.home.state

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.data.source.remote.User

sealed class HomeState {
    object Loading: HomeState()
    data class Success(val list: List<Recipe>): HomeState()
    data class Error(val message: String): HomeState()
}