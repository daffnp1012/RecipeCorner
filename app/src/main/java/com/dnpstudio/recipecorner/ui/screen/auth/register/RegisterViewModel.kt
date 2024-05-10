package com.dnpstudio.recipecorner.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.dnpstudio.recipecorner.data.source.remote.User
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {

    private val _registerState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)
    val registerState = _registerState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Idle
    )

    fun register(username: String, email: String, password: String){
        viewModelScope.launch {
            _registerState.emitAll(repository.register(username, email, password))
        }
    }

}