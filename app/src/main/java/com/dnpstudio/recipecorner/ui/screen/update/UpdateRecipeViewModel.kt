package com.dnpstudio.recipecorner.ui.screen.update

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.ui.screen.detail.DetailArguments
import com.dnpstudio.recipecorner.ui.screen.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

    fun updateRecipe(id: Int, recipeImg: String, recipeName: String, ingredients: String, steps: String){
        viewModelScope.launch {
            _updateRecipeState.emitAll(
                repository.updateRecipe(id, recipeImg, recipeName, ingredients, steps)
            )
        }
    }

}