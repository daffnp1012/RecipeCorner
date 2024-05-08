package com.dnpstudio.recipecorner.ui.screen.home.state

import com.dnpstudio.recipecorner.data.source.remote.Recipe
import kotlinx.coroutines.flow.Flow

sealed class HomeState<T> {
    data object Loading: HomeState<Nothing>()
    data class Success(val list: Result<Flow<List<Recipe>>>): HomeState<List<Recipe>>()
    data class Error(val message: String): HomeState<Nothing>()
}