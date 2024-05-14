package com.dnpstudio.recipecorner.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.local.favorite.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: RecipeRepository
): ViewModel() {

    val favoriteState = repository.getFavoriteList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        emptyList()
    )

//    fun deleteFavorite(favorite: Favorite){
//        viewModelScope.launch {
//
//        }
//    }

}