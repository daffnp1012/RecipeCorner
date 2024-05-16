package com.dnpstudio.recipecorner.ui.screen.update

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.dnpstudio.recipecorner.ui.screen.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    
    val navArgs: DetailArguments = savedStateHandle.navArgs()

    private val _updateRecipeState =
        MutableStateFlow<ResponseState<Boolean>>(ResponseState.Loading)
    val updateRecipeState = _updateRecipeState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Loading
    )

    fun updateRecipe(
        recipe: Recipe,
        uri: Uri?
    ){
        viewModelScope.launch {

            if (uri == null){
                _updateRecipeState.emitAll(
                    repository.updateRecipe(recipe)
                )
            } else {
                _updateRecipeState.emit(ResponseState.Loading)
                try {
                    repository.uploadFile(recipe.recipeName, uri).let { url ->
                        repository.updateRecipe(recipe.copy(recipeImg = url)).collect()
                        _updateRecipeState.emit(ResponseState.Success(true))
                    }
                } catch (e: Exception) {
                    _updateRecipeState.emit(ResponseState.Error(e.message.toString()))
                }
            }
        }
    }

}