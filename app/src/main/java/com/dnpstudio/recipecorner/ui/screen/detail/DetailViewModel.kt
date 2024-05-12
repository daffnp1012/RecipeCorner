package com.dnpstudio.recipecorner.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.ui.screen.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: RecipeRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val navArgs: DetailArguments = stateHandle.navArgs()

    private val _detailState = MutableStateFlow<ResponseState<Recipe>>(
        ResponseState.Loading
    )
    val detailState = _detailState.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            ResponseState.Loading
    )

    fun onRealtimeRecipeDetail(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            repository.getRecipeDetail(recipeId).onSuccess {
                it.onEach {
                    _detailState.emit(ResponseState.Success(it))
                }.collect()
            }.onFailure {
                _detailState.emit(ResponseState.Error(it.message.toString()))
            }
        }
    }

    fun leaveRealtimeChannel(){
        viewModelScope.launch {
            repository.unsubcribeChannel()
        }
    }

}