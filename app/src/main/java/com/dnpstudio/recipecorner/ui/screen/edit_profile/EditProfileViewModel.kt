package com.dnpstudio.recipecorner.ui.screen.edit_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.preference.Preferences
import com.dnpstudio.recipecorner.ui.screen.navArgs
import com.dnpstudio.recipecorner.ui.screen.profile.ProfileArguments
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _editProfileState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)
    val editProfileState = _editProfileState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Idle
    )
    val navArgs : ProfileArguments = savedStateHandle.navArgs()

    fun editProfile(id: String, username: String){
        viewModelScope.launch {
            _editProfileState.emit(ResponseState.Loading)
            try {
                repository.editProfile(id, username)
                _editProfileState.emit(ResponseState.Success(true))
                Preferences.id = id
                Preferences.username = username
            } catch (e: Exception) {
                _editProfileState.emit(ResponseState.Error(e.message.toString()))
            }
        }
    }

}