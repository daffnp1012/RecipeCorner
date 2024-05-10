package com.dnpstudio.recipecorner.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
):ViewModel() {

    private val _addRecipeState = MutableStateFlow<ResponseState<Recipe>>(ResponseState.Idle)
    val addRecipeState = _addRecipeState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Idle
    )

    fun addRecipe(recipe: Recipe){
        viewModelScope.launch {
            _addRecipeState.emitAll(repository.insertRecipe(recipe))
        }
    }

}