package com.dnpstudio.recipecorner.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.dnpstudio.recipecorner.ui.screen.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
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

    private val _favoriteState = mutableStateOf(false)
    val favoriteState = _favoriteState.value

    fun onRealtimeRecipeDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeDetail(navArgs.id!!).onSuccess {
                it.onEach {
                    _detailState.emit(ResponseState.Success(it))
                }.collect()
            }.onFailure {
                _detailState.emit(ResponseState.Error(it.message.toString()))
                Log.d("DETAIL", it.message.toString())
            }
        }
    }


    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.deleteFavorite -> {
                viewModelScope.launch {
                    repository.deleteFavorite(event.favorite)
                }
            }

            is DetailEvent.insertFavorite -> {
                viewModelScope.launch {
                    repository.insertFavorite(event.favorite)
                }
            }
        }
    }

    fun getFavoriteData() {
        viewModelScope.launch {
            _detailState.emitAll(
                flow {
                    emit(
                        ResponseState.Success(
                            Recipe(
                                navArgs.id.toString(),
                                navArgs.recipeImg!!,
                                navArgs.recipeName!!,
                                navArgs.ingredients!!,
                                navArgs.steps
                            )
                        )
                    )
                }
            )
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }

    fun leaveRealtimeChannel() {
        viewModelScope.launch {
            repository.unsubscribeDetailChannel()
        }
    }

}

sealed class DetailEvent {
    data class insertFavorite(val favorite: Favorite) : DetailEvent()
    data class deleteFavorite(val favorite: Favorite) : DetailEvent()
}