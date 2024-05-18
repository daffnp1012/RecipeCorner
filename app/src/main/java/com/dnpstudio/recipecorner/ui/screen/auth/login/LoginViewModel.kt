package com.dnpstudio.recipecorner.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnpstudio.recipecorner.data.repository.RecipeRepository
import com.rmaprojects.apirequeststate.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel(){

    private val _loginState =
        MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)
    val loginState = _loginState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3000),
        ResponseState.Idle
    )

    fun login(email: String, password: String){
        viewModelScope.launch {
            _loginState.emitAll(repository.login(email, password))
        }
    }

}