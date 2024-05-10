package com.dnpstudio.recipecorner.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {

    private val _recipeListState = MutableStateFlow<ResponseState<List<Recipe>>>(
        ResponseState.Loading
    )

    val recipeListState =
        _recipeListState.asStateFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            ResponseState.Loading
        )

    fun onRealtimeRecipeList() {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipe()
                .onSuccess { flow ->
                    flow.onEach {
                        _recipeListState.emit(ResponseState.Success(it))
                    }.collect()
                }
                .onFailure {
                    _recipeListState.emit(ResponseState.Error(it.message.toString()))
                }
        }
    }

    fun leaveRealtimeChannel() = viewModelScope.launch {
        repository.unsubcribeChannel()
    }

}