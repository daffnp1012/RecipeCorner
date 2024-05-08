package com.dnpstudio.recipecorner.ui.screen.detail

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.Recipe
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: RecipeRepository
) : ViewModel() {

    private val _detailState = mutableStateOf<ResponseState<Recipe>>(
        ResponseState.Loading
    )
    val detailState = _detailState.value

    fun onRealtimeRecipeDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipeDetail()
        }
    }

}