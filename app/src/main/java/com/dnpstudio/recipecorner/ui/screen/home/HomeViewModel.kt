package com.dnpstudio.recipecorner.ui.screen.home

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.ui.screen.home.event.HomeEvent
import com.dnpstudio.recipecorner.ui.screen.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {

    private val _recipeListState = MutableStateFlow<HomeState?>(null)
    val recipeListState = _recipeListState.asStateFlow()

    fun onEvent(
        event: HomeEvent
    ){
        when(event){
            HomeEvent.getRecipe ->{
                viewModelScope.launch {
                    _recipeListState.emit(HomeState.Loading)
                    try {
                        _recipeListState.emit(HomeState.Success(repository.getRecipe()))
                    } catch (e: Exception){
                        _recipeListState.emit(HomeState.Error(e.toString()))
                        return@launch
                    }
                }
            }

            else -> {

            }
        }
    }

}