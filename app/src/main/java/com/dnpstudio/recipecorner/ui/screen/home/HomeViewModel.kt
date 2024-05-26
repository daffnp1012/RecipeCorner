package com.dnpstudio.recipecorner.ui.screen.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipeListState =
        MutableStateFlow<ResponseState<List<Recipe>>>(
        ResponseState.Loading
    )

    //State yang menyatakan sedang mencari atau tidak
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isRefreshing = mutableStateOf(true)
    val isRefreshing = _isRefreshing.value

    //State teks yang diketik user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val recipeListState =
        _recipeListState.asStateFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            ResponseState.Loading
        )

    fun onRealtimeRecipeList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipe().onSuccess { flow ->
                flow.onEach {
                    _recipeListState.emit(ResponseState.Success(it))
                }.collect()
            }
                .onFailure {
                    _recipeListState.emit(ResponseState.Error(it.message.toString()))
                }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            leaveRealtimeChannel()
            delay(3000)
            onRealtimeRecipeList()
        }
    }

    fun leaveRealtimeChannel() {
        viewModelScope.launch {
            repository.unsubcribeChannel()
        }
    }
    fun deleteRecipe(id: Int) {
        viewModelScope.launch {
            repository.deleteRecipe(id).collect()
        }
    }
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        } else {
            _isSearching.value
        }
    }
}