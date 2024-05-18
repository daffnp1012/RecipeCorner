package com.dnpstudio.recipecorner.ui.screen.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.preference.Preferences
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
):ViewModel() {

    private val _addRecipeState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)
    val addRecipeState = _addRecipeState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Idle
    )

    fun addRecipe(recipe: Recipe, file: Uri? = null){
        viewModelScope.launch {
            _addRecipeState.emit(ResponseState.Loading)
            if (file == null){
                try {
                    repository.insertRecipe(recipe).collect()
                    _addRecipeState.emit(ResponseState.Success(true))
                } catch (e: Exception){
                    _addRecipeState.emit(ResponseState.Error(e.message.toString()))
                }
            } else {
                try {
                    repository.uploadFile("${Preferences.username}/${recipe.recipeName}.png", file).let { url ->
                        repository.insertRecipe(recipe.copy(recipeImg = url)).collect()
                        _addRecipeState.emit(ResponseState.Success(true))
                    }
                } catch(e: Exception){
                    _addRecipeState.emit(ResponseState.Error(e.message.toString()))
                }
            }
        }
    }

}